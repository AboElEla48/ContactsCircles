package eg.foureg.circles.circles

import android.content.Context
import eg.foureg.circles.circles.data.CircleData
import io.reactivex.Observable

open class CirclesEditorImpl : CirclesEditor{

    fun contactUpdated(context: Context, oldContactId : String, newContactId : String) {
        // load circles list

        // Update contact reference in circles
    }

    protected fun updateContactReferenceInCircles(circlesList : List<CircleData>, oldContactId : String, newContactId : String) {
        Observable.fromIterable(circlesList)
                .subscribe { circle ->
                    circle.contactsIds.remove(oldContactId)
                    circle.contactsIds.add(newContactId)
                }
    }

    protected fun deleteContactReferenceInCircles(circlesList : List<CircleData>, oldContactId : String) {
        Observable.fromIterable(circlesList)
                .subscribe { circle ->
                    circle.contactsIds.remove(oldContactId)
                }
    }
}