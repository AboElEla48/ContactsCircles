package com.aboelela.circles.data;

import com.aboelela.circles.CirclesApplication;
import com.mvvm.framework.base.models.BaseModel;
import com.mvvm.framework.utils.ContactsUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aboelela on 14/07/17.
 * Model for device contacts
 */

public class DeviceContactsModel extends BaseModel
{
    private ArrayList<ContactsUtil.ContactModel> deviceContacts = new ArrayList<>();

    /**
     * Load device contacts
     * @return :the list of device contacts
     */
    public List<ContactsUtil.ContactModel> loadDeviceContacts() {
        Observable.fromIterable(ContactsUtil.loadDeviceContacts(CirclesApplication.getInstance()))
                .subscribeOn(Schedulers.newThread())
                .blockingSubscribe(new io.reactivex.functions.Consumer<ContactsUtil.ContactModel>()
                {
                    @Override
                    public void accept(@NonNull ContactsUtil.ContactModel contactModel) throws Exception {
                        deviceContacts.add(contactModel);
                    }
                });

        return deviceContacts;
    }

    public List<ContactsUtil.ContactModel> getDeviceContacts() {
        return deviceContacts;
    }

}
