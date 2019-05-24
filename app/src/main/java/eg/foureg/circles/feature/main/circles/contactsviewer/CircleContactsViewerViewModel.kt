package eg.foureg.circles.feature.main.circles.contactsviewer

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.view.View
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.contacts.data.ContactData
import eg.foureg.circles.feature.circle.models.CirclesModel
import org.koin.android.ext.android.get

class CircleContactsViewerViewModel : ViewModel() {

    lateinit var circlesModel: CirclesModel
    private lateinit var circleData: CircleData

    val contactsList = MutableLiveData<ArrayList<ContactData>>()
    val circleName = MutableLiveData<String>()
    val loadingProgressBarVisibility = MutableLiveData<Int>()

    fun initCircle(context : Context, circle: CircleData) {

        circlesModel = (context as Activity).get()

        circleData = circle

        //set circle name
        circleName.value = circleData.name

        // set circle contacts
        circlesModel.loadCircleContacts(context, circleData).subscribe {
            loadingProgressBarVisibility.value = View.GONE
        }


    }
}