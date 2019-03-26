package eg.foureg.circles.contacts

import android.graphics.Bitmap
import io.reactivex.Observable
import io.reactivex.annotations.Nullable
import io.reactivex.functions.Consumer

/**
 * Define the data model for contact
 */
class ContactData constructor(){
    var id: String = ""
    var name : String = ""
    var phones : ArrayList<ContactPhoneNumber>? = ArrayList()
    var emails : ArrayList<String>? = ArrayList()
    var photoID : Int = 0
    var image : Bitmap? = null
    var notes : String? = ""

    constructor(n : String,  pList : ArrayList<ContactPhoneNumber>?, eList : ArrayList<String>?, nts : String?) : this() {
        name = n
        phones = pList
        emails = eList
        notes = nts

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


}