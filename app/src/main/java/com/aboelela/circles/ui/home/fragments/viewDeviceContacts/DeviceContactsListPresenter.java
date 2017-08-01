package com.aboelela.circles.ui.home.fragments.viewDeviceContacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aboelela.circles.data.CirclesModel;
import com.aboelela.circles.data.DeviceContactsModel;
import com.aboelela.circles.data.entities.Circle;
import com.aboelela.circles.data.preferences.PreferencesManager;
import com.aboelela.circles.ui.home.HomeActivityMessagesHelper;
import com.aboelela.circles.ui.home.fragments.viewDeviceContacts.adapters.DeviceContactsListAdapter;
import com.aboelela.circles.ui.home.fragments.viewDeviceContacts.data.DeviceContactsListViewModel;
import com.jakewharton.rxbinding2.view.RxView;
import com.mvvm.framework.annotation.DataModel;
import com.mvvm.framework.annotation.ViewModel;
import com.mvvm.framework.annotation.singleton.Singleton;
import com.mvvm.framework.base.presenters.BasePresenter;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by aboelela on 14/07/17.
 * Presenter for multi select device contacts
 */

class DeviceContactsListPresenter extends BasePresenter<DeviceContactsListFragment, DeviceContactsListPresenter>
{
    @ViewModel
    private DeviceContactsListViewModel deviceContactsListViewModel;

    @Singleton
    @DataModel
    private DeviceContactsModel deviceContactsModel;

    @Singleton
    @DataModel
    private CirclesModel circlesModel;

    private Circle circleToAssignContacts;

    private DeviceContactsListAdapter deviceContactsListAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getBaseView().getArguments() != null) {
            circleToAssignContacts = getBaseView().getArguments().getParcelable(DeviceContactsListFragment.Bundle_Circle_To_Assign_Key);
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseView().getContext());
        getBaseView().deviceContactsRecyclerView.setLayoutManager(mLayoutManager);
        getBaseView().deviceContactsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getBaseView().deviceContactsRecyclerView.setHasFixedSize(true);

        // Show waiting message for loading device contacts
        deviceContactsListViewModel.setProgressBarVisibility(View.VISIBLE);

        deviceContactsModel.loadDeviceContacts(new Consumer<Object>()
        {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                deviceContactsListViewModel.setProgressBarVisibility(View.GONE);

                initListAfterLoadingContacts();
            }
        });


    }

    private void initListAfterLoadingContacts() {
        deviceContactsListAdapter = new DeviceContactsListAdapter(
                new ArrayList<>(deviceContactsModel.getDeviceContacts().keySet()),
                circleToAssignContacts != null);
        getBaseView().deviceContactsRecyclerView.setAdapter(deviceContactsListAdapter);


        checkEmptyContactsList();

        if (circleToAssignContacts == null) {
            deviceContactsListViewModel.setSelectionLayoutVisibility(View.GONE);
        }
        else {

            deviceContactsListViewModel.setSelectionLayoutVisibility(View.VISIBLE);

            // Handle event of save button
            RxView.clicks(getBaseView().saveSelectionBtn)
                    .subscribe(new Consumer<Object>()
                    {
                        @Override
                        public void accept(@NonNull Object o) throws Exception {
                            deviceContactsListAdapter.getSelectedContactsNames(new Consumer<String>()
                            {
                                @Override
                                public void accept(@NonNull String contactName) throws Exception {
                                    // Save contact to circle
                                    circleToAssignContacts.addCircleContact(deviceContactsModel.getDeviceContacts().get(contactName));
                                }
                            });

                            // save circle
                            PreferencesManager.saveCirclesList(circlesModel.getCircles());

                            // dismiss fragment
                            HomeActivityMessagesHelper.sendMessageFinishDeviceContactsFragment();
                            HomeActivityMessagesHelper.sendMessageOpenCircleContacts(circleToAssignContacts);
                        }
                    });


            // Handle event of cancel button
            RxView.clicks(getBaseView().cancelSelectionBtn)
                    .subscribe(new Consumer<Object>()
                    {
                        @Override
                        public void accept(@NonNull Object o) throws Exception {
                            // dismiss
                            HomeActivityMessagesHelper.sendMessageFinishDeviceContactsFragment();
                            HomeActivityMessagesHelper.sendMessageOpenCircleContacts(circleToAssignContacts);
                        }
                    });
        }
    }

    private void checkEmptyContactsList() {
        int emptyTextVisibility = deviceContactsModel.getDeviceContacts().isEmpty() ? View.VISIBLE : View.GONE;
        deviceContactsListViewModel.setEmptyTextVisibility(emptyTextVisibility);
    }

}
