package com.mvvm.framework.annotation.singleton;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by aboelela on 24/06/17.
 *
 * This is the annotation for fields declared as singleton
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Singleton
{
}
