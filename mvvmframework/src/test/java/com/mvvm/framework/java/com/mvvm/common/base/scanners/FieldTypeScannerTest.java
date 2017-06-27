package com.mvvm.framework.java.com.mvvm.common.base.scanners;

import com.mvvm.framework.annotation.DataModel;
import com.mvvm.framework.annotation.Presenter;
import com.mvvm.framework.annotation.ViewModel;
import com.mvvm.framework.base.scanners.FieldTypeScanner;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by AboelelaA on 6/7/2017.
 * This is a test class for fields scanner according to its annotation type
 */
public class FieldTypeScannerTest
{
    @DataModel
    Long obj2 = 26L;

    @ViewModel
    Integer obj3 = 17;

    @Presenter
    String obj1 = "obj1_val_not_used";

    @Test
    public void apply_NoReturnNull() throws Exception {

        // Declare
        FieldTypeScanner fieldTypeScanner = new FieldTypeScanner();

        Field[] fields = this.getClass().getDeclaredFields();

        apply_Presenter(fieldTypeScanner, fields);
        apply_Models(fieldTypeScanner, fields);
        apply_ViewModel(fieldTypeScanner, fields);

    }


    private void apply_Presenter(FieldTypeScanner fieldTypeScanner, Field[] fields) throws Exception
    {
        // Extract Presenter
        Observable.fromIterable(fieldTypeScanner.apply(fields, Presenter.class))
                .map(new Function<Field, Object>()
                {
                    @Override
                    public Object apply(@NonNull Field presenterField) throws Exception {

                        Constructor<?> presenterConstructor = presenterField.getType().getDeclaredConstructor();
                        presenterConstructor.setAccessible(true);
                        return presenterConstructor.newInstance();
                    }
                })
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@NonNull Object presenterObject) throws Exception {
                        Assert.assertTrue(presenterObject.getClass().getSimpleName().equals("String"));
                    }
                });
    }

    private void apply_Models(FieldTypeScanner fieldTypeScanner, Field[] fields) throws Exception
    {
        // Extract Models
        Observable.fromIterable(fieldTypeScanner.apply(fields, DataModel.class))
                .map(new Function<Field, Object>()
                {
                    @Override
                    public Object apply(@NonNull Field modelField) throws Exception {

                        Constructor<?> presenterConstructor = modelField.getType().getDeclaredConstructors()[0];
                        presenterConstructor.setAccessible(true);
                        return presenterConstructor.newInstance(10L);
                    }
                })
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@NonNull Object modelObject) throws Exception {
                        Assert.assertTrue(modelObject.getClass().getSimpleName().equals("Long"));
                    }
                });
    }

    private void apply_ViewModel(FieldTypeScanner fieldTypeScanner, Field[] fields) throws Exception
    {
        // Extract ViewModels
        Observable.fromIterable(fieldTypeScanner.apply(fields, ViewModel.class))
                .map(new Function<Field, Object>()
                {
                    @Override
                    public Object apply(@NonNull Field viewModelField) throws Exception {

                        Constructor<?> presenterConstructor = viewModelField.getType().getDeclaredConstructors()[0];
                        presenterConstructor.setAccessible(true);
                        return presenterConstructor.newInstance(20);
                    }
                })
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@NonNull Object viewModelObject) throws Exception {
                        Assert.assertTrue(viewModelObject.getClass().getSimpleName().equals("Integer"));
                    }
                });
    }


}