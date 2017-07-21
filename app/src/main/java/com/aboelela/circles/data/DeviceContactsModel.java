package com.aboelela.circles.data;

import com.aboelela.circles.CirclesApplication;
import com.aboelela.circles.data.runTimeErrors.ContactsNotLoadedException;
import com.mvvm.framework.base.models.BaseModel;
import com.mvvm.framework.utils.ContactsUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

/**
 * Created by aboelela on 14/07/17.
 * Model for device contacts
 */

public class DeviceContactsModel extends BaseModel
{
    private Map<String, ContactsUtil.ContactModel> deviceContacts = null;

    /**
     * Load device contacts
     *
     * @return :the list of device contacts
     */
    public Map<String, ContactsUtil.ContactModel> loadDeviceContacts() {
        Observable.fromCallable(new Callable<Map<String, ContactsUtil.ContactModel>>()
        {
            @Override
            public Map<String, ContactsUtil.ContactModel> call() throws Exception {
                deviceContacts = ContactsUtil.loadDeviceContacts(CirclesApplication.getInstance());
                if(deviceContacts == null) {
                    deviceContacts = new HashMap<>();
                }
                return deviceContacts;
            }
        }).blockingSubscribe();

        return deviceContacts;
    }

    /**
     * get the loaded device contacts
     * @return : list of device contacts
     *
     * @throws ContactsNotLoadedException : exception in case you requests contacts without loading it
     */
    public Map<String, ContactsUtil.ContactModel> getDeviceContacts()throws ContactsNotLoadedException {
        if(deviceContacts != null) {
            return deviceContacts;
        }
        throw new ContactsNotLoadedException();
    }

}
