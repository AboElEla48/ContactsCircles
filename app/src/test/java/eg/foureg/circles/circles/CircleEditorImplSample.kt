package eg.foureg.circles.circles

import eg.foureg.circles.circles.data.CircleData

class CircleEditorImplSample : CirclesEditorImpl() {
    fun test_updateContactReferenceInCircles(circlesList : List<CircleData>, oldContactId : String, newContactId : String) {
        updateContactReferenceInCircles(circlesList, oldContactId, newContactId)
    }

    fun test_deleteContactReferenceInCircles(circlesList : List<CircleData>, oldContactId : String) {
        deleteContactReferenceInCircles(circlesList, oldContactId)
    }

    fun test_generateNewCircleId(list: List<CircleData>) : Int {
        return generateNewCircleId(list)
    }

    fun test_findCircle(circleId: Int, circles: List<CircleData>): CircleData? {
        return findCircle(circleId, circles)
    }

    fun test_isContactIdExistInCircle(circle : CircleData, contactId: String): Boolean {
        return isContactIdExistInCircle(circle, contactId)
    }

}