package com.aboelela.circles.ui.home.fragments.viewContacts;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.aboelela.circles.R;
import com.aboelela.circles.data.entities.Circle;
import com.mvvm.framework.annotation.InflateLayout;
import com.mvvm.framework.base.views.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CircleContactsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
@InflateLayout(R.layout.fragment_circle_contacts_list)
public class CircleContactsListFragment extends BaseFragment
{
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CircleContactsListFragment.
     */
    public static CircleContactsListFragment newInstance(Circle circle) {
        CircleContactsListFragment fragment = new CircleContactsListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

}
