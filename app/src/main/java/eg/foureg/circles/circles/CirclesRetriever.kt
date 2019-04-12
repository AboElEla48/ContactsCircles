package eg.foureg.circles.circles

import android.content.Context
import eg.foureg.circles.circles.data.CircleData
import io.reactivex.Observable

interface CirclesRetriever {
    fun loadCircles(context: Context) : Observable<List<CircleData>>
}