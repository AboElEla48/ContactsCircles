package com.aboelela.circles.ui.home.fragments.viewContacts;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aboelela.circles.data.entities.Circle;
import com.mvvm.framework.base.presenters.BasePresenter;

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

        circle = getBaseView().getArguments().getParcelable(CircleContactsListFragment.Bundle_Circle_Key);
    }
}
