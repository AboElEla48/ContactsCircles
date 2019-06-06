package eg.foureg.circles.feature.circle.models

import android.content.Context
import eg.foureg.circles.circles.CirclesEditor
import eg.foureg.circles.circles.CirclesRetriever
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.contacts.data.ContactData
import io.reactivex.Observable

class CirclesModel(cRetriever : CirclesRetriever, cEditor : CirclesEditor) {

    var circlesRetriever : CirclesRetriever
    var circlesEditor : CirclesEditor

    init {
        circlesEditor = cEditor
        circlesRetriever = cRetriever
    }

    fun loadCircles(context: Context) : Observable<List<CircleData>> {
        return circlesRetriever.loadCircles(context)
    }

    fun loadCircleContacts(context: Context, circleData: CircleData) : Observable<ArrayList<ContactData>> {
        return  circlesRetriever.loadCircleContacts(context, circleData.circleID)
    }

    fun addNewCircle(context: Context, circleData: CircleData): Observable<Int>{
        return circlesEditor.addNewCircle(context, circleData)
    }

    fun updateCircleName(context: Context, circleID: Int, newCircleName: String): Observable<Boolean>{
        return circlesEditor.updateCircleName(context, circleID, newCircleName)
    }

    fun updateCircleContacts(context: Context, circleId: Int, circleContacts : ArrayList<String>): Observable<Boolean> {
        return circlesEditor.updateCircleContacts(context, circleId, circleContacts)
    }

    fun deleteCircle(context: Context, circleId : Int) : Observable<Boolean> {
        return circlesEditor.deleteCircle(context, circleId)
    }

    fun addContactToCircle(context: Context, contactUri : String, circleId : Int): Observable<Boolean> {
        return circlesEditor.addContactToCircle(context, contactUri, circleId)
    }




}