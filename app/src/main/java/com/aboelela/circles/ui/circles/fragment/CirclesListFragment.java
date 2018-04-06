package com.aboelela.circles.ui.circles.fragment;

import android.widget.GridView;

import com.aboelela.circles.R;
import com.foureg.baseframework.annotations.ContentViewId;
import com.foureg.baseframework.annotations.ViewId;
import com.foureg.baseframework.annotations.ViewPresenter;
import com.foureg.baseframework.ui.BaseFragment;

/**
 Fragment to display all circles
 *
 */

@ContentViewId(R.layout.fragment_circles_list)
public class CirclesListFragment extends BaseFragment
{
    @ViewPresenter
    CirclesListFragmentPresenter circlesListFragmentPresenter;

    @ViewId(R.id.fragment_circles_grid)
    GridView circlesGrid;
}
