package com.mvvm.framework.messaging;

/**
 * Created by aboelela on 24/06/17.
 * Define data message to send and receive
 */

public class CustomMessage
{
    private int messageId;
    private Object data;
    private int payLoad;

    public CustomMessage() {}

    public CustomMessage(int messageId, int payload, Object data) {
        setMessageId(messageId);
        setData(data);
        setPayLoad(payload);
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }

    public Object getData() {
        return data;
    }

    public void setPayLoad(int payLoad) {
        this.payLoad = payLoad;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getPayLoad() {
        return payLoad;
    }
}
