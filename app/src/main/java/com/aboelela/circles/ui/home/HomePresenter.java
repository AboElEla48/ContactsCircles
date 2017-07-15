package com.aboelela.circles.ui.home;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aboelela.circles.R;
import com.aboelela.circles.constants.CirclesMessages;
import com.aboelela.circles.data.entities.Circle;
import com.aboelela.circles.ui.ActivityNavigationManager;
import com.aboelela.circles.ui.home.fragments.addCircle.NewCircleDialogFragment;
import com.aboelela.circles.ui.home.fragments.viewCircleContacts.CircleContactsListFragment;
import com.aboelela.circles.ui.home.fragments.viewCircles.CirclesListFragment;
import com.aboelela.circles.ui.home.fragments.viewDeviceContacts.DeviceContactsListFragment;
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

        ActivityNavigationManager.showPermissionsActivity(getBaseView().getBaseContext(),
                new String[]{Manifest.permission.READ_CONTACTS},
                getBaseView().getResources().getStringArray(R.array.txt_arr_permissions_arr));

    }

    @Override
    public void onMessageReceived(CustomMessage msg) {
        super.onMessageReceived(msg);
        switch (msg.getMessageId()) {
            case CirclesMessages.MSGID_Add_Circle: {
                // Show dialog for creating new circle
                showNewCircleDialog();
                break;
            }

            case CirclesMessages.MSGID_Open_Contacts_Of_Circle: {
                // Show contacts of given circle
                showCircleContacts((Circle) msg.getData());
                break;
            }

            case CirclesMessages.MSGID_Open_Device_Contacts: {
                // Show device contacts
                showDeviceContactsToAssignCircleContacts((Circle) msg.getData());
                break;
            }

            case CirclesMessages.MSGID_Set_Home_Title: {
                // Change the screen title
                setTitleText(msg.getData().toString());
                break;
            }
        }
    }

    /**
     * Show new circle dialog to add new circle
     */
    private void showNewCircleDialog() {
        NewCircleDialogFragment circleDialogFragment = NewCircleDialogFragment.newInstance();
        circleDialogFragment.show(getBaseView().getSupportFragmentManager(), "");
    }

    /**
     * Display the contacts of circle
     *
     * @param circle : the circle to view its contacts
     */
    private void showCircleContacts(Circle circle) {
        getBaseView().getSupportFragmentManager().beginTransaction().replace(R.id.activity_home_frameLayout,
                CircleContactsListFragment.newInstance(circle)).commit();

        // set title of screen
        setTitleText(circle.getName());
    }

    /**
     * Let user select contact from device to assign to specific circle
     *
     * @param circle : the circle that device contact will be assigned to it
     */
    private void showDeviceContactsToAssignCircleContacts(Circle circle) {
        getBaseView().getSupportFragmentManager().beginTransaction().replace(R.id.activity_home_frameLayout,
                DeviceContactsListFragment.newInstance(circle)).commit();
    }

    /**
     * Change screen title
     *
     * @param text : the string to set to screen title
     */
    private void setTitleText(String text) {
        getBaseView().setTitle(text);
    }
}
