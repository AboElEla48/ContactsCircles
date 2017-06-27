package com.mvvm.framework.java.com.mvvm.common.base.creators.SingletonCreatorTest;

import com.mvvm.framework.annotation.singleton.Singleton;
import com.mvvm.framework.base.creators.FieldTypeCreator;
import com.mvvm.framework.base.scanners.FieldTypeScanner;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by aboelela on 24/06/17.
 * Test class for creating singleton object
 */
public class SingletonCreatorTest
{
    @Test
    public void getCreator_GetsSameObject() throws Exception {
        final SingletonTest1 test1 = new SingletonTest1();
        final SingletonTest2 test2 = new SingletonTest2();

        Observable.fromIterable(new FieldTypeScanner().apply(test1.getClass().getDeclaredFields(), Singleton.class))
                .map(new Function<Field, Object>()
                {
                    @Override
                    public Object apply(@NonNull Field field) throws Exception {
                        SingletonObject o = (SingletonObject) new FieldTypeCreator().createFieldObject(field);

                        field.setAccessible(true);
                        field.set(test1, o);

                        return o;

                    }
                }).blockingSubscribe();

        Observable.fromIterable(new FieldTypeScanner().apply(test2.getClass().getDeclaredFields(), Singleton.class))
                .map(new Function<Field, Object>()
                {
                    @Override
                    public Object apply(@NonNull Field field) throws Exception {
                        SingletonObject o = (SingletonObject) new FieldTypeCreator().createFieldObject(field);

                        field.setAccessible(true);
                        field.set(test2, o);

                        return o;
                    }
                }).blockingSubscribe();


        test1.setOddVal();
        Assert.assertTrue(test2.object.getVal() % 2 == 1);

        test2.setEvenVal();
        Assert.assertTrue(test1.object.getVal() % 2 == 0);
    }


}