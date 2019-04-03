package eg.foureg.circles.feature.contacts.view

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.graphics.Bitmap
import eg.foureg.circles.contacts.ContactData
import eg.foureg.circles.contacts.ContactPhoneNumber
import eg.foureg.circles.feature.contacts.models.ContactsModel
import io.reactivex.Observable
import org.koin.android.ext.android.get


class ContactViewViewModel : ViewModel() {
    var contactName: MutableLiveData<String> = MutableLiveData()
    var phones: MutableLiveData<List<ContactPhoneNumber>> = MutableLiveData()
    var emails: MutableLiveData<List<String>> = MutableLiveData()
    var image: MutableLiveData<Bitmap> = MutableLiveData()


    lateinit var contactsModel : ContactsModel

    fun initContact(context: Context, contactVal: ContactData) {
        contactsModel = (context as Activity).get()

        contactName.value = contactVal.name
        phones.value = contactVal.phones
        emails.value = contactVal.emails
        image.value = contactVal.image

    }

    fun deleteContact(context: Context) : Observable<Boolean> {

        contactsModel = (context as Activity).get()

        return contactsModel.deleteContact(context, phones.value)

    }
}