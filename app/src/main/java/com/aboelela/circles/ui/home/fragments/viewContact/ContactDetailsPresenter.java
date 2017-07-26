package com.aboelela.circles.ui.home.fragments.viewContact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.aboelela.circles.ui.home.fragments.viewContact.adapters.ContactDetailsEmailsListAdapter;
import com.aboelela.circles.ui.home.fragments.viewContact.adapters.ContactDetailsPhonesListAdapter;
import com.aboelela.circles.ui.home.fragments.viewContact.data.ContactDetailsViewModel;
import com.mvvm.framework.annotation.ViewModel;
import com.mvvm.framework.base.presenters.BasePresenter;
import com.mvvm.framework.utils.ContactsUtil;

/**
 * Created by AboelelaA on 7/24/2017.
 * Presenter for showing contact details
 */

class ContactDetailsPresenter extends BasePresenter<ContactDetailsFragment, ContactDetailsPresenter>
{
    @ViewModel
    private ContactDetailsViewModel contactDetailsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get contact model to display its details
        ContactsUtil.ContactModel contactModel = getBaseView().getArguments().getParcelable(ContactDetailsFragment.Bundle_Contact_Key);

        // set contact name
        contactDetailsViewModel.setContactDetailsName(contactModel.getContactName());

        // show/hide phones section
        if (contactModel.getPhones() != null && contactModel.getPhones().size() > 0) {
            contactDetailsViewModel.setPhonesSectionVisibility(View.VISIBLE);
            getBaseView().contactPhonesRecyclerView.setAdapter(new ContactDetailsPhonesListAdapter(contactModel.getPhones()));
        }
        else {
            contactDetailsViewModel.setPhonesSectionVisibility(View.GONE);
        }

        // show/hide emails section
        if (contactModel.getEmails() != null && contactModel.getEmails().size() > 0) {
            contactDetailsViewModel.setEmailsSectionVisibility(View.VISIBLE);
            getBaseView().contactEmailsRecyclerView.setAdapter(new ContactDetailsEmailsListAdapter(contactModel.getEmails()));
        }
        else {
            contactDetailsViewModel.setEmailsSectionVisibility(View.GONE);
        }
    }
}
