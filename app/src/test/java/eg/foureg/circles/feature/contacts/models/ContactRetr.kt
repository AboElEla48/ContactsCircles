package eg.foureg.circles.feature.contacts.models

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import eg.foureg.circles.contacts.data.ContactData
import eg.foureg.circles.contacts.ContactsRetriever
import io.reactivex.Observable

class ContactRetr() : ContactsRetriever, Parcelable {
    override fun loadContactByPhoneNumber(context: Context, phoneNumber: String): Observable<ContactData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadContacts(context: Context): ArrayList<ContactData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadContactsImages(context: Context, contactsList: ArrayList<ContactData>?): ArrayList<ContactData>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ContactRetr> {
        override fun createFromParcel(parcel: Parcel): ContactRetr {
            return ContactRetr(parcel)
        }

        override fun newArray(size: Int): Array<ContactRetr?> {
            return arrayOfNulls(size)
        }
    }
}