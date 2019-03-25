package eg.foureg.circles.feature.contacts.listing

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import eg.foureg.circles.contacts.ContactData
import eg.foureg.circles.feature.contacts.models.ContactsModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.koin.android.ext.android.get


class ContactsListViewModel : ViewModel() {
    var contacts: MutableLiveData<ArrayList<ContactData>> = MutableLiveData()

    lateinit var contactsModel : ContactsModel

    /**
     * load device contacts
     */
    fun loadContacts(context: Context) : PublishSubject<Boolean> {
        val publishSubject : PublishSubject<Boolean> = PublishSubject.create()
        contactsModel = (context as Activity).get()

        // Display contacts
        contactsModel.loadContacts(context)
                .subscribeOn(Schedulers.io())
                .flatMap { rawContacts: ArrayList<ContactData> -> contactsModel.removeDuplicate(rawContacts) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ contactsList: ArrayList<ContactData> ->
                    contacts.value = contactsList

                    loadContactsImages(context, contactsList)

                    publishSubject.onNext(true)

                }

        return publishSubject

    }

    private fun loadContactsImages(context: Context, contactsList : ArrayList<ContactData>) {
        contactsModel = (context as Activity).get()

        // load contacts images
        contactsModel.loadContactsImages(context, contactsList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ contactsListWithImages: ArrayList<ContactData>? ->
                    if (contactsListWithImages != null) {
                        contacts.value = contactsListWithImages
                    }
                }
    }
}
