package eg.foureg.circles.contacts

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import io.reactivex.Observable
import io.reactivex.annotations.Nullable
import io.reactivex.functions.Consumer

/**
 * Define the data model for contact
 */
class ContactData constructor() : Parcelable{

    var name : String = ""
    var phones : ArrayList<ContactPhoneNumber>? = ArrayList()
    var emails : ArrayList<String>? = ArrayList()
    var photoID : Int = 0
    var image : Bitmap? = null
    var notes : String? = ""

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        phones = parcel.readArrayList(ContactData::class.java.classLoader) as ArrayList<ContactPhoneNumber>
        emails = parcel.readArrayList(ContactData::class.java.classLoader) as ArrayList<String>
        photoID = parcel.readInt()
        image = parcel.readParcelable(Bitmap::class.java.classLoader)
        notes = parcel.readString()
    }

    constructor(n : String,  pList : ArrayList<ContactPhoneNumber>?, eList : ArrayList<String>?, nts : String?) : this() {
        name = n
        phones = pList
        emails = eList
        notes = nts
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeList(phones)
        dest?.writeList(emails)
        dest?.writeInt(photoID)
        dest?.writeParcelable(image, 0)
        dest?.writeString(notes)
    }

    override fun describeContents(): Int {
        return 0
    }

    /**
     * Merge given contact data into existing contact data
     */
    fun mergeContact(newContact : ContactData) {
        Observable.fromIterable(newContact.phones)
                .filter { newPhone ->
                    var found = false
                    for(p in phones!!) {
                        if(p.phoneNumber.equals(newPhone.phoneNumber) && p.phoneNumberType.equals(newPhone.phoneNumberType)) {
                            found = true
                            break
                        }
                    }
                    !found
                }     // phone doesn't exist before
                .subscribe({ newPhone -> phones?.add(newPhone) })

        Observable.fromIterable(newContact.emails)
                .filter { newEmail : String -> !(emails!!.contains(newEmail)) }     // email doesn't exist before
                .subscribe({ newEmail : String -> emails?.add(newEmail) })
    }

    companion object CREATOR : Parcelable.Creator<ContactData> {
        override fun createFromParcel(parcel: Parcel): ContactData {
            return ContactData(parcel)
        }

        override fun newArray(size: Int): Array<ContactData?> {
            return arrayOfNulls(size)
        }
    }


}