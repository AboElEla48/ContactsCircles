package com.aboelela.circles.ui.home.fragments.viewCircles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.BaseAdapter;

import com.aboelela.circles.constants.CirclesMessages;
import com.aboelela.circles.data.AppUIModel;
import com.aboelela.circles.data.CirclesModel;
import com.aboelela.circles.ui.home.HomeActivityMessagesHelper;
import com.aboelela.circles.ui.home.fragments.viewCircles.adapters.CirclesGridAdapter;
import com.aboelela.circles.ui.home.fragments.viewCircles.adapters.CirclesListAdapter;
import com.aboelela.circles.ui.home.fragments.viewCircles.data.CirclesListViewModel;
import com.jakewharton.rxbinding2.view.RxView;
import com.mvvm.framework.annotation.DataModel;
import com.mvvm.framework.annotation.ViewModel;
import com.mvvm.framework.annotation.singleton.Singleton;
import com.mvvm.framework.base.presenters.BasePresenter;
import com.mvvm.framework.messaging.CustomMessage;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

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

    @Singleton
    @DataModel
    private AppUIModel appUIModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseView().getContext());
        getBaseView().circlesRecyclerView.setLayoutManager(mLayoutManager);
        getBaseView().circlesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getBaseView().circlesRecyclerView.setHasFixedSize(true);

        getBaseView().circlesRecyclerView.setAdapter(new CirclesListAdapter(circlesModel));
        getBaseView().circlesGridView.setAdapter(new CirclesGridAdapter(circlesModel));

        if(appUIModel.isCirclesListViewingAsList()) {
            viewAsList();
        }
        else {
            viewAsGrid();
        }

        RxView.clicks(getBaseView().viewAsGridBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        viewAsGrid();
                    }
                });


        RxView.clicks(getBaseView().viewAsListBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        viewAsList();
                    }
                });

        // Handle click on Add circle button
        RxView.clicks(getBaseView().addCircleBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        HomeActivityMessagesHelper.sendMessageAddCircle();

                    }
                });

        checkEmptyList();
    }

    private void viewAsGrid() {
        getBaseView().circlesGridView.setVisibility(View.VISIBLE);
        getBaseView().circlesRecyclerView.setVisibility(View.GONE);
        appUIModel.setCirclesListViewingAsGrid();

        checkEmptyList();
    }

    private void viewAsList() {
        getBaseView().circlesRecyclerView.setVisibility(View.VISIBLE);
        getBaseView().circlesGridView.setVisibility(View.GONE);
        appUIModel.setCirclesListViewingAsList();

        checkEmptyList();
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
        ((BaseAdapter)getBaseView().circlesGridView.getAdapter()).notifyDataSetChanged();
        checkEmptyList();
    }


}
