package com.aboelela.circles.ui.home.fragments.viewCircles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.aboelela.circles.ui.home.fragments.viewCircles.data.CirclesListViewModel;
import com.mvvm.framework.annotation.ViewModel;
import com.mvvm.framework.base.presenters.BasePresenter;

/**
 * Created by aboelela on 30/06/17.
 * Presenter for viewing the list of fragments
 */

public class CirclesListPresenter extends BasePresenter<CirclesListFragment, CirclesListPresenter>
{
    @ViewModel
    CirclesListViewModel circlesListViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        circlesListViewModel.setEmptyTextVisibility(View.VISIBLE);
    }
}
