package eg.foureg.circles.feature.main.circles.contactsviewer

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.view.View
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.contacts.data.ContactData

class CircleContactsViewerViewModel : ViewModel() {

    private lateinit var circleData: CircleData

    val contactsList = MutableLiveData<ArrayList<ContactData>>()
    val circleName = MutableLiveData<String>()
    val loadingProgressBarVisibility = MutableLiveData<Int>()

    fun initCircle(circle: CircleData) {

        circleData = circle

        circleName.value = circleData.name
        loadingProgressBarVisibility.value = View.GONE
    }
}