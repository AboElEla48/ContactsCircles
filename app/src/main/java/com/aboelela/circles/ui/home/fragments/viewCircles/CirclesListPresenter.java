package com.aboelela.circles.ui.home.fragments.viewCircles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aboelela.circles.constants.CirclesMessages;
import com.aboelela.circles.data.CirclesModel;
import com.aboelela.circles.ui.home.fragments.viewCircles.data.CirclesListViewModel;
import com.mvvm.framework.annotation.DataModel;
import com.mvvm.framework.annotation.ViewModel;
import com.mvvm.framework.annotation.singleton.Singleton;
import com.mvvm.framework.base.presenters.BasePresenter;
import com.mvvm.framework.messaging.CustomMessage;

/**
 * Created by aboelela on 30/06/17.
 * Presenter for viewing the list of fragments
 */

class CirclesListPresenter extends BasePresenter<CirclesListFragment, CirclesListPresenter>
{
    @ViewModel
    private CirclesListViewModel circlesListViewModel;

    @Singleton
    @DataModel
    private CirclesModel circlesModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseView().getContext());
        getBaseView().circlesRecyclerView.setLayoutManager(mLayoutManager);
        getBaseView().circlesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getBaseView().circlesRecyclerView.setHasFixedSize(true);

        getBaseView().circlesRecyclerView.setAdapter(new CirclesListAdapter(circlesModel));


    }

    @Override
    public void onResume() {
        super.onResume();

        checkEmptyList();
    }

    private void checkEmptyList() {
        if (circlesModel.getCircles().size() == 0) {
            circlesListViewModel.setEmptyTextVisibility(View.VISIBLE);
        }
        else {
            circlesListViewModel.setEmptyTextVisibility(View.GONE);
        }
    }

    @Override
    public void onMessageReceived(CustomMessage msg) {
        super.onMessageReceived(msg);
        switch (msg.getMessageId()) {
            case CirclesMessages.MSGID_Refresh_Circles_List: {
                updateCirclesList();
                break;
            }
        }
    }

    private void updateCirclesList() {
        getBaseView().circlesRecyclerView.getAdapter().notifyDataSetChanged();
        checkEmptyList();
    }
}
