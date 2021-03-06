package eg.foureg.circles.feature.activitycircles.contactsviewer

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.view.View
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.contacts.data.ContactData
import eg.foureg.circles.feature.circle.models.CirclesModel
import eg.foureg.circles.feature.contacts.models.ContactsModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.get

class CircleContactsViewerViewModel : ViewModel() {

    lateinit var circlesModel: CirclesModel
    lateinit var contactsModel: ContactsModel
    private lateinit var circleData: CircleData

    val contactsList = MutableLiveData<ArrayList<ContactData>>()
    val circleName = MutableLiveData<String>()
    val loadingProgressBarVisibility = MutableLiveData<Int>()

    fun initCircle(context: Context, circle: CircleData) {

        circlesModel = (context as Activity).get()
        contactsModel = (context as Activity).get()

        circleData = circle

        //set circle name
        circleName.value = circleData.name

        // set circle contacts
        circlesModel.loadCircleContacts(context, circleData).subscribe { contacts ->

            // remove duplicates
            Observable.just(contacts)
                    .subscribeOn(Schedulers.computation())
                    .flatMap { rawContacts: ArrayList<ContactData> ->
                        contactsModel.removeDuplicate(rawContacts)
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {contacts->
                        loadingProgressBarVisibility.value = View.GONE
                        contactsList.value = contacts }



        }

    }

    fun updateCircleName(context: Context, newCircleName: String): Observable<Boolean> {
        circleName.value = newCircleName

        return circlesModel.updateCircleName(context, circleData.circleID, newCircleName)
    }
}