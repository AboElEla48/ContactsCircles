package com.aboelela.circles.ui.home.fragments.addCircle;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aboelela.circles.data.CirclesModel;
import com.aboelela.circles.data.entities.Circle;
import com.aboelela.circles.ui.home.HomeActivityMessagesHelper;
import com.jakewharton.rxbinding2.view.RxView;
import com.mvvm.framework.annotation.DataModel;
import com.mvvm.framework.annotation.singleton.Singleton;
import com.mvvm.framework.base.presenters.BasePresenter;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by aboelela on 30/06/17.
 * Presenter for new circle dialog fragment
 */

class NewCircleDialogPresenter extends BasePresenter<NewCircleDialogFragment, NewCircleDialogPresenter>
{
    @Singleton
    @DataModel
    CirclesModel circlesModel;

    private int editCircleID = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getBaseView().getArguments() != null) {
            editCircleID = getBaseView().getArguments().getInt(NewCircleDialogFragment.Bundle_Circle_ID, -1);
            if (editCircleID > -1) {
                circlesModel.searchForCircle(editCircleID, new Consumer<Circle>()
                {
                    @Override
                    public void accept(@NonNull Circle circle) throws Exception {
                        getBaseView().circleNameEditText.setText(circle.getName());
                    }
                });

            }
        }

        RxView.clicks(getBaseView().saveBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {

                        String circleName = getBaseView().circleNameEditText.getText().toString();

                        // save to data store
                        if (editCircleID == -1) {
                            // this is a new item
                            circlesModel.addCircle(circleName);
                        } else {
                            // edit circle name
                            circlesModel.editCircleName(editCircleID, circleName);
                        }

                        // Notify list fragment to refresh
                        HomeActivityMessagesHelper.sendMessageRefreshCirclesList();

                        // dismiss dialog
                        getBaseView().dismiss();
                    }
                });
    }
}
