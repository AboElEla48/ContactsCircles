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

    @ViewModelViewVisibilityField(R.id.fragment_device_contacts_selection_layout)
    private int selectionLayoutVisibility;

    @ViewModelViewVisibilityField(R.id.fragment_device_contacts_list_progress_layout)
    private int progressBarVisibility;

    public void setEmptyTextVisibility(int emptyTextVisibility) {
        try {
            setViewModelFieldValue(this, "emptyTextVisibility", emptyTextVisibility);
        }
        catch (Exception ex) {
            LogUtil.writeErrorLog(TAG, "Error setting the visibility of empty text view");
        }
    }

    public void setProgressBarVisibility(int progressBarVisibility) {
        try
        {
            setViewModelFieldValue(this, "progressBarVisibility", progressBarVisibility);
        }
        catch (Exception ex) {
            LogUtil.writeErrorLog(TAG, "Error setting the visibility of progress bar");
        }
    }

    public void setSelectionLayoutVisibility(int selectionLayoutVisibility) {
        try {
            setViewModelFieldValue(this, "selectionLayoutVisibility", selectionLayoutVisibility);
        }
        catch (Exception ex) {
            LogUtil.writeErrorLog(TAG, "Error setting the visibility of selection view");
        }
    }
}
