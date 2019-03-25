package eg.foureg.circles.contacts

import android.content.Context

interface ContactsEditor {
    fun insertNewContact(context: Context, contact: ContactData)
}