package com.aboelela.circles.ui.home.addCircles;

import com.aboelela.circles.R;
import com.mvvm.framework.annotation.InflateLayout;
import com.mvvm.framework.annotation.Presenter;
import com.mvvm.framework.base.views.BaseActivity;

@InflateLayout(R.layout.activity_add_circle)
public class AddCircleActivity extends BaseActivity
{
    @Presenter
    AddCirclesPresenter addCirclesPresenter;
}
