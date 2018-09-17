package eg.foureg.circles.feature.contacts.edit

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import eg.foureg.circles.contacts.ContactData
import eg.foureg.circles.feature.contacts.models.ContactsModel

class ContactEditorViewModel : ViewModel() {

    var contactName : MutableLiveData<String> = MutableLiveData()
    var phones : MutableLiveData<ArrayList<String>> = MutableLiveData()
    var emails : MutableLiveData<ArrayList<String>> = MutableLiveData()
    var image : MutableLiveData<Bitmap> = MutableLiveData()

    /**
     * init contact value
     */
    fun initContact(contactIndex : Int?) {
        val contactVal : ContactData = ContactsModel.getInstance().contactsList.get(contactIndex?:0)

        contactName.value = contactVal.name
        phones.value = contactVal.phones
        emails.value = contactVal.emails
        image.value = contactVal.image
    }

    /**
     * save contact
     */
    fun saveContact() {

    }
}