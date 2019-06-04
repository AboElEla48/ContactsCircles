package eg.foureg.circles.feature.activitycircles.contactsadder

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import eg.foureg.circles.contacts.data.ContactData
import eg.foureg.circles.feature.contacts.models.ContactsModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.get

class CircleAddContactsViewModel : ViewModel() {

    lateinit var contactsModel : ContactsModel

    val contacts : MutableLiveData<ArrayList<ContactData>> = MutableLiveData()

    fun initContacts(activity: Activity) {
        contactsModel = activity.get()

        contactsModel.loadContacts(activity as Context)
                .subscribeOn(Schedulers.io())
                .flatMap { rawContacts: ArrayList<ContactData> -> contactsModel.removeDuplicate(rawContacts) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{cList ->
                    contacts.value = cList
                }
    }
}