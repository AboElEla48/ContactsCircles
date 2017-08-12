package com.aboelela.circles.constants;

import com.mvvm.framework.messaging.CustomMessage;

/**
 * Created by aboelela on 01/07/17.
 *
 * Hold all messages
 */

public final class CirclesMessages
{
    public static final int MSGID_Add_Circle = 1;

    public static final int MSGID_Edit_Circle_Name = 2;

    public static final int MSGID_Refresh_Circles_List = 3;
    public static CustomMessage MSG_Refresh_Circles_List = new CustomMessage(MSGID_Refresh_Circles_List,
            Constants.App.Result_SUCCESS, null);

    public static final int MSGID_Open_Contacts_Of_Circle = 4;

    public static final int MSGID_Open_Device_Contacts = 5;

    public static final int MSGID_Open_Contact_Details = 6;

    public static final int MSGID_Finish_Device_Contacts_Fragment = 7;
}
