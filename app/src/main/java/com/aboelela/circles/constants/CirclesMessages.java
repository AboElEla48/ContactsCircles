package com.aboelela.circles.constants;

import com.mvvm.framework.messaging.CustomMessage;

/**
 * Created by aboelela on 01/07/17.
 *
 * Hold all messages
 */

public final class CirclesMessages
{
    public static final int MSGID_Refresh_Circles_List = 1;
    public static CustomMessage MSG_Refresh_Circles_List = new CustomMessage(MSGID_Refresh_Circles_List,
            Constants.App.Result_SUCCESS, null);
}