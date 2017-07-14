package com.aboelela.circles.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aboelela.circles.R;
import com.aboelela.circles.constants.CirclesMessages;
import com.aboelela.circles.data.entities.Circle;
import com.aboelela.circles.ui.home.fragments.addCircle.NewCircleDialogFragment;
import com.aboelela.circles.ui.home.fragments.viewCircles.CirclesListFragment;
import com.aboelela.circles.ui.home.fragments.viewContacts.CircleContactsListFragment;
import com.mvvm.framework.base.presenters.BasePresenter;
import com.mvvm.framework.messaging.CustomMessage;

/**
 * Created by aboelela on 29/06/17.
 * Presenter for home screen
 */

class HomePresenter extends BasePresenter<HomeActivity, HomePresenter>
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the view list of circles fragment
        getBaseView().getSupportFragmentManager().beginTransaction().replace(R.id.activity_home_frameLayout,
                CirclesListFragment.newInstance()).commit();

    }

    @Override
    public void onMessageReceived(CustomMessage msg) {
        super.onMessageReceived(msg);
        switch (msg.getMessageId()) {
            case CirclesMessages.MSGID_Open_Contacts_Of_Circle: {
                showCircleContacts((Circle) msg.getData());
                break;
            }

            case CirclesMessages.MSGID_Add_Circle: {
                // Show dialog for creating new circle
                NewCircleDialogFragment circleDialogFragment = NewCircleDialogFragment.newInstance();
                circleDialogFragment.show(getBaseView().getSupportFragmentManager(), "");

                break;
            }
        }
    }

    /**
     * Display the contacts of circle
     *
     */
    private void showCircleContacts(Circle circle) {
        getBaseView().getSupportFragmentManager().beginTransaction().replace(R.id.activity_home_frameLayout,
                CircleContactsListFragment.newInstance(circle)).commit();
    }
}
