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
    public enum CirclesListMode {
        Circles_As_List,
        Circles_As_Grid
    }

    String TAG = "CirclesListViewModel";

    @ViewModelViewVisibilityField(R.id.fragment_circles_list_empty_textView)
    Integer emptyTextVisibility;

    // Hold the viewing mode of the circles
    CirclesListMode circlesListMode = CirclesListMode.Circles_As_List;

    /**
     * set the visibility of empty text view
     * @param emptyTextVisibility
     */
    public void setEmptyTextVisibility(Integer emptyTextVisibility) {
        try {
            setViewModelFieldValue(this, "emptyTextVisibility", emptyTextVisibility);
        } catch (Exception ex) {
            LogUtil.writeErrorLog(TAG, "Error setting the visibility of empty text view");
        }
    }

    public Integer getEmptyTextVisibility() {
        return emptyTextVisibility;
    }
}
