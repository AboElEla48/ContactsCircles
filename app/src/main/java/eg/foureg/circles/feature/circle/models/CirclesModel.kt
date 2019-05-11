package eg.foureg.circles.feature.circle.models

import android.content.Context
import eg.foureg.circles.circles.CirclesEditor
import eg.foureg.circles.circles.CirclesRetriever
import eg.foureg.circles.circles.data.CircleData
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

    fun addNewCircle(context: Context, circleData: CircleData): Observable<Int>{
        return circlesEditor.addNewCircle(context, circleData)
    }

    fun deleteCircle(context: Context, circleId : Int) : Observable<Boolean> {
        return circlesEditor.deleteCircle(context, circleId)
    }

    fun addContactToCircle(context: Context, contactId : String, circleId : Int): Observable<Boolean> {
        return circlesEditor.addContactToCircle(context, contactId, circleId)
    }




}