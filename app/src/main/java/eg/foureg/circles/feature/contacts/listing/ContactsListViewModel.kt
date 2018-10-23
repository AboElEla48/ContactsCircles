package eg.foureg.circles.feature.contacts.listing

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import eg.foureg.circles.contacts.ContactData
import eg.foureg.circles.feature.contacts.models.ContactsModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ContactsListViewModel : ViewModel() {
    var contacts: MutableLiveData<ArrayList<ContactData>> = MutableLiveData()

    /**
     * load device contacts
     */
    fun loadContacts(context: Context) {
        // Display contacts
        ContactsModel.getInstance().loadContacts(context)
                .subscribeOn(Schedulers.io())
                .flatMap { rawContacts: ArrayList<ContactData> -> ContactsModel.getInstance().removeDuplicate(rawContacts) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ contactsList: ArrayList<ContactData> ->
                    contacts.value = contactsList

                    loadContactsImages(context, contactsList)

                }

    }

    private fun loadContactsImages(context: Context, contactsList : ArrayList<ContactData>) {
        // load contacts images
        ContactsModel.getInstance().loadContactsImages(context, contactsList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ contactsListWithImages: ArrayList<ContactData>? ->
                    if (contactsListWithImages != null) {
                        contacts.value = contactsListWithImages
                    }
                }
    }
}