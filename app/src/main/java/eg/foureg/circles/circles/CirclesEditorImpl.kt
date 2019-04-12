package eg.foureg.circles.circles

import android.content.Context
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.common.PrefHelper
import io.reactivex.Observable

open class CirclesEditorImpl : CirclesEditor {
    override fun addNewCircle(context: Context, newCircleData: CircleData): Observable<Int> {
        return Observable.create<Int> { emitter ->
            val circlesRetrieverImpl = CirclesRetrieverImpl()

            circlesRetrieverImpl.loadCircles(context)
                    .subscribe { circlesList ->
                        val list = ArrayList(circlesList)

                        newCircleData.circleID = generateNewCircleId(list)

                        list.add(newCircleData)
                        saveCirclesList(context, list)
                                .subscribe {
                                    emitter.onNext(newCircleData.circleID)
                                }
                    }
        }

    }

    override fun deleteCircle(context: Context, circleId: Int): Observable<Boolean> {
        return Observable.create<Boolean> { emitter ->
            val circlesRetrieverImpl = CirclesRetrieverImpl()

            circlesRetrieverImpl.loadCircles(context)
                    .subscribe { circlesList ->
                        val list = ArrayList(circlesList)

                        val circleToDelete = findCircle(circleId, list)
                        if (circleToDelete != null) {
                            list.remove(circleToDelete)

                            saveCirclesList(context, list)
                                    .subscribe {
                                        emitter.onNext(true)
                                    }
                        } else {
                            emitter.onNext(false)
                        }

                    }
        }
    }

    override fun updateCircleName(context: Context, circleId: Int, newCircleName: String): Observable<Boolean> {
        return Observable.create<Boolean> { emitter ->
            val circlesRetrieverImpl = CirclesRetrieverImpl()

            circlesRetrieverImpl.loadCircles(context)
                    .subscribe { circlesList ->
                        val list = ArrayList(circlesList)

                        val circleToEdit = findCircle(circleId, list)
                        if (circleToEdit != null) {
                            circleToEdit.name = newCircleName

                            saveCirclesList(context, list)
                                    .subscribe {
                                        emitter.onNext(true)
                                    }
                        } else {
                            emitter.onNext(false)
                        }

                    }
        }
    }

    override fun addContactToCircle(context: Context, newContactId: String, circleId: Int): Observable<Boolean> {
        return Observable.create<Boolean> { emitter ->
            val circlesRetrieverImpl = CirclesRetrieverImpl()

            circlesRetrieverImpl.loadCircles(context)
                    .subscribe { circlesList ->
                        val list = ArrayList(circlesList)

                        val circleToEdit = findCircle(circleId, list)
                        if (circleToEdit != null) {

                            // Assure this id doesn't exist before
                            val found = isContactIdExistInCircle(circleToEdit, newContactId)

                            // add new contact if not added before
                            if (!found) {
                                circleToEdit.contactsIds.add(newContactId)

                                saveCirclesList(context, list)
                                        .subscribe {
                                            emitter.onNext(true)
                                        }
                            } else {
                                // contact already exist
                                emitter.onNext(true)
                            }
                        } else {
                            emitter.onNext(false)
                        }

                    }
        }
    }

    override fun removeContactFromCircle(context: Context, contactId: String, circleId: Int): Observable<Boolean> {
        return Observable.create<Boolean> { emitter ->
            val circlesRetrieverImpl = CirclesRetrieverImpl()

            circlesRetrieverImpl.loadCircles(context)
                    .subscribe { circlesList ->
                        val list = ArrayList(circlesList)

                        val circleToEdit = findCircle(circleId, list)
                        if (circleToEdit != null) {
                            circleToEdit.contactsIds.remove(contactId)

                            saveCirclesList(context, list)
                                    .subscribe {
                                        emitter.onNext(true)
                                    }
                        } else {
                            emitter.onNext(false)
                        }

                    }
        }
    }

    override fun contactUpdated(context: Context, oldContactId: String, newContactId: String): Observable<Boolean> {
        return Observable.create<Boolean> { emitter ->
            val circlesRetrieverImpl = CirclesRetrieverImpl()

            // load circles contacts
            circlesRetrieverImpl.loadCircles(context)
                    .subscribe { circlesList ->
                        val list = ArrayList(circlesList)

                        // Update contact reference in all circles contain old id
                        val updated = updateContactReferenceInCircles(circlesList, oldContactId, newContactId)

                        if (updated) {
                            saveCirclesList(context, list)
                                    .subscribe {
                                        emitter.onNext(true)
                                    }
                        } else {
                            //old contact didn't exist
                            emitter.onNext(true)
                        }

                    }
        }
    }

    override fun contactDeleted(context: Context, oldContactId: String): Observable<Boolean> {
        return Observable.create<Boolean> { emitter ->
            val circlesRetrieverImpl = CirclesRetrieverImpl()

            // load circles contacts
            circlesRetrieverImpl.loadCircles(context)
                    .subscribe { circlesList ->
                        val list = ArrayList(circlesList)

                        // delete contact id from all circles contain old id
                        val updated = deleteContactReferenceInCircles(list, oldContactId)

                        if (updated) {
                            saveCirclesList(context, list)
                                    .subscribe {
                                        emitter.onNext(true)
                                    }
                        } else {
                            //old contact didn't exist
                            emitter.onNext(true)
                        }
                    }
        }
    }

    private fun saveCirclesList(context: Context, circlesList: List<CircleData>): Observable<Boolean> {
        return Observable.create<Boolean> { emitter ->
            val circlesStrs: ArrayList<String> = ArrayList()

            for (circle in circlesList) {
                circlesStrs.add(circle.toString())
            }
            PrefHelper.saveStringArray(context, CirclesRetrieverImpl.PREF_CIRCLES_KEY, circlesStrs)

            emitter.onNext(true)
        }

    }

    protected fun findCircle(circleId: Int, circles: List<CircleData>): CircleData? {
        for (circle in circles) {
            if (circle.circleID == circleId) {
                return circle
            }
        }

        return null
    }

    protected fun generateNewCircleId(list: List<CircleData>): Int {
        if (list.isNotEmpty()) {
            // search for the biggest number in array
            var max = list.get(0).circleID
            for (circle in list) {
                if (circle.circleID > max) {
                    max = circle.circleID
                } 

            }

            return max + 1
        }

        return 1
    }

    protected fun updateContactReferenceInCircles(circlesList: List<CircleData>, oldContactId: String, newContactId: String) : Boolean {
        var updated = false

        // Update contact reference in circles
        for (circle in circlesList) {

            if (isContactIdExistInCircle(circle, oldContactId)) {
                circle.contactsIds.remove(oldContactId)
                circle.contactsIds.add(newContactId)

                updated = true
            }
        }

        return updated
    }

    protected fun deleteContactReferenceInCircles(circlesList: List<CircleData>, oldContactId: String) : Boolean {

        var updated = false

        for (circle in circlesList) {
            if (isContactIdExistInCircle(circle, oldContactId)) {

                // remove contact from all circles that associate to it
                circle.contactsIds.remove(oldContactId)

                updated = true

            }
        }

        return updated
    }

    protected fun isContactIdExistInCircle(circle: CircleData, contactId: String): Boolean {

        for (id in circle.contactsIds) {
            if (id == contactId) {
                return true
            }
        }

        return false
    }
}