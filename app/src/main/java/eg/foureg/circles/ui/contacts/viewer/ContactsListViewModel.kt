package eg.foureg.circles.ui.contacts.viewer

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import eg.foureg.circles.contacts.ContactData
import eg.foureg.circles.contacts.ContactsRetriever

class ContactsListViewModel : ViewModel() {
    var contacts : MutableLiveData<List<ContactData>> = MutableLiveData()

    fun loadContacts(context : Context) {
        contacts.value = ContactsRetriever().loadContacts(context)
    }
}