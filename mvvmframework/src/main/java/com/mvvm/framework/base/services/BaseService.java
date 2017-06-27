package com.mvvm.framework.base.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by AboelelaA on 6/6/2017.
 *
 * This is the parent class for all services
 */

public class BaseService extends Service
{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
