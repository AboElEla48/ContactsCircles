package eg.foureg.circles.contacts

import android.content.Context
import eg.foureg.circles.contacts.data.ContactData
import io.reactivex.Observable

interface ContactsRetriever {
    fun loadContacts(context: Context): ArrayList<ContactData>
    fun loadContactsImages(context: Context, contactsList: ArrayList<ContactData>?): ArrayList<ContactData>?

    fun loadContactByPhoneNumber(context: Context, phoneNumber: String): Observable<ContactData>

}