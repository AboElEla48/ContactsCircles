package eg.foureg.circles.circles

import eg.foureg.circles.circles.data.CircleData

class CircleEditorImplSample : CirclesEditorImpl() {
    fun testupdateContactReferenceInCircles(circlesList : List<CircleData>, oldContactId : String, newContactId : String) {
        updateContactReferenceInCircles(circlesList, oldContactId, newContactId)
    }

    fun testdeleteContactReferenceInCircles(circlesList : List<CircleData>, oldContactId : String) {
        deleteContactReferenceInCircles(circlesList, oldContactId)
    }

}