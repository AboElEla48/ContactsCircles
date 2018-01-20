package com.aboelela.circles.ui.splash;

import com.aboelela.circles.R;
import com.foureg.baseframework.annotations.ContentViewId;
import com.foureg.baseframework.annotations.ViewPresenter;
import com.foureg.baseframework.ui.BaseActivity;

@ContentViewId(R.layout.activity_splash)
public class SplashActivity extends BaseActivity
{
    @ViewPresenter
    SplashPresenter splashPresenter;
}
