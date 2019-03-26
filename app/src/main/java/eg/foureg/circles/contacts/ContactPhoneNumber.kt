package eg.foureg.circles.contacts

class ContactPhoneNumber constructor(){

    var phoneNumberRawIdUri: String = ""
    var phoneNumber: String = ""
    var phoneNumberType: PHONE_NUM_TYPE = PHONE_NUM_TYPE.PHONE_NUM_TYPE_MOBILE

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
}