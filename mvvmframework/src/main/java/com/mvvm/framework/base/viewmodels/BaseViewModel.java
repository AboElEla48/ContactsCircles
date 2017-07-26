package com.mvvm.framework.base.viewmodels;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mvvm.framework.annotation.viewmodelfields.ViewModelCheckBoxField;
import com.mvvm.framework.annotation.viewmodelfields.ViewModelHintEditTextField;
import com.mvvm.framework.annotation.viewmodelfields.ViewModelImageViewField;
import com.mvvm.framework.annotation.viewmodelfields.ViewModelTextField;
import com.mvvm.framework.annotation.viewmodelfields.ViewModelTextViewTextColorField;
import com.mvvm.framework.annotation.viewmodelfields.ViewModelViewVisibilityField;
import com.mvvm.framework.base.scanners.FieldTypeScanner;
import com.mvvm.framework.interfaces.BaseView;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by AboelelaA on 6/6/2017.
 *
 * This is the parent class for all ViewModels
 */

public class BaseViewModel<V extends BaseView, VM extends BaseViewModel>
{
    protected BaseView baseView;
    protected BaseViewModel childViewModel;

    protected HashMap<Field, PublishSubject<Object>> viewModelFieldsNotifiers = new HashMap<>();

    public void initView(@NonNull BaseView view, @NonNull BaseViewModel childViewModel) {
        this.baseView = view;
        this.childViewModel = childViewModel;
    }

    public void addField(Field viewModelField, PublishSubject subject) {
        viewModelFieldsNotifiers.put(viewModelField, subject);
    }

    public void releaseViewModelValues() {
        Observable.fromIterable(viewModelFieldsNotifiers.values())
                .subscribe(new Consumer<PublishSubject<Object>>()
                {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull PublishSubject<Object> objectPublishSubject) throws Exception {
                        objectPublishSubject.onComplete();
                    }
                });
    }

    protected void setViewModelFieldValue(BaseViewModel viewModel, String fieldName, final Object value) throws NoSuchFieldException, IllegalAccessException{
        Field field = viewModel.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(viewModel, value);

        // Notify change to reflect on UI
        Observable.just(viewModelFieldsNotifiers.get(field))
                .subscribe(new Consumer<PublishSubject<Object>>()
                {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull PublishSubject<Object> objectPublishSubject) throws Exception {
                        objectPublishSubject.onNext(value);
                    }
                });

    }

    /**
     * given the view model object, associate all values in ViewModel with views in BaseView
     *
     */
    public void associateViewModelWithViews() {
        // Loop all fields defined in ViewModel

        // Search for text views
        associateViewModelFieldValuesOfType(ViewModelTextField.class);

        // Search for visibility values in ViewModel
        associateViewModelFieldValuesOfType(ViewModelViewVisibilityField.class);

        // Search for text view text color values in ViewModel
        associateViewModelFieldValuesOfType(ViewModelTextViewTextColorField.class);

        // Search for fields reflecting Hint text in Edit View
        associateViewModelFieldValuesOfType(ViewModelHintEditTextField.class);

        // Search for fields reflecting drawable in Image View
        associateViewModelFieldValuesOfType(ViewModelImageViewField.class);

        // Search for fields reflecting check box value
        associateViewModelFieldValuesOfType(ViewModelCheckBoxField.class);

    }


    private void associateViewModelFieldValuesOfType(final Class<?> viewModelFieldAnnotation) {
        // search for text view fields
        getViewModelFieldsOfAnnotationType(viewModelFieldAnnotation)
                .subscribe(collectViewModelFields(viewModelFieldAnnotation));
    }


    private Consumer<List<Field>> collectViewModelFields(final Class<?> viewModelFieldAnnotation) {
        return new Consumer<List<Field>>()
        {
            @Override
            public void accept(@io.reactivex.annotations.NonNull final List<Field> viewModelFieldObjects) throws Exception {
                Observable.fromIterable(viewModelFieldObjects)
                        .subscribe(createFieldPublishSubjects(viewModelFieldAnnotation));
            }
        };
    }

    private Consumer<Field> createFieldPublishSubjects(final Class<?> viewModelFieldAnnotation) {
        return new Consumer<Field>()
        {
            @Override
            public void accept(@io.reactivex.annotations.NonNull final Field viewModelFieldObject) throws Exception {
                // get annotation of field
                final int fieldResId = getViewModelResIdOfAnnotationField(viewModelFieldObject, viewModelFieldAnnotation);
                viewModelFieldObject.setAccessible(true);

                // Create Publish Subject corresponding to this field
                PublishSubject fieldPublishSubject = PublishSubject.create();
                childViewModel.addField(viewModelFieldObject, fieldPublishSubject);

                getViewFieldOfResIdAndClass(getViewClass(viewModelFieldAnnotation), fieldResId)
                        .subscribe(setViewValueFromViewModel(fieldPublishSubject, viewModelFieldAnnotation));
            }
        };
    }

    private Class getViewClass(Class<?> viewModelFieldAnnotation) {
        if (viewModelFieldAnnotation.getName().equals(ViewModelCheckBoxField.class.getName())) {
            return CheckBox.class;
        }
        else if (viewModelFieldAnnotation.getName().equals(ViewModelHintEditTextField.class.getName())) {
            return EditText.class;
        }
        else if (viewModelFieldAnnotation.getName().equals(ViewModelTextField.class.getName())) {
            return TextView.class;
        }
        else if (viewModelFieldAnnotation.getName().equals(ViewModelTextViewTextColorField.class.getName())) {
            return TextView.class;
        }
        else if (viewModelFieldAnnotation.getName().equals(ViewModelViewVisibilityField.class.getName())) {
            return View.class;
        }
        else if (viewModelFieldAnnotation.getName().equals(ViewModelImageViewField.class.getName())) {
            return ImageView.class;
        }

        return null;
    }

    /**
     * Set value from ViewModel to corresponding view in View
     *
     * @param viewModelPublishSubject
     * @return
     */
    private Consumer<View> setViewValueFromViewModel(final PublishSubject viewModelPublishSubject,
                                                     final Class<?> viewModelFieldAnnotation) {
        return new Consumer<View>()
        {
            @Override
            public void accept(@io.reactivex.annotations.NonNull View view) throws Exception {
                if (viewModelFieldAnnotation.getName().equals(ViewModelCheckBoxField.class.getName())) {
                    // set check box value
                    ((PublishSubject<Boolean>)viewModelPublishSubject)
                            .subscribe(setCheckBoxValue((CheckBox) view));
                }
                else if (viewModelFieldAnnotation.getName().equals(ViewModelHintEditTextField.class.getName())) {
                    // set Hint text in edit text
                    ((PublishSubject<String>)viewModelPublishSubject)
                            .subscribe(setEditViewHintText((EditText) view));
                }
                else if (viewModelFieldAnnotation.getName().equals(ViewModelImageViewField.class.getName())) {
                    // set Image view drawable
                    ((PublishSubject<Integer>) viewModelPublishSubject)
                            .subscribe(setImageViewDrawable((ImageView) view));
                }
                else if (viewModelFieldAnnotation.getName().equals(ViewModelTextField.class.getName())) {
                    // set text
                    ((PublishSubject<String>)viewModelPublishSubject)
                            .subscribe(setTextViewText((TextView) view));
                }
                else if (viewModelFieldAnnotation.getName().equals(ViewModelTextViewTextColorField.class.getName())) {
                    //  set text color
                    ((PublishSubject<Integer>) viewModelPublishSubject)
                            .subscribe(setTextViewTextColor((TextView) view));
                }
                else if (viewModelFieldAnnotation.getName().equals(ViewModelViewVisibilityField.class.getName())) {
                    // set view visibility
                    ((PublishSubject<Integer>) viewModelPublishSubject)
                            .subscribe(setViewVisibility(view));
                }

            }
        };
    }

    private Consumer<String> setTextViewText(final TextView textView) {
        return new Consumer<String>()
        {
            @Override
            public void accept(@io.reactivex.annotations.NonNull String s) throws Exception {

                // set text to text view
                textView.setText(s);
            }
        };
    }

    private Consumer<Integer> setViewVisibility(final View view) {
        return new Consumer<Integer>()
        {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Integer visibility) throws Exception {

                // set view visibility
                switch (visibility.intValue()) {
                    case View.VISIBLE:
                    case View.GONE:
                    case View.INVISIBLE:
                    {
                        view.setVisibility(visibility.intValue());
                        break;
                    }
                }

            }
        };
    }

    private Consumer<Integer> setTextViewTextColor(final TextView textView) {
        return new Consumer<Integer>()
        {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Integer color) throws Exception {

                // set text view text color
                textView.setTextColor(color);
            }
        };
    }

    private Consumer<String> setEditViewHintText(final EditText editText) {
        return new Consumer<String>()
        {
            @Override
            public void accept(@io.reactivex.annotations.NonNull String val) throws Exception {

                // set hint text
                editText.setHint(val);

            }
        };
    }

    private Consumer<Integer> setImageViewDrawable(final ImageView imageView) {
        return new Consumer<Integer>()
        {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Integer drawableID) throws Exception {

                // set Image View drawable
                imageView.setImageDrawable(imageView.getContext().getDrawable(drawableID));
            }
        };
    }

    private Consumer<Boolean> setCheckBoxValue(final CheckBox checkBox) {
        return new Consumer<Boolean>()
        {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Boolean val) throws Exception {

                // set check box value
                checkBox.setChecked(val);
            }
        };
    }


    protected Observable<List<Field>> getViewModelFieldsOfAnnotationType(Class annotationType) {
        return Observable.just(new FieldTypeScanner().apply(childViewModel.getClass().getDeclaredFields(), annotationType))
                .filter(new Predicate<List<Field>>()
                {
                    @Override
                    public boolean test(@io.reactivex.annotations.NonNull List<Field> fields) throws Exception {
                        return fields.size() > 0;
                    }
                });
    }

    protected Observable getViewFieldOfResIdAndClass(final Class clz, final int resId) {
        return Observable.fromIterable(Arrays.asList(baseView.getClass().getDeclaredFields()))
                .filter(new Predicate<Field>()
                {
                    @Override
                    public boolean test(@io.reactivex.annotations.NonNull Field field) throws Exception {
                        // search for field with the same resource Id of view model resource Id
                        if (field != null && field.getType().equals(clz)) {
                            field.setAccessible(true);
                        }
                        else {
                            return false;
                        }

                        return ((View) field.get(baseView)).getId() == resId;

                    }
                })
                .map(new Function<Field, View>()
                {
                    @Override
                    public View apply(@io.reactivex.annotations.NonNull Field field) throws Exception {
                        return ((View) field.get(baseView));
                    }
                });
    }

    private int getViewModelResIdOfAnnotationField(Field field, Class<?> viewModelFieldAnnotation) {
        if (viewModelFieldAnnotation.getName().equals(ViewModelCheckBoxField.class.getName())) {
            return ((ViewModelCheckBoxField) field.getDeclaredAnnotations()[0]).value();
        }
        else if (viewModelFieldAnnotation.getName().equals(ViewModelHintEditTextField.class.getName())) {
            return ((ViewModelHintEditTextField) field.getDeclaredAnnotations()[0]).value();
        }
        else if (viewModelFieldAnnotation.getName().equals(ViewModelTextField.class.getName())) {
            return ((ViewModelTextField) field.getDeclaredAnnotations()[0]).value();
        }
        else if (viewModelFieldAnnotation.getName().equals(ViewModelTextViewTextColorField.class.getName())) {
            return ((ViewModelTextViewTextColorField) field.getDeclaredAnnotations()[0]).value();
        }
        else if (viewModelFieldAnnotation.getName().equals(ViewModelViewVisibilityField.class.getName())) {
            return ((ViewModelViewVisibilityField) field.getDeclaredAnnotations()[0]).value();
        }
        else if (viewModelFieldAnnotation.getName().equals(ViewModelImageViewField.class.getName())) {
            return ((ViewModelImageViewField) field.getDeclaredAnnotations()[0]).value();
        }

        return 0;

    }

}
