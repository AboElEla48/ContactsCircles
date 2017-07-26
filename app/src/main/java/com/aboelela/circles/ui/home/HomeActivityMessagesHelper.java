package com.aboelela.circles.ui.home;

import com.aboelela.circles.constants.CirclesMessages;
import com.aboelela.circles.data.entities.Circle;
import com.aboelela.circles.ui.home.fragments.viewCircles.CirclesListFragment;
import com.mvvm.framework.messaging.CustomMessage;
import com.mvvm.framework.messaging.MessagesServer;
import com.mvvm.framework.utils.ContactsUtil;

import io.reactivex.annotations.Nullable;

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
    public static void sendMessageAddCircle() {
        MessagesServer.getInstance().sendMessage(HomeActivity.class, new CustomMessage(CirclesMessages.MSGID_Add_Circle, 0, null));
    }

    /**
     * send message to refresh the circles list
     */
    public static void sendMessageRefreshCirclesList() {
        MessagesServer.getInstance().sendMessage(CirclesListFragment.class,
                CirclesMessages.MSG_Refresh_Circles_List);
    }

    /**
     * send message to show contacts list of given circle
     * @param circle : circle to show its contacts
     */
    public static void sendMessageOpenCircleContacts(Circle circle) {
        MessagesServer.getInstance().sendMessage(HomeActivity.class,
                new CustomMessage(CirclesMessages.MSGID_Open_Contacts_Of_Circle, 0, circle));
    }

    /**
     * send message to show device contacts fragment to assign contacts to given circle
     * @param circleToAssignContacts :  circle to assign contacts from device to it
     */
    public static void sendMessageShowDeviceContacts(@Nullable  Circle circleToAssignContacts) {
        MessagesServer.getInstance().sendMessage(HomeActivity.class,
                new CustomMessage(CirclesMessages.MSGID_Open_Device_Contacts, 0, circleToAssignContacts));
    }

    /**
     * send message to show contact details
     * @param contactModel : the contact to show its details
     */
    public static void sendMessageShowContactDetails(ContactsUtil.ContactModel contactModel) {
        MessagesServer.getInstance().sendMessage(HomeActivity.class,
                new CustomMessage(CirclesMessages.MSGID_Open_Contact_Details, 0, contactModel));
    }

    /**
     * send message to remove contacts fragment from activity buffer
     */
    public static void sendMessageFinishDeviceContactsFragment() {
        MessagesServer.getInstance().sendMessage(HomeActivity.class,
                new CustomMessage(CirclesMessages.MSGID_Finish_Device_Contacts_Fragment, 0, null));
    }


}
