package com.aboelela.circles.ui.home.fragments.viewDeviceContacts;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aboelela.circles.R;
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
import com.mvvm.framework.utils.DialogMsgUtil;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getBaseView().getArguments() != null) {
            circleToAssignContacts = getBaseView().getArguments().getParcelable(DeviceContactsListFragment.Bundle_Circle_To_Assign_Key);
        }
        
        // Show waiting message for loading device contacts
        ProgressDialog loadingMsg = DialogMsgUtil.createProgressDialog(getBaseView().getContext(), "",
                getBaseView().getString(R.string.txt_contacts_loading_waiting_message));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseView().getContext());
        getBaseView().deviceContactsRecyclerView.setLayoutManager(mLayoutManager);
        getBaseView().deviceContactsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getBaseView().deviceContactsRecyclerView.setHasFixedSize(true);

        deviceContactsListAdapter = new DeviceContactsListAdapter(new ArrayList<>(deviceContactsModel.loadDeviceContacts().keySet()),
                circleToAssignContacts != null);
        getBaseView().deviceContactsRecyclerView.setAdapter(deviceContactsListAdapter);

        loadingMsg.dismiss();

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
                            HomeActivityMessagesHelper.sendMessageOpenCircleContacts(circleToAssignContacts);
                        }
                    });
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        checkEmptyContactsList();
    }

    private void checkEmptyContactsList() {
        int emptyTextVisibility = deviceContactsModel.getDeviceContacts().isEmpty() ? View.VISIBLE : View.GONE;
        deviceContactsListViewModel.setEmptyTextVisibility(emptyTextVisibility);
    }

}
