package com.aboelela.circles.ui.home.fragments.viewContact;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

        // set contact avatar
        if(contactModel.getBitmap() != null) {
            getBaseView().avatarImageView.setImageDrawable(new BitmapDrawable(getBaseView().getResources(), contactModel.getBitmap()));
        }

        // show/hide phones section
        if (contactModel.getPhones() != null && contactModel.getPhones().size() > 0) {
            contactDetailsViewModel.setPhonesSectionVisibility(View.VISIBLE);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseView().getContext());
            getBaseView().contactPhonesRecyclerView.setLayoutManager(mLayoutManager);
            getBaseView().contactPhonesRecyclerView.setItemAnimator(new DefaultItemAnimator());
            getBaseView().contactPhonesRecyclerView.setHasFixedSize(true);

            getBaseView().contactPhonesRecyclerView.setAdapter(new ContactDetailsPhonesListAdapter(contactModel.getPhones()));
        }
        else {
            contactDetailsViewModel.setPhonesSectionVisibility(View.GONE);
        }

        // show/hide emails section
        if (contactModel.getEmails() != null && contactModel.getEmails().size() > 0) {
            contactDetailsViewModel.setEmailsSectionVisibility(View.VISIBLE);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseView().getContext());
            getBaseView().contactEmailsRecyclerView.setLayoutManager(mLayoutManager);
            getBaseView().contactEmailsRecyclerView.setItemAnimator(new DefaultItemAnimator());
            getBaseView().contactEmailsRecyclerView.setHasFixedSize(true);

            getBaseView().contactEmailsRecyclerView.setAdapter(new ContactDetailsEmailsListAdapter(contactModel.getEmails()));
        }
        else {
            contactDetailsViewModel.setEmailsSectionVisibility(View.GONE);
        }
    }
}
