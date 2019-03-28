package eg.foureg.circles.contacts

import android.content.Context
import android.net.Uri
import io.reactivex.Observable

interface ContactsEditor {
    fun insertNewContact(context: Context, contact: ContactData)
    fun deleteContact(context: Context, contactID: String) : Observable<Boolean>
}