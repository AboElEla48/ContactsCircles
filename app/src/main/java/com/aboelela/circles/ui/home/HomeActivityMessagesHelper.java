package com.aboelela.circles.ui.home;

import com.aboelela.circles.constants.CirclesMessages;
import com.aboelela.circles.data.entities.Circle;
import com.aboelela.circles.ui.home.fragments.viewCircles.CirclesListFragment;
import com.mvvm.framework.messaging.CustomMessage;
import com.mvvm.framework.messaging.MessagesServer;

/**
 * Created by aboelela on 21/07/17.
 * Unified place to send messages to home activity
 */

public final class HomeActivityMessagesHelper
{
    private HomeActivityMessagesHelper() {}

    /**
     * send message to home activity to trigger add circle dialog
     */
    public static void addCircle() {
        MessagesServer.getInstance().sendMessage(HomeActivity.class, new CustomMessage(CirclesMessages.MSGID_Add_Circle, 0, null));
    }

    /**
     * send message to refresh the circles list
     */
    public static void refreshCirclesList() {
        MessagesServer.getInstance().sendMessage(CirclesListFragment.class,
                CirclesMessages.MSG_Refresh_Circles_List);
    }

    /**
     * send message to show contacts list of given circle
     * @param circle : circle to show its contacts
     */
    public static void openCircleContacts(Circle circle) {
        MessagesServer.getInstance().sendMessage(HomeActivity.class,
                new CustomMessage(CirclesMessages.MSGID_Open_Contacts_Of_Circle, 0, circle));
    }

    /**
     * send message to show device contacts fragment to assign contacts to given circle
     * @param circleToAssignContacts :  circle to assign contacts from device to it
     */
    public static void showDeviceContacts(Circle circleToAssignContacts) {
        MessagesServer.getInstance().sendMessage(HomeActivity.class,
                new CustomMessage(CirclesMessages.MSGID_Open_Device_Contacts, 0, circleToAssignContacts));
    }


}
