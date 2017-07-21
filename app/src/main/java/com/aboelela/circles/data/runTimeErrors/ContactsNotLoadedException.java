package com.aboelela.circles.data.runTimeErrors;

/**
 * Created by aboelela on 21/07/17.
 * Throws this exception when someone tries to get the contacts from model without loading it from device
 */

public class ContactsNotLoadedException extends RuntimeException
{
    @Override
    public String getMessage() {
        return "You didn't call loadDeviceContacts() before getting contacts";
    }
}
