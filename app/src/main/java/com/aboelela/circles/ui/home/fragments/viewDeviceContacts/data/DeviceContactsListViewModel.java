package com.aboelela.circles.ui.home.fragments.viewDeviceContacts.data;

import com.aboelela.circles.R;
import com.aboelela.circles.ui.home.fragments.viewDeviceContacts.DeviceContactsListFragment;
import com.mvvm.framework.annotation.viewmodelfields.ViewModelViewVisibilityField;
import com.mvvm.framework.base.viewmodels.BaseViewModel;
import com.mvvm.framework.utils.LogUtil;

/**
 * Created by aboelela on 14/07/17.
 * View model for device contacts list fragment
 */

public class DeviceContactsListViewModel extends BaseViewModel<DeviceContactsListFragment, DeviceContactsListViewModel>
{
    private final String TAG = "DeviceContactsListViewModel";

    @ViewModelViewVisibilityField(R.id.fragment_device_contacts_empty_textView)
    private int emptyTextVisibility;

    public void setEmptyTextVisibility(int emptyTextVisibility) {
        try {
            setViewModelFieldValue(this, "emptyTextVisibility", emptyTextVisibility);
        }
        catch (Exception ex) {
            LogUtil.writeErrorLog(TAG, "Error setting the visibility of empty text view");
        }
    }
}
