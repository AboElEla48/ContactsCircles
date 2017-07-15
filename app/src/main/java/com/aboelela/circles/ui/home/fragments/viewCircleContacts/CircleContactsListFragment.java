package com.aboelela.circles.ui.home.fragments.viewCircleContacts;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.aboelela.circles.R;
import com.aboelela.circles.data.entities.Circle;
import com.mvvm.framework.annotation.InflateLayout;
import com.mvvm.framework.annotation.Presenter;
import com.mvvm.framework.base.views.BaseFragment;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CircleContactsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
@InflateLayout(R.layout.fragment_circle_contacts_list)
public class CircleContactsListFragment extends BaseFragment
{
    @Presenter
    CircleContactsListPresenter circleContactsListPresenter;

    @BindView(R.id.fragment_circle_contacts_circle_name_textView)
    TextView circleName;

    @BindView(R.id.fragment_circles_contacts_list_assign_contact_btn)
    FloatingActionButton assignContactBtn;

    static final String Bundle_Circle_Key = "Bundle_Circle_Key";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CircleContactsListFragment.
     */
    public static CircleContactsListFragment newInstance(Circle circle) {
        CircleContactsListFragment fragment = new CircleContactsListFragment();
        Bundle args = new Bundle();

        args.putParcelable(Bundle_Circle_Key, circle);

        fragment.setArguments(args);
        return fragment;
    }

}
