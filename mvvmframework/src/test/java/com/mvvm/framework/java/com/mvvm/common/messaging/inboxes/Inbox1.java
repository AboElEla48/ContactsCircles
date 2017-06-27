package com.mvvm.framework.java.com.mvvm.common.messaging.inboxes;

import com.mvvm.framework.messaging.CustomMessage;
import com.mvvm.framework.messaging.InboxHolder;

/**
 * Created by aboelela on 24/06/17.
 *
 * Sample for inbox class
 */

public class Inbox1 implements InboxHolder
{
    @Override
    public void onMessageReceived(CustomMessage msg) {
        x += (int)msg.getData();

    }

    public int getVal() {
        return x;
    }

    private int x = 0;
}
