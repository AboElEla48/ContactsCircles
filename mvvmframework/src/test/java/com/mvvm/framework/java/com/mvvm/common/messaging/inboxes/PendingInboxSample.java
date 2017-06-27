package com.mvvm.framework.java.com.mvvm.common.messaging.inboxes;

import com.mvvm.framework.messaging.CustomMessage;
import com.mvvm.framework.messaging.InboxHolder;

/**
 * Created by aboelela on 26/06/17.
 * Sample for inbox that is created lately after message sent
 */

public class PendingInboxSample implements InboxHolder
{
    CustomMessage customMessage;

    @Override
    public void onMessageReceived(CustomMessage msg) {
        customMessage = msg;
    }

    public CustomMessage getPendingMessage() {
        return customMessage;
    }
}
