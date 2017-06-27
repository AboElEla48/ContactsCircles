package com.mvvm.framework.java.com.mvvm.common.messaging;

import com.mvvm.framework.java.com.mvvm.common.messaging.inboxes.Inbox1;
import com.mvvm.framework.java.com.mvvm.common.messaging.inboxes.PendingInboxSample;
import com.mvvm.framework.messaging.CustomMessage;
import com.mvvm.framework.messaging.MessagesServer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by aboelela on 24/06/17.
 * Test class for messages server
 */
public class MessagesServerTest
{
    private Inbox1 inbox1;
    @Before
    public void init() {
        inbox1 = new Inbox1();

        MessagesServer.getInstance().registerInboxHolder(inbox1);
    }

    @Test
    public void sendMessage_AssureMessageSent() throws Exception {
        CustomMessage message = new CustomMessage();
        message.setData(10);

        MessagesServer.getInstance().sendMessage(Inbox1.class, message);

        Assert.assertTrue(inbox1.getVal() == 10);
    }

    @Test
    public void sendMessageDelayed() throws Exception {
        CustomMessage message = new CustomMessage(1, 0, "This is pending message");

        MessagesServer.getInstance().sendMessage(PendingInboxSample.class, message);

        PendingInboxSample pendingInboxSample = new PendingInboxSample();
        MessagesServer.getInstance().registerInboxHolder(pendingInboxSample);

        Assert.assertTrue(pendingInboxSample.getPendingMessage().getMessageId() == message.getMessageId());
        Assert.assertTrue(pendingInboxSample.getPendingMessage().getData().equals(message.getData()));

        MessagesServer.getInstance().unRegisterInboxHolder(pendingInboxSample);

    }

    @After
    public void release() {
        MessagesServer.getInstance().unRegisterInboxHolder(inbox1);
    }

}