package com.aboelela.circles.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aboelela.circles.R;
import com.aboelela.circles.ui.home.fragments.addCircle.NewCircleDialogFragment;
import com.aboelela.circles.ui.home.fragments.viewCircles.CirclesListFragment;
import com.jakewharton.rxbinding2.view.RxView;
import com.mvvm.framework.base.presenters.BasePresenter;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by aboelela on 29/06/17.
 * Presenter for home screen
 */

public class HomePresenter extends BasePresenter<HomeActivity, HomePresenter>
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the view list of circles fragment
        getBaseView().getSupportFragmentManager().beginTransaction().replace(R.id.activity_home_frameLayout,
                CirclesListFragment.newInstance()).commit();

        // Handle click on Add cirlce button
        RxView.clicks(getBaseView().addCirlceBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        NewCircleDialogFragment circleDialogFragment = NewCircleDialogFragment.newInstance();
                        circleDialogFragment.show(getBaseView().getSupportFragmentManager(), "");
                    }
                });
    }
}
