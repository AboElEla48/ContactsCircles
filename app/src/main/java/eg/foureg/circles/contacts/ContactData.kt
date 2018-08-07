package eg.foureg.circles.contacts

import io.reactivex.annotations.Nullable

/**
 * Define the data model for contact
 */
class ContactData constructor(){
    var name : String = ""
    var phones : ArrayList<String>? = ArrayList()
    var emails : ArrayList<String>? = ArrayList()
    var notes : String? = ""

    constructor(n : String,  pList : ArrayList<String>?, eList : ArrayList<String>?, nts : String?) : this() {
        name = n
        phones = pList
        emails = eList
        notes = nts

    }

}