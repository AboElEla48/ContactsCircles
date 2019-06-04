package eg.foureg.circles.circles

import android.content.Context
import eg.foureg.circles.circles.data.CircleData
import io.reactivex.Observable

interface CirclesEditor {
    fun addNewCircle(context: Context, newCircleData: CircleData) : Observable<Int>
    fun deleteCircle(context: Context, circleId : Int) : Observable<Boolean>
    fun updateCircleName(context: Context, circleId: Int, newCircleName: String): Observable<Boolean>
    fun updateCircleContacts(context: Context, circleId: Int, contacts: ArrayList<String>): Observable<Boolean>


    fun addContactToCircle(context: Context, newContactUri: String, circleId: Int): Observable<Boolean>
    fun removeContactFromCircle(context: Context, contactId: String, circleId: Int): Observable<Boolean>

    fun contactUpdated(context: Context, oldContactId : String, newContactId : String): Observable<Boolean>
    fun contactDeleted(context: Context, oldContactId: String): Observable<Boolean>
}