package eg.foureg.circles.circles.data

import android.os.Parcel
import android.os.Parcelable

class CircleData constructor() : Parcelable {
    var name : String = ""


    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
    }

    constructor(cName : String) : this(){
        name = cName
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
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