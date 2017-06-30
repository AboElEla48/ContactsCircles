package com.aboelela.circles.ui.home.fragments.addCircle;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jakewharton.rxbinding2.view.RxView;
import com.mvvm.framework.base.presenters.BasePresenter;
import com.mvvm.framework.utils.DialogMsgUtil;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by aboelela on 30/06/17.
 * Presenter for new circle dialog fragment
 */

public class NewCircleDialogPresenter extends BasePresenter<NewCircleDialogFragment, NewCircleDialogPresenter>
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RxView.clicks(getBaseView().saveBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        // TODO: save circle dismiss dialog
                        DialogMsgUtil.showInfoMessage(getBaseView().getContext(), "Handle save button");
                    }
                });
    }
}
