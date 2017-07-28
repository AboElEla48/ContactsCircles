package com.aboelela.circles.ui.home.fragments.viewContact.data;

import com.aboelela.circles.R;
import com.aboelela.circles.ui.home.fragments.viewContact.ContactDetailsFragment;
import com.mvvm.framework.annotation.viewmodelfields.ViewModelTextField;
import com.mvvm.framework.annotation.viewmodelfields.ViewModelViewVisibilityField;
import com.mvvm.framework.base.viewmodels.BaseViewModel;
import com.mvvm.framework.utils.LogUtil;

/**
 * Created by aboelela on 26/07/17.
 * This is the view model for the contact details
 */

public class ContactDetailsViewModel extends BaseViewModel<ContactDetailsFragment, ContactDetailsViewModel>
{
    private static final String TAG = "ContactDetailsViewModel";

    @ViewModelTextField(R.id.fragment_contact_details_name_text_view)
    private String contactDetailsName;

    @ViewModelViewVisibilityField(R.id.fragment_contact_details_phones_layout)
    private Integer phonesSectionVisibility;

    @ViewModelViewVisibilityField(R.id.fragment_contact_details_emails_layout)
    private Integer emailsSectionVisibility;

    public void setContactDetailsName(String contactDetailsName) {
        setField("contactDetailsName", contactDetailsName);
    }

    public void setPhonesSectionVisibility(Integer phonesSectionVisibility) {
        setField("phonesSectionVisibility", phonesSectionVisibility);
    }

    public void setEmailsSectionVisibility(Integer emailsSectionVisibility) {
        setField("emailsSectionVisibility", emailsSectionVisibility);
    }


    private void setField(String field, Object val) {
        try {
            setViewModelFieldValue(this, field, val);
        }
        catch (Exception ex) {
            LogUtil.writeErrorLog(TAG, "Error setting field: " + field + " with value: " + val, ex);
        }

    }
}
