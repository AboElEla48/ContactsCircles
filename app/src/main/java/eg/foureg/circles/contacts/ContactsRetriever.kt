package eg.foureg.circles.contacts

import android.content.Context
import android.provider.ContactsContract
import eg.foureg.circles.common.Logger
import kotlin.collections.ArrayList

/**
 * Retriever class for contacts saved on device
 */
class ContactsRetriever {
    /**
     * Load contacts book
     */
    fun loadContacts(context : Context) : List<ContactData> {

        // Open content resolver to retrieve contacts
        val contacts = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")

        val contactsList : ArrayList<ContactData> = ArrayList()

        // iterate contacts
        while (contacts!!.moveToNext()) {
            val name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val email = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS))

            Logger.debug(TAG, "Contact: " + name + ", " + phoneNumber + ", " + email)

            // extract contact
            val contactData = ContactData()
            contactData.name = name
            contactData.phones?.add(phoneNumber)
            contactData.emails?.add(email)

            // add contact
            contactsList.add(contactData)
        }

        // close content provider
        contacts.close()

        contactsList.clear()
        contactsList.add(ContactData("Ahmed", null, null, ""))
        contactsList.add(ContactData("Hassan", null, null, ""))
        contactsList.add(ContactData("Mostafa", null, null, ""))
        contactsList.add(ContactData("Kamal", null, null, ""))

        return contactsList
    }

    companion object {
        private const val TAG = "ContactsRetriever"
    }
}