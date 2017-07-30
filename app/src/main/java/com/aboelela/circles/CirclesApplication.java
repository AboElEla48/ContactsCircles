package com.aboelela.circles;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by aboelela on 29/06/17.
 * This is the application class
 */

public class CirclesApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        appInstance = this;
    }

    public static CirclesApplication getInstance() {
        return appInstance;
    }

    private static CirclesApplication appInstance;
}
