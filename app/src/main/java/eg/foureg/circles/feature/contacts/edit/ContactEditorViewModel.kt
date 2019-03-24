package eg.foureg.circles.feature.contacts.edit

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.graphics.Bitmap
import eg.foureg.circles.contacts.ContactData
import eg.foureg.circles.contacts.ContactsEditorImpl
import eg.foureg.circles.feature.contacts.models.ContactsModel
import io.reactivex.Observable
import org.koin.android.ext.android.get

class ContactEditorViewModel : ViewModel() {

    var contactName : MutableLiveData<String> = MutableLiveData()
    var phones : MutableLiveData<ArrayList<String>> = MutableLiveData()
    var emails : MutableLiveData<ArrayList<String>> = MutableLiveData()
    var image : MutableLiveData<Bitmap> = MutableLiveData()

    lateinit var contactsModel : ContactsModel

    /**
     * init contact value
     */
    fun initContact(context: Context, contactIndex : Int?) {
        contactsModel = (context as Activity).get()

        if(contactIndex!! > -1) {
            val contactVal: ContactData = contactsModel.contactsList.get(contactIndex)

            contactName.value = contactVal.name
            phones.value = contactVal.phones
            emails.value = contactVal.emails
            image.value = contactVal.image
        }
    }

    fun saveContact(context: Context, contactData: ContactData) {
        contactsModel = (context as Activity).get()
        contactsModel.addNewContact(context, contactData)
    }
//
//    /**
//     * update contact
//     */
//    fun updateContact(context: Context, contactIndex: Int?) : Observable<Boolean> {
//        contactsModel = (context as Activity).get()
//
//        val contactVal : ContactData = contactsModel.contactsList.get(contactIndex?:0)
//
//        return ContactsEditorImpl().updateContactName(context, contactVal.id, contactName.value!!)
//    }
}