package com.mvvm.framework.messaging;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by aboelela on 24/06/17.
 *
 * Define server for routing all messages among different inboxes
 */

public final class MessagesServer
{
    private MessagesServer() {}

    /**
     * get singleton object to server
     * @return
     */
    public static MessagesServer getInstance() {
        if(sInstance == null) {
            sInstance = new MessagesServer();
        }
        return sInstance;
    }

    /**
     * Register as receiver for messages
     * @param inboxHolder
     */
    public void registerInboxHolder(InboxHolder inboxHolder) {
        inboxHolders.add(inboxHolder);
        Observable.just(inboxHolder)
                .filter(new Predicate<InboxHolder>()
                {
                    @Override
                    public boolean test(@NonNull InboxHolder inboxHolder) throws Exception {
                        return pendingMessages.get(inboxHolder.getClass()) != null;
                    }
                })
                .subscribe(new Consumer<InboxHolder>()
                {
                    @Override
                    public void accept(@NonNull InboxHolder inboxHolder) throws Exception {
                        // send pending message
                        inboxHolder.onMessageReceived(pendingMessages.get(inboxHolder.getClass()));

                        // pending message consumed. It is no more pending
                        pendingMessages.remove(inboxHolder.getClass());
                    }
                });
    }

    /**
     * Unregister from listening to messages
     * @param inboxHolder
     */
    public void unRegisterInboxHolder(InboxHolder inboxHolder) {
        inboxHolders.remove(inboxHolder);
    }

    /**
     * Send message to inbox
     * @param inboxHolderClass
     * @param msg
     */
    public void sendMessage(final Class<?> inboxHolderClass, final CustomMessage msg) {
        // temporary add message to pending messages. It will remain there if no one consumed this message
        pendingMessages.put(inboxHolderClass, msg);

        Observable.fromIterable(inboxHolders)
                .filter(new Predicate<InboxHolder>()
                {
                    @Override
                    public boolean test(@NonNull InboxHolder inboxHolder) throws Exception {
                        return inboxHolder.getClass().equals(inboxHolderClass);
                    }
                })
                .subscribe(new Consumer<InboxHolder>()
                {
                    @Override
                    public void accept(@NonNull InboxHolder inboxHolder) throws Exception {
                        inboxHolder.onMessageReceived(msg);

                        // message consumed, remove it from pending messages array
                        pendingMessages.remove(inboxHolderClass);
                    }
                });
    }


    private static MessagesServer sInstance;
    private ArrayList<InboxHolder> inboxHolders = new ArrayList<>();
    private HashMap<Class, CustomMessage> pendingMessages = new HashMap<>();
}

