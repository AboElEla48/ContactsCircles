package eg.foureg.circles.feature.contacts.view

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.PhoneLookup
import eg.foureg.circles.contacts.ContactData
import eg.foureg.circles.feature.contacts.models.ContactsModel
import io.reactivex.Observable


class ContactViewViewModel : ViewModel() {
    var contactName: MutableLiveData<String> = MutableLiveData()
    var phones: MutableLiveData<List<String>> = MutableLiveData()
    var emails: MutableLiveData<List<String>> = MutableLiveData()
    var image: MutableLiveData<Bitmap> = MutableLiveData()

    var contactIndex: Int = 0

    fun initContact(contactIndex: Int?) {
        val contactVal: ContactData = ContactsModel.getInstance().contactsList.get(contactIndex
                ?: 0)

        this.contactIndex = contactIndex!!

        contactName.value = contactVal.name
        phones.value = contactVal.phones
        emails.value = contactVal.emails
        image.value = contactVal.image
    }

    fun deleteContact(context: Context, listener : Observable<Boolean>) {
        Observable.fromIterable(phones.value)
                .subscribe { phone ->
                    val contactUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone))
                    val cur = context.getContentResolver().query(contactUri, null, null, null, null)
                    try {
                        if (cur!!.moveToFirst()) {
                            do {

                                val lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY))
                                val uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey)
                                context.getContentResolver().delete(uri, null, null)

                            } while (cur.moveToNext())

                            // delete contact from loaded contacts list
                            ContactsModel.getInstance().contactsList.removeAt(contactIndex)
                        }

                    } catch (e: Exception) {
                        println(e.stackTrace)
                    } finally {
                        cur!!.close()
                        listener.subscribe()
                    }

                }.dispose()
    }
}