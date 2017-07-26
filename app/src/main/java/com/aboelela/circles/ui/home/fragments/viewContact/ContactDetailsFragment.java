package com.aboelela.circles.ui.home.fragments.viewContact;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aboelela.circles.R;
import com.mvvm.framework.annotation.InflateLayout;
import com.mvvm.framework.annotation.Presenter;
import com.mvvm.framework.base.views.BaseFragment;
import com.mvvm.framework.utils.ContactsUtil;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
@InflateLayout(R.layout.fragment_contact_details)
public class ContactDetailsFragment extends BaseFragment
{
    @Presenter
    ContactDetailsPresenter contactDetailsPresenter;

    @BindView(R.id.fragment_contact_details_name_text_view)
    TextView contactNameTextView;

    @BindView(R.id.fragment_contact_details_phones_layout)
    View phonesSection;

    @BindView(R.id.fragment_contact_details_emails_layout)
    View emailsSection;

    @BindView(R.id.fragment_contact_details_phones_recycler_view)
    RecyclerView contactPhonesRecyclerView;

    @BindView(R.id.fragment_contact_details_emails_recycler_view)
    RecyclerView contactEmailsRecyclerView;

    static final String Bundle_Contact_Key = "Bundle_Contact_Key";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContactDetailsFragment.
     */
    public static ContactDetailsFragment newInstance(ContactsUtil.ContactModel contactModel) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();

        args.putParcelable(Bundle_Contact_Key, contactModel);

        fragment.setArguments(args);
        return fragment;
    }

}
