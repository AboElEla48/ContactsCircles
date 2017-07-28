package com.aboelela.circles.data;

import com.aboelela.circles.CirclesApplication;
import com.aboelela.circles.data.runTimeErrors.ContactsNotLoadedException;
import com.mvvm.framework.base.models.BaseModel;
import com.mvvm.framework.utils.ContactsUtil;
import com.mvvm.framework.utils.LogUtil;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by aboelela on 14/07/17.
 * Model for device contacts
 */

public class DeviceContactsModel extends BaseModel
{
    private Map<String, ContactsUtil.ContactModel> deviceContacts = null;

    private final String TAG = "DeviceContactsModel";

    /**
     * Load device contacts
     *
     */
    public void loadDeviceContacts(final Consumer<Map<String, ContactsUtil.ContactModel>> receiver) {
        if (deviceContacts == null) {
            Observable.just(ContactsUtil.loadDeviceContacts(CirclesApplication.getInstance(), new Consumer<Map<String, ContactsUtil.ContactModel>>()
            {
                @Override
                public void accept(@NonNull Map<String, ContactsUtil.ContactModel> stringContactModelMap) throws Exception {
                    deviceContacts = stringContactModelMap;
                    receiver.accept(deviceContacts);
                }
            })).subscribe();

        }
        else {
            try {
                receiver.accept(deviceContacts);
            }
            catch (Exception ex) {
                LogUtil.writeErrorLog(TAG, ex);
            }

        }
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
