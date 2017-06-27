package com.mvvm.framework.base.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mvvm.framework.base.scanners.LayoutIdScanner;
import com.mvvm.framework.interfaces.ActivityLifeCycle;
import com.mvvm.framework.interfaces.BaseView;
import com.mvvm.framework.messaging.CustomMessage;
import com.mvvm.framework.messaging.InboxHolder;
import com.mvvm.framework.messaging.MessagesServer;

import butterknife.ButterKnife;

/**
 * Created by AboelelaA on 6/6/2017.
 * This is the parent activity
 */

public class BaseActivity extends AppCompatActivity implements BaseView, ActivityLifeCycle, InboxHolder
{
    private LifeCycleDelegate lifeCycleDelegate;

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get declared resource Id of this activity
        int resourceId = new LayoutIdScanner().apply(this);
        setContentView(resourceId);

        // Bind views
        ButterKnife.bind(this);

        MessagesServer.getInstance().registerInboxHolder(this);

        // pass lifecycle to baseView life cycle delegate
        lifeCycleDelegate = new LifeCycleDelegate(this);
        lifeCycleDelegate.onCreate(savedInstanceState);
    }

    @Override
    public void onMessageReceived(CustomMessage msg) {
        lifeCycleDelegate.onMessageReceived(msg);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifeCycleDelegate.onStart();
    }

    @Override
    public final void onRestart() {
        super.onRestart();
        lifeCycleDelegate.onRestart();
    }

    @Override
    public final void onResume() {
        super.onResume();
        lifeCycleDelegate.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        lifeCycleDelegate.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        lifeCycleDelegate.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public final void onPause() {
        lifeCycleDelegate.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        lifeCycleDelegate.onStop();
        super.onStop();
    }

    @Override
    public final void onDestroy() {
        MessagesServer.getInstance().unRegisterInboxHolder(this);
        lifeCycleDelegate.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        lifeCycleDelegate.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        lifeCycleDelegate.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        lifeCycleDelegate.onRestoreInstanceState(savedInstanceState);
    }
}
