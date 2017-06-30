package com.aboelela.circles.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aboelela.circles.R;
import com.aboelela.circles.ui.home.viewCircles.CirclesListFragment;
import com.mvvm.framework.base.presenters.BasePresenter;

/**
 * Created by aboelela on 29/06/17.
 * Presenter for home screen
 */

public class HomePresenter extends BasePresenter<HomeActivity, HomePresenter>
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getBaseView().getSupportFragmentManager().beginTransaction().replace(R.id.activity_home_frameLayout,
                CirclesListFragment.newInstance()).commit();

    }
}
