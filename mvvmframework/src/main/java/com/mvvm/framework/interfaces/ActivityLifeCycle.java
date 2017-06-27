package com.mvvm.framework.interfaces;

import android.content.Intent;

/**
 * Created by AboelelaA on 6/8/2017.
 * This interface for the lifecycle of activity
 */

public interface ActivityLifeCycle extends ViewLifeCycle
{
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
