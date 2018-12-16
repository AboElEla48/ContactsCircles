package eg.foureg.circles.feature.contacts.view

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.graphics.Bitmap
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
        ContactsModel.getInstance().deleteContact(context, contactIndex, phones.value!!, listener)
    }
}