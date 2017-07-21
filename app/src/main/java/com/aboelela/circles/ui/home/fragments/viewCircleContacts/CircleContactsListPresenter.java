package com.aboelela.circles.ui.home.fragments.viewCircleContacts;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aboelela.circles.data.entities.Circle;
import com.aboelela.circles.ui.home.HomeActivityMessagesHelper;
import com.jakewharton.rxbinding2.view.RxView;
import com.mvvm.framework.base.presenters.BasePresenter;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by AboelelaA on 7/13/2017.
 * Presenter for contacts list fragment
 */

class CircleContactsListPresenter extends BasePresenter<CircleContactsListFragment, CircleContactsListPresenter>
{
    private Circle circle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the circle which you will show its contacts here
        circle = getBaseView().getArguments().getParcelable(CircleContactsListFragment.Bundle_Circle_Key);

        //Handle button to assign contact to circle
        RxView.clicks(getBaseView().assignContactBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        // View the device contacts fragment to let user select from there
                        HomeActivityMessagesHelper.showDeviceContacts(circle);
                    }
                });
    }
}
