package com.aboelela.circles.ui.splash;

import com.aboelela.circles.R;
import com.mvvm.framework.annotation.InflateLayout;
import com.mvvm.framework.annotation.Presenter;
import com.mvvm.framework.base.views.BaseActivity;

@InflateLayout(R.layout.activity_splash)
public class SplashActivity extends BaseActivity
{
    @Presenter
    SplashPresenter splashPresenter;
}
