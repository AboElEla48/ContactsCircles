package com.aboelela.circles.ui.home.fragments.viewCircles.data;

import com.aboelela.circles.R;
import com.aboelela.circles.ui.home.fragments.viewCircles.CirclesListFragment;
import com.mvvm.framework.annotation.viewmodelfields.ViewModelViewVisibilityField;
import com.mvvm.framework.base.viewmodels.BaseViewModel;
import com.mvvm.framework.utils.LogUtil;

/**
 * Created by aboelela on 30/06/17.
 * Define the view model for circles list fragment
 */

public class CirclesListViewModel extends BaseViewModel<CirclesListFragment, CirclesListViewModel>
{
    private String TAG = "CirclesListViewModel";

    @ViewModelViewVisibilityField(R.id.fragment_circles_list_empty_textView)
    private Integer emptyTextVisibility;

    @ViewModelViewVisibilityField(R.id.fragment_circles_list_progress_layout)
    private Integer loadingLayoutVisibility;

    /**
     * set the visibility of empty text view
     * @param emptyTextVisibility: the text to be set in case empty items list/grid
     */
    public void setEmptyTextVisibility(Integer emptyTextVisibility) {
        setField("emptyTextVisibility", emptyTextVisibility);
    }

    public void setLoadingLayoutVisibility(Integer loadingLayoutVisibility) {
        setField("loadingLayoutVisibility", loadingLayoutVisibility);
    }

    private void setField(String field, Object val) {
        try {
            setViewModelFieldValue(this, field, val);
        } catch (Exception ex) {
            LogUtil.writeErrorLog(TAG, "Error setting the visibility of empty text view");
        }
    }
}
