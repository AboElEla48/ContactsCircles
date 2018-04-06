package com.aboelela.circles.ui.circles.fragment;

import com.aboelela.circles.model.CirclesModel;
import com.foureg.baseframework.annotations.DataModel;
import com.foureg.baseframework.ui.BaseViewPresenter;

/**
 * Created by AboelelaA on 1/30/2018.
 * This is the presenter class for circles list fragment
 */

class CirclesListFragmentPresenter extends BaseViewPresenter<CirclesListFragment>
{
    @DataModel
    CirclesModel circlesModel;

    private CirclesListAdapter circlesListAdapter;

    @Override
    public void initFragmentValues() {
        super.initFragmentValues();

        // fill grid with circles items
        circlesListAdapter = new CirclesListAdapter(getView().getContext(), circlesModel.loadCircles());
        getView().circlesGrid.setAdapter(circlesListAdapter);

    }
    
}
