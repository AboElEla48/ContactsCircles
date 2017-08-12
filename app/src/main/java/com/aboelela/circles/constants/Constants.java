package com.aboelela.circles.constants;

import com.mvvm.framework.messaging.CustomMessage;

/**
 * Created by aboelela on 29/06/17.
 * <p>
 * Hold all application constants
 */

public final class Constants
{
    private Constants() {
    }

    public static class App
    {
        static int Result_SUCCESS = 0;
        static int Result_Error = -1;

        public static int ViewMode_Grid = 1;
        public static int ViewMode_List = 2;
    }

    public static class Splash
    {
        public static int SPLASH_DURATION = 1 * 1000;
    }

    public static class MessageFragmentType
    {
        public final static int Message_Type_Confirmation = 1;
        public final static int Message_Type_Info = 2;
    }

    /**
     * Hold all messages
     */

    public static final class CirclesMessages
    {
        public static final int MSGID_Add_Circle = 1;

        public static final int MSGID_Edit_Circle_Name = 2;

        public static final int MSGID_Refresh_Circles_List = 3;
        public static CustomMessage MSG_Refresh_Circles_List = new CustomMessage(MSGID_Refresh_Circles_List,
                App.Result_SUCCESS, null);

        public static final int MSGID_Open_Contacts_Of_Circle = 4;

        public static final int MSGID_Open_Device_Contacts = 5;

        public static final int MSGID_Open_Contact_Details = 6;

        public static final int MSGID_Finish_Device_Contacts_Fragment = 7;

        public static final int MSGID_Show_Message_Fragment = 8;
        public static final int MSGID_Hide_Message_Fragment = 9;

        public static final int MSGID_Message_Fragment_OK_Button_Pressed = 10;
        public static final int MSGID_Message_Fragment_Cancel_Button_Pressed = 11;
    }
}
