package com.aboelela.circles.ui.home;

import com.aboelela.circles.R;
import com.mvvm.framework.annotation.InflateLayout;
import com.mvvm.framework.annotation.Presenter;
import com.mvvm.framework.base.views.BaseActivity;

@InflateLayout(R.layout.activity_home)
public class HomeActivity extends BaseActivity
{
    @Presenter
    HomePresenter homePresenter;
}
