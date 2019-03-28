package eg.foureg.circles.feature.contacts.edit

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.graphics.Bitmap
import eg.foureg.circles.contacts.ContactData
import eg.foureg.circles.contacts.ContactPhoneNumber
import eg.foureg.circles.contacts.ContactsEditorImpl
import eg.foureg.circles.feature.contacts.models.ContactsModel
import io.reactivex.Observable
import org.koin.android.ext.android.get

class ContactEditorViewModel : ViewModel() {

    var contactName : MutableLiveData<String> = MutableLiveData()
    var phones : MutableLiveData<ArrayList<ContactPhoneNumber>> = MutableLiveData()
    var emails : MutableLiveData<ArrayList<String>> = MutableLiveData()
    var image : MutableLiveData<Bitmap> = MutableLiveData()

    lateinit var contactsModel : ContactsModel

    /**
     * init contact value
     */
    fun initContact(context: Context, contactVal: ContactData?) {
        contactsModel = (context as Activity).get()

        if(contactVal != null) {
            contactName.value = contactVal.name
            phones.value = contactVal.phones
            emails.value = contactVal.emails
            image.value = contactVal.image
        }
    }

    fun saveContact(context: Context, contactData: ContactData) : Observable<Boolean> {
        contactsModel = (context as Activity).get()
        return contactsModel.addNewContact(context, contactData)
    }

    fun updateContact(context: Context, oldPhones:List<ContactPhoneNumber>?, newContact: ContactData) {
        contactsModel = (context as Activity).get()
        contactsModel.deleteContact(context, oldPhones).subscribe {
            saveContact(context, newContact)
        }
    }

}