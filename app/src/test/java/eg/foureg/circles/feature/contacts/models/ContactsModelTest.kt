package eg.foureg.circles.feature.contacts.models

import eg.foureg.circles.contacts.data.ContactData
import eg.foureg.circles.contacts.data.ContactPhoneNumber
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Test

class ContactsModelTest {

    class SampleContactModel : ContactsModel(ContactRetr(), ContactEdi()) {
        fun removeDuplicatesFromMode(rawContacts: ArrayList<ContactData>): Observable<ArrayList<ContactData>> {
            return removeDuplicate(rawContacts)
        }
    }

    @Test
    fun removeDuplicate() {
        val contactModel = SampleContactModel()

        val duplicateContactsList: ArrayList<ContactData> = ArrayList()
        duplicateContactsList.add(createDummyContact("c1", "0101", "0102", "1@g.com", "2@g.com", "notes1"))
        duplicateContactsList.add(createDummyContact("c2", "0111", "0112", "1@b.com", "2@b.com", "notes2"))
        duplicateContactsList.add(createDummyContact("c3", "0151", "0152", "1@c.com", "2@c.com", "notes3"))

        duplicateContactsList.add(createDummyContact("c1", "0101", "0177", "1@g.com", "88@g.com", "notes1"))

        contactModel.removeDuplicatesFromMode(duplicateContactsList)
                .subscribe{ contacts: List<ContactData> ->
                    Assert.assertTrue(contacts.size == 3)

                    Assert.assertTrue(contacts.get(0).name.equals("c1"))
                    Assert.assertTrue(contacts.get(1).name.equals("c2"))
                    Assert.assertTrue(contacts.get(2).name.equals("c3"))

                    Assert.assertTrue(contacts.get(0).phones?.size == 3)

                }
    }

    protected fun createDummyContact(name: String, p1: String, p2: String, e1: String, e2: String, notes: String): ContactData {
        val contactData = ContactData()

        contactData.name = name

        contactData.phones?.add(ContactPhoneNumber("-1", p1, ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_HOME))
        contactData.phones?.add(ContactPhoneNumber("-1", p2, ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_HOME))

        contactData.emails?.add(e1)
        contactData.emails?.add(e2)

        contactData.notes = notes

        return contactData
    }
}