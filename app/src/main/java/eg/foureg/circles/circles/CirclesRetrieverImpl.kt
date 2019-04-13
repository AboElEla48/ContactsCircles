package eg.foureg.circles.circles

import android.content.Context
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.common.PrefHelper
import io.reactivex.Observable

class CirclesRetrieverImpl : CirclesRetriever {

    override fun loadCircles(context: Context): Observable<List<CircleData>> {
        return Observable.create<List<CircleData>> { emitter ->
            val strsArr = PrefHelper.loadStringArray(context, PREF_CIRCLES_KEY)

            val circlesList = ArrayList<CircleData>()
            for (str in strsArr) {
                circlesList.add(CircleData.fromJson(str))
            }

            emitter.onNext(circlesList)
        }

    }

    companion object {
        const val PREF_CIRCLES_KEY = "PREF_CIRCLES_KEY"
    }
}