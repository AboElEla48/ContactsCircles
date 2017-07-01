package com.mvvm.framework.base.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.framework.base.scanners.LayoutIdScanner;
import com.mvvm.framework.interfaces.BaseView;
import com.mvvm.framework.interfaces.FragmentLifeCycle;
import com.mvvm.framework.messaging.CustomMessage;
import com.mvvm.framework.messaging.InboxHolder;
import com.mvvm.framework.messaging.MessagesServer;

import butterknife.ButterKnife;

/**
 * Created by AboelelaA on 6/6/2017.
 * This is the parent fragment
 */

public class BaseFragment extends Fragment implements BaseView, FragmentLifeCycle, InboxHolder
{
    private LifeCycleDelegate lifeCycleDelegate;

    @Override
    public void onMessageReceived(CustomMessage msg) {
        lifeCycleDelegate.onMessageReceived(msg);
    }

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public final void onRestart() {
        lifeCycleDelegate.onRestart();
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Get declared resource Id of this activity
        int resourceId = new LayoutIdScanner().apply(this);
        return inflater.inflate(resourceId, container, false);
    }

    @Override
    public final void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getActivity());

        // pass lifecycle to baseView life cycle delegate
        lifeCycleDelegate = new LifeCycleDelegate(this);
        lifeCycleDelegate.onCreate(savedInstanceState);

        lifeCycleDelegate.onActivityCreated(savedInstanceState);

        MessagesServer.getInstance().registerInboxHolder(this);
    }

    @Override
    public final void onPause() {
        super.onPause();
        lifeCycleDelegate.onPause();
    }

    @Override
    public final void onResume() {
        super.onResume();
        lifeCycleDelegate.onResume();
    }

    @Override
    public final void onStop() {
        super.onStop();
        lifeCycleDelegate.onStop();
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
        lifeCycleDelegate.onDestroy();
    }

    @Override
    public final void onRestoreInstanceState(Bundle savedInstanceState) {
        lifeCycleDelegate.onRestoreInstanceState(savedInstanceState);
    }
}
