package com.mvvm.framework.messaging;

/**
 * Created by aboelela on 24/06/17.
 *
 * Define interface for all classes that would like to keep inbox for it
 */

public interface InboxHolder
{
    void onMessageReceived(CustomMessage msg);
}
