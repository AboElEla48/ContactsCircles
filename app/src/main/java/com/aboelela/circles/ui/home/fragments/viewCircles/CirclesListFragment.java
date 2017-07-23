package com.aboelela.circles.ui.home.fragments.viewCircles;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

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

    @BindView(R.id.fragment_circles_grid_circles_gridView)
    GridView circlesGridView;

    @BindView(R.id.fragment_circles_view_as_grid_btn)
    ImageButton viewAsGridBtn;

    @BindView(R.id.fragment_circles_view_as_list_btn)
    ImageButton viewAsListBtn;

    @BindView(R.id.fragment_circles_list_empty_textView)
    View emptyTextView;

    @BindView(R.id.fragment_circles_list_add_circle_btn)
    FloatingActionButton addCircleBtn;

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
