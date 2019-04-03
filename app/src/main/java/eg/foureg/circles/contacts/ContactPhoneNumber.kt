package eg.foureg.circles.contacts

import android.os.Parcel
import android.os.Parcelable

class ContactPhoneNumber constructor() : Parcelable{
    var phoneNumberRawIdUri: String = ""
    var phoneNumber: String = ""
    var phoneNumberType: PHONE_NUM_TYPE = PHONE_NUM_TYPE.PHONE_NUM_TYPE_MOBILE

    constructor(parcel: Parcel) : this() {
        phoneNumberRawIdUri = parcel.readString()
        phoneNumber = parcel.readString()
        when(parcel.readInt()) {
            0 ->
                phoneNumberType = PHONE_NUM_TYPE.PHONE_NUM_TYPE_MOBILE
            1 ->
                phoneNumberType = PHONE_NUM_TYPE.PHONE_NUM_TYPE_WORK
            2 ->
                phoneNumberType = PHONE_NUM_TYPE.PHONE_NUM_TYPE_HOME
        }

    }

    constructor(pUri: String, pNum: String, pType:PHONE_NUM_TYPE) :this() {
        phoneNumberRawIdUri = pUri
        phoneNumber = pNum
        phoneNumberType = pType
    }

    enum class PHONE_NUM_TYPE {
        PHONE_NUM_TYPE_MOBILE,
        PHONE_NUM_TYPE_WORK,
        PHONE_NUM_TYPE_HOME
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(phoneNumberRawIdUri)
        dest?.writeString(phoneNumber)
        dest?.writeInt(phoneNumberType.ordinal)
    }

    override fun describeContents(): Int {
        return 0
    }



    companion object CREATOR : Parcelable.Creator<ContactPhoneNumber> {
        override fun createFromParcel(parcel: Parcel): ContactPhoneNumber {
            return ContactPhoneNumber(parcel)
        }

        override fun newArray(size: Int): Array<ContactPhoneNumber?> {
            return arrayOfNulls(size)
        }
    }
}