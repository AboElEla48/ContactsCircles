package eg.foureg.circles.feature.contacts.models

import android.content.Context
import eg.foureg.circles.contacts.ContactData
import io.reactivex.Observable

interface ContactsProvider {
    fun loadContacts(context: Context): Observable<ArrayList<ContactData>>
}