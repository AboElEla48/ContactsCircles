package com.aboelela.circles.ui.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aboelela.circles.Constants;
import com.aboelela.circles.ui.ActivityNavigationManager;
import com.mvvm.framework.base.presenters.BasePresenter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by aboelela on 29/06/17.
 * Presenter for splash screen
 */

public class SplashPresenter extends BasePresenter<SplashActivity, SplashPresenter>
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run() {
                ActivityNavigationManager.startCirclesHomeActivity(getBaseView());
//                if (PreferencesManager.isCirclesDefined()) {
//                    ActivityNavigationManager.startCirclesHomeActivity(getBaseView());
//                }
//                else {
//                    ActivityNavigationManager.startAddCirclesActivity(getBaseView());
//                }

                getBaseView().finish();
            }
        }, Constants.Splash.SPLASH_DURATION);
    }
}
