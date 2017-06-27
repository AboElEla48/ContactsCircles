package com.mvvm.framework.base.creators;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * Created by aboelela on 24/06/17.
 * <p>
 * This is the creator class for fields annotated as Singleton
 */

public final class SingletonCreator
{
    private SingletonCreator() {
    }

    /**
     * Get singleton object to SingletonCreator
     * @return: singleton object to SingletonCreator
     */
    public static SingletonCreator getCreator() {
        if(instance == null) {
            instance = new SingletonCreator();
        }
        return instance;
    }

    public Object getInstance(Class<?> cls) throws Exception{
        Object singletonObject = singletonObjectsList.get(cls);
        if(singletonObject == null) {
            // this object wasn't created before
//            try {
                Constructor<?> classConstructor = cls.getDeclaredConstructor();
                classConstructor.setAccessible(true);
                singletonObject = classConstructor.newInstance();

                singletonObjectsList.put(cls, singletonObject);

        }
        return singletonObject;
    }

    /**
     * Remove singleton object from singleton cache
     * @param cls
     */
    public void removeInstance(Class<?> cls) {
        singletonObjectsList.remove(cls);
    }

    private static SingletonCreator instance;
    private HashMap<Class, Object> singletonObjectsList = new HashMap<>();
}
