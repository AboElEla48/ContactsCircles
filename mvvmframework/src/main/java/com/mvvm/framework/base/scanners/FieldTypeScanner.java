package com.mvvm.framework.base.scanners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by AboelelaA on 6/7/2017.
 * This scanner is to scan given fields and get the required fields with given annotation type
 */

public class FieldTypeScanner implements BiFunction<Field[], Class<?>, Object>
{
    @Override
    public List<Field> apply(Field[] fieldsArr, Class<?> requiredAnnotation) throws UnsupportedOperationException {

        List<Field> fields = Arrays.asList(fieldsArr);
        final ArrayList<Field> resultFields = new ArrayList<>();

        // Search annotation on all fields to get the required annotation class
        for (final Field field : fields) {
            Observable.just(isFieldAnnotatedBy(field, requiredAnnotation))
                    .filter(new Predicate<Boolean>()
                    {
                        @Override
                        public boolean test(@NonNull Boolean aBoolean) throws Exception {
                            return aBoolean;
                        }
                    })
                    .subscribe(new Consumer<Boolean>()
                    {
                        @Override
                        public void accept(@NonNull Boolean aBoolean) throws Exception {
                            resultFields.add(field);
                        }
                    });
        }
        return resultFields;
    }

    /**
     * Check for one field if the required annotation is declared for it or not
     * @param field: The field to check its annotation
     * @param requiredAnnotation: the required annotation class
     */
    public boolean isFieldAnnotatedBy(Field field, Class<?> requiredAnnotation) {
        Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
        for (Annotation annotation : fieldAnnotations) {
            if (annotation.annotationType().getName().equals(requiredAnnotation.getName())) {
                return true;
            }
        }

        return false;
    }
}
