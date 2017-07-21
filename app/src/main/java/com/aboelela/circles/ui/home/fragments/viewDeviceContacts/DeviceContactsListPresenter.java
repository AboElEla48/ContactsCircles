package com.aboelela.circles.ui.home.fragments.viewDeviceContacts;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aboelela.circles.R;
import com.aboelela.circles.data.DeviceContactsModel;
import com.aboelela.circles.data.entities.Circle;
import com.aboelela.circles.ui.home.fragments.viewDeviceContacts.adapters.DeviceContactsListAdapter;
import com.aboelela.circles.ui.home.fragments.viewDeviceContacts.data.DeviceContactsListViewModel;
import com.jakewharton.rxbinding2.view.RxView;
import com.mvvm.framework.annotation.DataModel;
import com.mvvm.framework.annotation.ViewModel;
import com.mvvm.framework.base.presenters.BasePresenter;
import com.mvvm.framework.utils.DialogMsgUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
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

    @DataModel
    private DeviceContactsModel deviceContactsModel;

    private Circle circleToAssignContacts;

    private DeviceContactsListAdapter deviceContactsListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        circleToAssignContacts = getBaseView().getArguments().getParcelable(DeviceContactsListFragment.Bundle_Circle_To_Assign_Key);

        // Show waiting message for loading device contacts
        ProgressDialog loadingMsg = DialogMsgUtil.createProgressDialog(getBaseView().getContext(), "",
                getBaseView().getString(R.string.txt_contacts_loading_waiting_message));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseView().getContext());
        getBaseView().deviceContactsRecyclerView.setLayoutManager(mLayoutManager);
        getBaseView().deviceContactsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getBaseView().deviceContactsRecyclerView.setHasFixedSize(true);

        deviceContactsListAdapter = new DeviceContactsListAdapter(new ArrayList<>(deviceContactsModel.loadDeviceContacts().keySet()));
        getBaseView().deviceContactsRecyclerView.setAdapter(deviceContactsListAdapter);

        loadingMsg.dismiss();

        checkEmptyContactsList();

        // Handle event of save button
        RxView.clicks(getBaseView().saveSelectionBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        List<Integer> selectedItems = deviceContactsListAdapter.getSelectedItems();

                        Observable.fromIterable(selectedItems)
                                .subscribe(new Consumer<Integer>()
                                {
                                    @Override
                                    public void accept(@NonNull Integer integer) throws Exception {
                                        //TODO: Save contact to circle
//                                        deviceContactsModel.getDeviceContacts().get(integer);

                                        //TODO: dimiss
                                    }
                                });
                    }
                });

        // Handle event of cancel button
        RxView.clicks(getBaseView().cancelSelectionBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        // TODO: dismiss
                    }
                });
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
