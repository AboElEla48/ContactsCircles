package com.aboelela.circles;

import android.app.Application;

/**
 * Created by aboelela on 29/06/17.
 * This is the application class
 */

public class CirclesApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
    }

    public static CirclesApplication getInstance() {
        return appInstance;
    }

    private static CirclesApplication appInstance;
}
