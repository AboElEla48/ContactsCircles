package eg.foureg.circles.contacts

class ContactPhoneNumber constructor(){

    var phoneNumber: String = ""
    var phoneNumberType: PHONE_NUM_TYPE = PHONE_NUM_TYPE.PHONE_NUM_TYPE_MOBILE

    constructor(pNum: String, pType:PHONE_NUM_TYPE) :this() {
        phoneNumber = pNum
        phoneNumberType = pType
    }

    enum class PHONE_NUM_TYPE {
        PHONE_NUM_TYPE_MOBILE,
        PHONE_NUM_TYPE_WORK,
        PHONE_NUM_TYPE_HOME
    }
}