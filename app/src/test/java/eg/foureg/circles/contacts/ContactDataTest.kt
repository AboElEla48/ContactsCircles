package eg.foureg.circles.contacts

import io.reactivex.Observable
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import java.lang.reflect.Array

class ContactDataTest {

    @Test
    fun mergeContact() {
        val oldContact = ContactData()
        val newContact = ContactData()

        oldContact.name = "name"
        oldContact.phones?.add(ContactPhoneNumber("010", ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_HOME))
        oldContact.phones?.add(ContactPhoneNumber("011", ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_HOME))
        oldContact.emails?.add("1@gmail.com")
        oldContact.emails?.add("2@gmail.com")

        newContact.name = "name"
        newContact.phones?.add(ContactPhoneNumber("010", ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_HOME))
        newContact.phones?.add(ContactPhoneNumber("012", ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_HOME))
        newContact.emails?.add("1@gmail.com")
        newContact.emails?.add("3@gmail.com")

        oldContact.mergeContact(newContact)

        Assert.assertTrue(oldContact.phones?.size == 3)
        Assert.assertTrue(oldContact.emails?.size == 3)

        Assert.assertTrue(newContact.phones?.size == 2)
        Assert.assertTrue(newContact.emails?.size == 2)

        Assert.assertTrue(oldContact.phones?.get(0)?.phoneNumber.equals("010"))
        Assert.assertTrue(oldContact.phones?.get(1)?.phoneNumber.equals("011"))
        Assert.assertTrue(oldContact.phones?.get(2)?.phoneNumber.equals("012"))

        Assert.assertTrue(oldContact.emails?.get(0).equals("1@gmail.com"))
        Assert.assertTrue(oldContact.emails?.get(1).equals("2@gmail.com"))
        Assert.assertTrue(oldContact.emails?.get(2).equals("3@gmail.com"))
    }
}