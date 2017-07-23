package com.aboelela.circles.ui.home.fragments.viewCircleContacts.data;

import com.aboelela.circles.R;
import com.aboelela.circles.ui.home.fragments.viewCircleContacts.CircleContactsListFragment;
import com.mvvm.framework.annotation.viewmodelfields.ViewModelViewVisibilityField;
import com.mvvm.framework.base.viewmodels.BaseViewModel;
import com.mvvm.framework.utils.LogUtil;

/**
 * Created by aboelela on 23/07/17.
 * View model for circle contacts list
 */

public class CircleContactsListViewModel extends BaseViewModel<CircleContactsListFragment, CircleContactsListViewModel>
{
    private String TAG = "CircleContactsListViewModel";

    @ViewModelViewVisibilityField(R.id.fragment_circle_contacts_list_empty_textView)
    private Integer emptyTextVisibility;

    /**
     * set the visibility of empty text view
     * @param emptyTextVisibility: the text to be set in case empty items list/grid
     */
    public void setEmptyTextVisibility(Integer emptyTextVisibility) {
        try {
            setViewModelFieldValue(this, "emptyTextVisibility", emptyTextVisibility);
        } catch (Exception ex) {
            LogUtil.writeErrorLog(TAG, "Error setting the visibility of empty text view");
        }
    }

    public Integer getEmptyTextVisibility() {
        return emptyTextVisibility;
    }
}
