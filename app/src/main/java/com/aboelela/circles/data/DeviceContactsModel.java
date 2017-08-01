package com.aboelela.circles.data;

import com.aboelela.circles.CirclesApplication;
import com.aboelela.circles.data.runTimeErrors.ContactsNotLoadedException;
import com.mvvm.framework.base.models.BaseModel;
import com.mvvm.framework.utils.ContactsUtil;
import com.mvvm.framework.utils.LogUtil;

import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aboelela on 14/07/17.
 * Model for device contacts
 */

public class DeviceContactsModel extends BaseModel
{
    private Map<String, ContactsUtil.ContactModel> deviceContacts = null;

    private final String LOG_TAG = "DeviceContactsModel";

    /**
     * Load device contacts
     */
    public void loadDeviceContacts(Consumer<Object> receiver) {
        if (deviceContacts == null) {
            Observable.
                    fromCallable(new Callable<Map<String, ContactsUtil.ContactModel>>()
                    {
                        @Override
                        public Map<String, ContactsUtil.ContactModel> call() throws Exception {
                            deviceContacts = ContactsUtil.loadDeviceContacts(CirclesApplication.getInstance());
                            return deviceContacts;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(receiver);
        }
        else {
            try {
                receiver.accept(deviceContacts);
            }
            catch (Exception ex) {
                LogUtil.writeErrorLog(LOG_TAG, "Error loading device contacts");
            }

        }
    }

    /**
     * get the loaded device contacts
     *
     * @return : list of device contacts
     * @throws ContactsNotLoadedException : exception in case you requests contacts without loading it
     */
    public Map<String, ContactsUtil.ContactModel> getDeviceContacts() throws ContactsNotLoadedException {
        if (deviceContacts != null) {
            return deviceContacts;
        }
        throw new ContactsNotLoadedException();
    }

}
