package eg.foureg.circles.feature.circle.listing

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.feature.circle.models.CirclesModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.get

class CirclesListViewModel : ViewModel() {

    lateinit var circlesModel: CirclesModel
    val circlesList : MutableLiveData<List<CircleData>> = MutableLiveData()

    fun loadCircles(context : Context) : Observable<Boolean> {
        circlesModel = (context as Activity).get()

        return Observable.create<Boolean> { emiter ->
            circlesModel.loadCircles(context)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { list ->
                        circlesList.value = list

                        emiter.onNext(true)
                    }
        }
    }
}