package eg.foureg.circles.feature.circle.edit

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.feature.circle.models.CirclesModel
import org.koin.android.ext.android.get

class CircleEditViewModel : ViewModel() {

    val circleName : MutableLiveData<String> = MutableLiveData()
    val circleContacts : MutableLiveData<ArrayList<String>> = MutableLiveData()

    lateinit var circlesModel : CirclesModel

    fun initCircle(context: Context, circleData: CircleData?) {
        circlesModel = (context as Activity).get()

        circleName.value = circleData?.name
        circleContacts.value = circleData?.contactsIds
    }

    fun addNewCircle() {

    }


}