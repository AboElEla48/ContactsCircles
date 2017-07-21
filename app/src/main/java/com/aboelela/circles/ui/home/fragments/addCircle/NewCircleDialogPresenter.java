package com.aboelela.circles.ui.home.fragments.addCircle;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aboelela.circles.data.CirclesModel;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RxView.clicks(getBaseView().saveBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        // save to data store
                        circlesModel.addCircle(getBaseView().circleNameEditText.getText().toString());

                        // Notify list fragment to refresh
                        HomeActivityMessagesHelper.refreshCirclesList();

                        // dismiss dialog
                        getBaseView().dismiss();
                    }
                });
    }
}
