package com.aboelela.circles.ui.home.fragments.viewMessage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.aboelela.circles.constants.Constants;
import com.aboelela.circles.data.HomeMessageModel;
import com.aboelela.circles.data.runTimeErrors.CircleMessageUnknownException;
import com.aboelela.circles.ui.home.HomeActivityMessagesHelper;
import com.aboelela.circles.ui.home.fragments.viewMessage.data.MessageFragmentViewModel;
import com.jakewharton.rxbinding2.view.RxView;
import com.mvvm.framework.annotation.ViewModel;
import com.mvvm.framework.base.presenters.BasePresenter;
import com.mvvm.framework.messaging.CustomMessage;
import com.mvvm.framework.messaging.MessagesServer;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by aboelela on 12/08/17.
 * Presenter for message fragment
 */

class MessageFragmentPresenter extends BasePresenter<MessageFragment, MessageFragmentPresenter>
{
    @ViewModel
    MessageFragmentViewModel messageFragmentViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getBaseView().getArguments() != null) {
            HomeMessageModel homeMessageModel = getBaseView().getArguments().getParcelable(MessageFragment.Bundle_Home_Message);

            if (homeMessageModel != null) {
                // init message text
                messageFragmentViewModel.setMessageVal(homeMessageModel.getMessageStr());

                // init visibility of buttons
                switch (homeMessageModel.getMessageType()) {
                    case Constants.MessageFragmentType.Message_Type_Info: {
                        messageFragmentViewModel.setMessageViewLayoutVisibility(View.GONE);
                        break;
                    }

                    case Constants.MessageFragmentType.Message_Type_Confirmation: {
                        messageFragmentViewModel.setMessageViewLayoutVisibility(View.VISIBLE);
                        handleMessageButtons();
                        break;
                    }

                    default: {
                        // Unknown type
                        throw new CircleMessageUnknownException();
                    }
                }
            } else {
                throw new CircleMessageUnknownException();
            }


        } else {
            throw new CircleMessageUnknownException();
        }

    }

    private void handleMessageButtons() {
        RxView.clicks(getBaseView().okBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {

                        // send broadcast message to all listener
                        MessagesServer.getInstance().broadcastMessage(
                                new CustomMessage(Constants.CirclesMessages.MSGID_Message_Fragment_OK_Button_Pressed,
                                        0, null));

                        HomeActivityMessagesHelper.sendMessageHideHomeMessagesFragment();
                    }
                });

        RxView.clicks(getBaseView().cancelBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {

                        // send broadcast message to all listener
                        MessagesServer.getInstance().broadcastMessage(
                                new CustomMessage(Constants.CirclesMessages.MSGID_Message_Fragment_Cancel_Button_Pressed,
                                        0, null));

                        HomeActivityMessagesHelper.sendMessageHideHomeMessagesFragment();
                    }
                });

    }


}
