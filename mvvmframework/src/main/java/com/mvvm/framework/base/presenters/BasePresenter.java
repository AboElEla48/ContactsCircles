package com.mvvm.framework.base.presenters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.framework.annotation.DataModel;
import com.mvvm.framework.annotation.ViewModel;
import com.mvvm.framework.annotation.singleton.Singleton;
import com.mvvm.framework.annotation.singleton.SingletonPerSession;
import com.mvvm.framework.base.creators.FieldTypeCreator;
import com.mvvm.framework.base.creators.SingletonCreator;
import com.mvvm.framework.base.models.BaseModel;
import com.mvvm.framework.base.scanners.FieldTypeScanner;
import com.mvvm.framework.base.viewmodels.BaseViewModel;
import com.mvvm.framework.interfaces.ActivityLifeCycle;
import com.mvvm.framework.interfaces.BaseView;
import com.mvvm.framework.interfaces.FragmentLifeCycle;
import com.mvvm.framework.messaging.CustomMessage;
import com.mvvm.framework.messaging.InboxHolder;
import com.mvvm.framework.messaging.MessagesServer;
import com.mvvm.framework.utils.LogUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by AboelelaA on 6/6/2017.
 * <p>
 * This is the parent class for all presenters
 */

public class BasePresenter<V extends BaseView, P extends BasePresenter> implements ActivityLifeCycle, FragmentLifeCycle, InboxHolder
{
    private String TAG = BasePresenter.class.getName();

    private V baseView;
    private P childPresenter;

    private ArrayList<BaseViewModel> allViewModels;
    private ArrayList<Object> allSingletonPerSessionObjects;

    /**
     * init base baseView object
     *
     * @param baseView: this is te base baseView that will be accessed from presenter
     */
    public void initBaseView(@NonNull V baseView, @NonNull P basePresenter) {
        this.baseView = baseView;
        this.childPresenter = basePresenter;

        allViewModels = new ArrayList<>();
        allSingletonPerSessionObjects = new ArrayList<>();
    }

    /**
     * Create Models declared in presenter
     */
    private void createFieldsAnnotatedAsModels() {
        Observable.fromIterable(new FieldTypeScanner().apply(childPresenter.getClass().getDeclaredFields(), DataModel.class))
                .map(toDataModel(childPresenter))
                .subscribe();
    }


    public Function<Field, BaseModel> toDataModel(final BasePresenter modelPresenter) {
        return new Function<Field, BaseModel>()
        {
            @Override
            public BaseModel apply(@io.reactivex.annotations.NonNull Field modelField) throws Exception {
                BaseModel dataModel = (BaseModel) new FieldTypeCreator().createFieldObject(modelField);

                modelField.setAccessible(true);
                modelField.set(modelPresenter, dataModel);

                return dataModel;
            }
        };
    }

    /**
     * Create all fields annotated as singleton in presenter
     */
    private void createFieldsAnnotatedAsSingleton() {
        try {
            Observable.fromIterable(new FieldTypeScanner().apply(childPresenter.getClass().getDeclaredFields(), Singleton.class))
                    .map(toSingleton(childPresenter, false))
                    .subscribe();
        } catch (UnsupportedOperationException ex) {
            LogUtil.writeDebugLog(TAG, "No Singleton objects declared");
        }


        try {
            Observable.fromIterable(new FieldTypeScanner().apply(childPresenter.getClass().getDeclaredFields(), SingletonPerSession.class))
                    .map(toSingleton(childPresenter, true))
                    .subscribe();
        } catch (UnsupportedOperationException ex) {
            LogUtil.writeDebugLog(TAG, "No Singleton Per Session objects declared");
        }

    }

    private Function<Field, Object> toSingleton(final BasePresenter singletonPresenter, final boolean isPerSession) {
        return new Function<Field, Object>()
        {
            @Override
            public Object apply(@io.reactivex.annotations.NonNull Field singletonField) throws Exception {
                Object singletonObject = new FieldTypeCreator().createFieldObject(singletonField);

                singletonField.setAccessible(true);
                singletonField.set(singletonPresenter, singletonObject);

                if (isPerSession) {
                    allSingletonPerSessionObjects.add(singletonObject);
                }

                return singletonObject;
            }
        };
    }

    /**
     * Create View Models
     */
    private void createFieldsAnnotatedAsViewModels() {
        Observable.fromIterable(new FieldTypeScanner().apply(childPresenter.getClass().getDeclaredFields(), ViewModel.class))
                .map(toViewModel(childPresenter))
                .subscribe();

    }

    public Function<Field, BaseViewModel> toViewModel(final BasePresenter viewModelPresenter) {
        return new Function<Field, BaseViewModel>()
        {
            @Override
            public BaseViewModel apply(@io.reactivex.annotations.NonNull Field viewModelField) throws Exception {
                BaseViewModel viewModel = (BaseViewModel) new FieldTypeCreator().createFieldObject(viewModelField);

                viewModel.initView(viewModelPresenter.getBaseView(), viewModel);

                // add view model to presenter list
                allViewModels.add(viewModel);

                viewModelField.setAccessible(true);
                viewModelField.set(viewModelPresenter, viewModel);

                // Associate all views in baseView model
                viewModel.associateViewModelWithViews();


                return viewModel;
            }
        };
    }


    @Override
    public void onMessageReceived(CustomMessage msg) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        MessagesServer.getInstance().registerInboxHolder(this);

        // Create models from presenter by annotation
        createFieldsAnnotatedAsModels();

        // Create Singleton fields in presenter
        createFieldsAnnotatedAsSingleton();

        // Get View models by annotation
        createFieldsAnnotatedAsViewModels();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

        // Destroy all publish subjects of view model
        Observable.fromIterable(allViewModels)
                .subscribe(new Consumer<BaseViewModel>()
                {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull BaseViewModel baseViewModel) throws Exception {
                        baseViewModel.releaseViewModelValues();
                    }
                });

        Observable.fromIterable(allSingletonPerSessionObjects)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
                        SingletonCreator.getCreator().removeInstance(o.getClass());
                    }
                });

        MessagesServer.getInstance().unRegisterInboxHolder(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }


    public V getBaseView() {
        return baseView;
    }


}
