package eg.foureg.circles.feature.contacts.listing

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import eg.foureg.circles.contacts.ContactData
import eg.foureg.circles.feature.contacts.models.ContactsModel
import io.reactivex.Observable


class ContactsListViewModel : ViewModel() {
    var contacts: MutableLiveData<ArrayList<ContactData>> = MutableLiveData()

    /**
     * load device contacts
     */
    fun loadContacts(context: Context) {
        // Display contacts
        ContactsModel.getInstance().loadContacts(context)
                .subscribe({ contactsList: ArrayList<ContactData> ->
                    contacts.value = contactsList

                    // load contacts images
                    Observable.just(ContactsModel.getInstance().loadContactsImages(context, contactsList)
                            .subscribe({ contactsList: ArrayList<ContactData>? ->
                                if (contactsList != null) {
                                    contacts.value = contactsList
                                }
                            }))

                })

    }
}