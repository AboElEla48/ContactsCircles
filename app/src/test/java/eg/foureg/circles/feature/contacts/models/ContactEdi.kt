package eg.foureg.circles.feature.contacts.models

import android.content.Context
import android.net.Uri
import eg.foureg.circles.contacts.data.ContactData
import eg.foureg.circles.contacts.ContactsEditor
import io.reactivex.Observable

class ContactEdi : ContactsEditor {
    override fun deleteContact(context: Context, contactID: String): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertNewContact(context: Context, contact: ContactData): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}