/*
 * Copyright (c) This code is written by Ahmed AboElEla (eng.a.aboelela@gmail.com). You can use it but please refer to Owner
 */

package com.mvvm.framework.base.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.framework.annotation.Presenter;
import com.mvvm.framework.base.creators.FieldTypeCreator;
import com.mvvm.framework.base.presenters.BasePresenter;
import com.mvvm.framework.base.scanners.FieldTypeScanner;
import com.mvvm.framework.interfaces.ActivityLifeCycle;
import com.mvvm.framework.interfaces.BaseView;
import com.mvvm.framework.interfaces.FragmentLifeCycle;
import com.mvvm.framework.interfaces.ViewLifeCycle;
import com.mvvm.framework.messaging.CustomMessage;
import com.mvvm.framework.messaging.InboxHolder;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by AboelelaA on 6/7/2017.
 * This is the object that will held in activities and fragments to pass baseView life cycle events to it
 */

class LifeCycleDelegate implements ActivityLifeCycle, FragmentLifeCycle, InboxHolder
{
    private WeakReference<ViewLifeCycle> hostObjectReference;
    protected BasePresenter presenter;

    LifeCycleDelegate(Object hostViews) {
        hostObjectReference = new WeakReference<>((ViewLifeCycle) hostViews);
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        final BaseView hostView = (BaseView) hostObjectReference.get();
        if (hostView == null) {
            return;
        }

        // Get presenter object by annotation
        createFieldsAnnotatedAsPresenter(hostView, savedInstanceState);
    }

    @Override
    public void onMessageReceived(CustomMessage msg) {
        if(presenter != null) {
            presenter.onMessageReceived(msg);
        }
    }

    /**
     * Create presenter object
     * @param hostView : the baseView object to get fields from
     */
    private void createFieldsAnnotatedAsPresenter(final BaseView hostView, @Nullable final Bundle savedInstanceState) {
        Observable.fromIterable(new FieldTypeScanner().apply(hostView.getClass().getDeclaredFields(), Presenter.class))
                .map(toPresenter(hostView))
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        if(presenter != null) {
                            presenter.onCreate(savedInstanceState);
                        }
                    }
                });
    }

    /**
     * create Presenter
     * @param hostView: the baseView object to get fields from
     * @return : function to subscribe to it
     */
    Function<Field, BasePresenter> toPresenter(final BaseView hostView) {
        return new Function<Field, BasePresenter>()
        {
            @Override
            public BasePresenter apply(@NonNull Field presenterField) throws Exception {

                // Create object of presenter type class
                presenter = (BasePresenter) (new FieldTypeCreator().createFieldObject(presenterField));

                // pass base baseView to presenter
                presenter.initBaseView(hostView, presenter);

                // set presenter to baseView
                presenterField.setAccessible(true);
                presenterField.set(hostView, presenter);

                return presenter;

            }
        };
    }

    @Override
    public void onStart() {
        if(presenter != null) {
            presenter.onStart();
        }
    }

    @Override
    public void onRestart() {
        if(presenter != null) {
            presenter.onRestart();
        }
    }

    @Override
    public void onResume() {
        if(presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    public void onPause() {
        if(presenter != null) {
            presenter.onPause();
        }
    }

    @Override
    public void onStop() {
        if(presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if(presenter != null) {
            presenter.onDestroy();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(presenter != null) {
            presenter.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(presenter != null) {
            presenter.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if(presenter != null) {
            presenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(presenter != null) {
            return presenter.onCreateView(inflater, container, savedInstanceState);
        }
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if(presenter != null) {
            presenter.onActivityCreated(savedInstanceState);
        }
    }

    @Override
    public boolean onActivityBackPressed() {
        return(presenter != null && presenter.onActivityBackPressed());
    }
}
