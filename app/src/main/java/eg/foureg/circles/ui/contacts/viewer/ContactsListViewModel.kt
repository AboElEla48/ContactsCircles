package eg.foureg.circles.ui.contacts.viewer

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import eg.foureg.circles.contacts.ContactData

class ContactsListViewModel : ViewModel() {
    var contacts : MutableLiveData<List<ContactData>> = MutableLiveData()
}