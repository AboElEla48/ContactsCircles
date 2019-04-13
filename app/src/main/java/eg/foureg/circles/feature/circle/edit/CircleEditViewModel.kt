package eg.foureg.circles.feature.circle.edit

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.view.View
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.feature.circle.models.CirclesModel
import io.reactivex.Observable
import org.koin.android.ext.android.get

class CircleEditViewModel : ViewModel() {

    val circleName : MutableLiveData<String> = MutableLiveData()

    val progressVisibility : MutableLiveData<Int> = MutableLiveData()

    lateinit var circlesModel : CirclesModel

    fun initCircle(context: Context, circleData: CircleData?) {
        circlesModel = (context as Activity).get()

        circleName.value = circleData?.name
    }

    fun addNewCircle(context: Context) : Observable<Int> {

        progressVisibility.value = View.VISIBLE

        val newCircle = CircleData(circleName.value!!, ArrayList())

        return circlesModel.addNewCircle(context, newCircle)

    }


}