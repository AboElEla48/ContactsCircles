package com.aboelela.circles.ui.home.fragments.viewDeviceContacts;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.aboelela.circles.R;
import com.aboelela.circles.data.DeviceContactsModel;
import com.aboelela.circles.data.entities.Circle;
import com.aboelela.circles.ui.home.fragments.viewDeviceContacts.adapters.DeviceContactsListAdapter;
import com.aboelela.circles.ui.home.fragments.viewDeviceContacts.data.DeviceContactsListViewModel;
import com.mvvm.framework.annotation.DataModel;
import com.mvvm.framework.annotation.ViewModel;
import com.mvvm.framework.base.presenters.BasePresenter;
import com.mvvm.framework.utils.DialogMsgUtil;

/**
 * Created by aboelela on 14/07/17.
 * Presenter for multi select device contacts
 */

public class DeviceContactsListPresenter extends BasePresenter<DeviceContactsListFragment, DeviceContactsListPresenter>
{
    @ViewModel
    private DeviceContactsListViewModel deviceContactsListViewModel;

    @DataModel
    DeviceContactsModel deviceContactsModel;

    private Circle circleToAssignContacts;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        circleToAssignContacts = getBaseView().getArguments().getParcelable(DeviceContactsListFragment.Bundle_Circle_To_Assign_Key);

        // Show waiting message for loading device contacts
        ProgressDialog loadingMsg = DialogMsgUtil.createProgressDialog(getBaseView().getContext(), "",
                getBaseView().getString(R.string.txt_contacts_loading_waiting_message));

        DeviceContactsListAdapter deviceContactsListAdapter = new DeviceContactsListAdapter(deviceContactsModel.loadDeviceContacts());
        getBaseView().deviceContactsRecyclerView.setAdapter(deviceContactsListAdapter);

        loadingMsg.dismiss();

        checkEmptyContactsList();
    }

    @Override
    public void onResume() {
        super.onResume();

        checkEmptyContactsList();
    }

    void checkEmptyContactsList() {
        int emptyTextVisibility = deviceContactsModel.getDeviceContacts().isEmpty() ? View.GONE : View.VISIBLE;
        deviceContactsListViewModel.setEmptyTextVisibility(emptyTextVisibility);
    }

}
