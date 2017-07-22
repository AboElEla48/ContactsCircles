package com.aboelela.circles.ui.home.fragments.viewCircleContacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aboelela.circles.data.entities.Circle;
import com.aboelela.circles.ui.home.HomeActivityMessagesHelper;
import com.aboelela.circles.ui.home.fragments.viewCircleContacts.adapters.CircleContactsListAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.mvvm.framework.base.presenters.BasePresenter;
import com.mvvm.framework.utils.ContactsUtil;
import com.mvvm.framework.utils.LogUtil;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by AboelelaA on 7/13/2017.
 * Presenter for contacts list fragment
 */

class CircleContactsListPresenter extends BasePresenter<CircleContactsListFragment, CircleContactsListPresenter>
{
    private Circle circle;
    private final String TAG = "CircleContactsListPresenter";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the circle which you will show its contacts here
        circle = getBaseView().getArguments().getParcelable(CircleContactsListFragment.Bundle_Circle_Key);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseView().getContext());
        getBaseView().circleContactsRecyclerView.setLayoutManager(mLayoutManager);
        getBaseView().circleContactsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getBaseView().circleContactsRecyclerView.setHasFixedSize(true);

        //log the list of contacts in this circle
        Observable.fromIterable(circle.getCircleContacts())
                .subscribe(new Consumer<ContactsUtil.ContactModel>()
                {
                    @Override
                    public void accept(@NonNull ContactsUtil.ContactModel contactModel) throws Exception {
                        LogUtil.writeErrorLog(TAG, "Circle Contact: " + contactModel.toString());
                    }
                });

        // Fill the list with circle items
        getBaseView().circleContactsRecyclerView.setAdapter(new CircleContactsListAdapter(circle.getCircleContacts()));

        //Handle button to assign contact to circle
        RxView.clicks(getBaseView().assignContactBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        // View the device contacts fragment to let user select from there
                        HomeActivityMessagesHelper.sendMessageShowDeviceContacts(circle);
                    }
                });
    }
}
