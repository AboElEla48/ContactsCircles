package com.aboelela.circles.ui.home.fragments.viewDeviceContacts;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.aboelela.circles.R;
import com.aboelela.circles.data.entities.Circle;
import com.mvvm.framework.annotation.InflateLayout;
import com.mvvm.framework.annotation.Presenter;
import com.mvvm.framework.base.views.BaseFragment;

import butterknife.BindView;

/**
 * This Fragment to show list of device contacts and multi select from it
 */
@InflateLayout(R.layout.fragment_device_contacts_list)
public class DeviceContactsListFragment extends BaseFragment
{
    @Presenter
    DeviceContactsListPresenter deviceContactsListPresenter;

    @BindView(R.id.fragment_device_contacts_recyclerView)
    RecyclerView deviceContactsRecyclerView;

    @BindView(R.id.fragment_device_contacts_empty_textView)
    TextView deviceContactsEmptyListTextView;

    static String Bundle_Circle_To_Assign_Key  = "Bundle_Circle_To_Assign_Key";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment DeviceContactsListFragment.
     */
    public static DeviceContactsListFragment newInstance(Circle circle) {
        DeviceContactsListFragment fragment = new DeviceContactsListFragment();
        Bundle args = new Bundle();

        args.putParcelable(Bundle_Circle_To_Assign_Key, circle);

        fragment.setArguments(args);
        return fragment;
    }

}