package com.aboelela.circles.ui.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aboelela.circles.constants.Constants;
import com.aboelela.circles.ui.ActivityNavigationManager;
import com.foureg.baseframework.ui.BaseViewPresenter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by aboelela on 29/06/17.
 * Presenter for splash screen
 */

public class SplashPresenter extends BaseViewPresenter<SplashActivity>
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run() {
                ActivityNavigationManager.startCirclesHomeActivity(getView());
                getView().finish();
            }
        }, Constants.Splash.SPLASH_DURATION);
    }
}
