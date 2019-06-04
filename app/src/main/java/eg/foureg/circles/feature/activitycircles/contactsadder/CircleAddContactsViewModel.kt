package eg.foureg.circles.feature.activitycircles.contactsadder

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import eg.foureg.circles.contacts.data.ContactData
import eg.foureg.circles.feature.circle.models.CirclesModel
import eg.foureg.circles.feature.contacts.models.ContactsModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.get

class CircleAddContactsViewModel : ViewModel() {

    lateinit var contactsModel : ContactsModel
    lateinit var circlesModel : CirclesModel

    val contacts : MutableLiveData<ArrayList<ContactData>> = MutableLiveData()

    fun initContacts(activity: Activity) {
        contactsModel = activity.get()
        circlesModel = activity.get()

        contactsModel.loadContacts(activity as Context)
                .subscribeOn(Schedulers.io())
                .flatMap { rawContacts: ArrayList<ContactData> -> contactsModel.removeDuplicate(rawContacts) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{cList ->
                    contacts.value = cList
                }
    }

    fun updateCircleContacts(activity: Activity, circleId: Int, contactsUri: ArrayList<String>): Observable<Boolean> {
       return  circlesModel.updateCircleContacts(activity as Context, circleId, contactsUri)
    }
}