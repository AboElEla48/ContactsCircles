package com.aboelela.circles.ui.home;

import android.support.design.widget.FloatingActionButton;

import com.aboelela.circles.R;
import com.mvvm.framework.annotation.InflateLayout;
import com.mvvm.framework.annotation.Presenter;
import com.mvvm.framework.base.views.BaseActivity;

import butterknife.BindView;

@InflateLayout(R.layout.activity_home)
public class HomeActivity extends BaseActivity
{
    @Presenter
    HomePresenter homePresenter;

    @BindView(R.id.activity_home_add_circle_btn)
    FloatingActionButton addCirlceBtn;
}
