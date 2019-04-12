package eg.foureg.circles.circles.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson

class CircleData constructor() : Parcelable {
    var circleID : Int = 0
    var name : String = ""
    var contactsIds : ArrayList<String> = ArrayList()


    constructor(parcel: Parcel) : this() {
        val circleData = Gson().fromJson<CircleData>(parcel.readString(), CircleData::class.java)

        circleID = circleData.circleID
        name = circleData.name
        contactsIds = circleData.contactsIds
    }

    constructor(cId : Int, cName : String, ids : ArrayList<String>) : this(){
        circleID = cId
        name = cName
        contactsIds = ids
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(Gson().toJson(this))
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CircleData> {
        override fun createFromParcel(parcel: Parcel): CircleData {
            return CircleData(parcel)
        }

        override fun newArray(size: Int): Array<CircleData?> {
            return arrayOfNulls(size)
        }
    }
}