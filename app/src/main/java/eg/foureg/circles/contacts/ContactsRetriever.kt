package eg.foureg.circles.contacts

import android.content.Context
import eg.foureg.circles.contacts.ContactData

interface ContactsRetriever {
    fun loadContacts(context: Context): ArrayList<ContactData>
    fun loadContactsImages(context: Context, contactsList: ArrayList<ContactData>?): ArrayList<ContactData>?

}