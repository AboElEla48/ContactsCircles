package eg.foureg.circles.circles

import android.content.Context
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.contacts.data.ContactData
import io.reactivex.Observable

interface CirclesRetriever {
    fun loadCircles(context: Context) : Observable<List<CircleData>>
    fun loadCircleContacts(context: Context, circleId: Int): Observable<List<ContactData>>
}