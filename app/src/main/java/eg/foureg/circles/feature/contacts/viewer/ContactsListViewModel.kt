package eg.foureg.circles.feature.contacts.viewer

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import eg.foureg.circles.contacts.ContactData
import eg.foureg.circles.feature.contacts.models.ContactsModel


class ContactsListViewModel : ViewModel() {
    var contacts : MutableLiveData<List<ContactData>> = MutableLiveData()

    /**
     * load device contacts
     */
    fun loadContacts(context : Context) {
        ContactsModel().loadContacts(context)
                .subscribe({ contactsList: List<ContactData> -> contacts.value = contactsList })
    }
}