package eg.foureg.circles.feature.contacts.models

import android.content.Context
import eg.foureg.circles.contacts.ContactData
import eg.foureg.circles.contacts.ContactsRetriever

class ContactsModel {

    fun loadContacts(context : Context) : List<ContactData> {
        return ContactsRetriever().loadContacts(context)
    }
}