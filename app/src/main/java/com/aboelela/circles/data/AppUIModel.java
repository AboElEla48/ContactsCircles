package com.aboelela.circles.data;

import com.aboelela.circles.constants.Constants;
import com.aboelela.circles.data.preferences.PreferencesManager;
import com.mvvm.framework.base.models.BaseModel;

/**
 * Created by aboelela on 23/07/17.
 * Model for saving the app preferences
 */

public class AppUIModel extends BaseModel
{
    public void setCirclesListViewingAsList() {
        PreferencesManager.saveCirclesListViewMode(Constants.App.ViewMode_List);
    }

    public void setCirclesListViewingAsGrid() {
        PreferencesManager.saveCirclesListViewMode(Constants.App.ViewMode_Grid);
    }

    public boolean isCirclesListViewingAsList() {
        return PreferencesManager.loadCirclesListViewMode() == Constants.App.ViewMode_List;
    }

    public boolean isCirclesListViewingAsgrid() {
        return PreferencesManager.loadCirclesListViewMode() == Constants.App.ViewMode_Grid;
    }

    public void setCircleContactsListViewingAsList() {
        PreferencesManager.saveCircleContactsListViewMode(Constants.App.ViewMode_List);
    }

    public void setCircleContactsListViewingAsGrid() {
        PreferencesManager.saveCircleContactsListViewMode(Constants.App.ViewMode_Grid);
    }

    public boolean isCircleContactsListViewingAsList() {
        return PreferencesManager.loadCircleContactsListViewMode() == Constants.App.ViewMode_List;
    }

    public boolean isCircleContactsListViewingAsgrid() {
        return PreferencesManager.loadCircleContactsListViewMode() == Constants.App.ViewMode_Grid;
    }
}
