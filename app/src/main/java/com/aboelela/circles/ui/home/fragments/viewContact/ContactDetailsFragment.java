package com.aboelela.circles.ui.home.fragments.viewContact;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.aboelela.circles.R;
import com.mvvm.framework.annotation.InflateLayout;
import com.mvvm.framework.base.views.BaseFragment;
import com.mvvm.framework.utils.ContactsUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
@InflateLayout(R.layout.fragment_contact_details)
public class ContactDetailsFragment extends BaseFragment
{
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContactDetailsFragment.
     */
    public static ContactDetailsFragment newInstance(ContactsUtil.ContactModel contactModel) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();


        fragment.setArguments(args);
        return fragment;
    }

}
