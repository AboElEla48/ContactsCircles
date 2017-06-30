package com.aboelela.circles.ui.home.viewCircles;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aboelela.circles.R;
import com.mvvm.framework.annotation.InflateLayout;
import com.mvvm.framework.annotation.Presenter;
import com.mvvm.framework.base.views.BaseFragment;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CirclesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@InflateLayout(R.layout.fragment_circles_list)
public class CirclesListFragment extends BaseFragment
{
    @Presenter
    CirclesListPresenter circlesListPresenter;

    @BindView(R.id.fragment_circles_list_circles_recyclerView)
    RecyclerView circlesRecyclerView;

    @BindView(R.id.fragment_circles_list_empty_textView)
    View circlesEmptyText;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    public static CirclesListFragment newInstance() {
        CirclesListFragment fragment = new CirclesListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

}
