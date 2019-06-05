package eg.foureg.circles.circles

import android.content.Context
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.common.PrefHelper
import eg.foureg.circles.contacts.ContactsRetrieverImpl
import eg.foureg.circles.contacts.data.ContactData
import io.reactivex.Observable

class CirclesRetrieverImpl : CirclesRetriever {
    /**
     * Load saved circles
     */
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

    /**
     * load contacts of given circle
     */
    override fun loadCircleContacts(context: Context, circleId: Int): Observable<List<ContactData>> {
        return Observable.create<List<ContactData>>{emitter ->
            val circlesStr = PrefHelper.loadStringArray(context, CirclesRetrieverImpl.PREF_CIRCLES_KEY)

            Observable.fromIterable(circlesStr)
                    .map { str ->
                        val loadedCircle : CircleData = CircleData.fromJson(str)
                        loadedCircle
                    }
                    .filter { circle ->
                        circle.circleID == circleId
                    }
                    .subscribe { targetCircle ->
                        val contactsRetrieverImpl = ContactsRetrieverImpl()
                        contactsRetrieverImpl.loadContactFromUri(context, targetCircle.contactsIds.get(0))
                    }
        }
    }

    companion object {
        const val PREF_CIRCLES_KEY = "PREF_CIRCLES_KEY"
    }
}