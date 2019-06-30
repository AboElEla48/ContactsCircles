package eg.foureg.circles.contacts

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.ContactsContract
import eg.foureg.circles.common.Logger
import io.reactivex.Observable
import kotlin.collections.ArrayList
import android.content.ContentResolver
import android.net.Uri
import eg.foureg.circles.contacts.data.ContactData
import eg.foureg.circles.contacts.data.ContactPhoneNumber
import android.R.attr.phoneNumber
import android.provider.Contacts.Phones.CONTENT_FILTER_URL


/**
 * Retriever class for contacts saved on device
 */
class ContactsRetrieverImpl : ContactsRetriever {
    /**
     * Load contacts book
     */
    override fun loadContacts(context: Context): Observable<ArrayList<ContactData>> {

        return Observable.create<ArrayList<ContactData>> { emitter ->
            // Open content resolver to retrieve contactsCursor
            val contactsCursor = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    null,
                    null,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")

            val contactsList: ArrayList<ContactData> = ArrayList()

            var counter = 0

            // iterate contactsCursor
            while (contactsCursor!!.moveToNext()) {
                val id = Uri.withAppendedPath(ContactsContract.RawContacts.CONTENT_URI,
                        contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID))).toString()

                val emailID = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID))

                val name = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val phoneType = contactsCursor.getInt(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))

                Logger.debug(TAG, "ID: $id, Name: $name, Phone Number:$phoneNumber")

                val photoID = contactsCursor.getInt(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_ID))

                // extract contact
                val contactData = ContactData()
                contactData.name = if (name == null) {
                    "TempName"
                } else {
                    name
                }

                val type = when (phoneType) {
                    ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE ->
                        ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_MOBILE
                    ContactsContract.CommonDataKinds.Phone.TYPE_WORK ->
                        ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_WORK
                    ContactsContract.CommonDataKinds.Phone.TYPE_HOME ->
                        ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_HOME

                    else ->
                        ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_MOBILE
                }

                contactData.phones?.add(ContactPhoneNumber(id, phoneNumber, type))
                contactData.emails = loadEmailAddress(emailID, context.contentResolver)
                contactData.photoID = photoID

                // add contact
                contactsList.add(contactData)

                counter++
                if(counter % 5 == 0) {
                    emitter.onNext(contactsList)
                }
            }

            // close content provider
            contactsCursor.close()

            emitter.onNext(contactsList)
            emitter.onComplete()
        }
    }

    override fun loadContactByPhoneNumber(context: Context, phoneNumber: String): Observable<ContactData> {
        return Observable.create<ContactData> { emitter ->

            loadContacts(context)
                    .subscribe { contacts ->
                        Observable.fromIterable(contacts)
                                .filter { contact: ContactData ->
                                    var containsPhone = false
                                    for (i in 0 until contact.phones?.size!!) {
                                        if(contact.phones?.get(i)?.phoneNumber == phoneNumber) {
                                            containsPhone = true
                                            break
                                        }
                                    }
                                    containsPhone
                                }
                                .blockingSubscribe { contact ->
                                    emitter.onNext(contact)
                                }

                    }
        }


    }

    /**
     * load contacts images in different stream
     */
    override fun loadContactsImages(context: Context, contactsList: ArrayList<ContactData>?): ArrayList<ContactData>? {
        Observable.fromIterable(contactsList)
                .blockingSubscribe { contact: ContactData? ->
                    if (contact != null && contact.photoID != 0) {
                        contact.image = fetchContactImage(context, contact.photoID)
                    }
                }

        return contactsList
    }

    private fun fetchContactImage(context: Context, photoID: Int?): Bitmap? {
        val photoCursor = context.contentResolver.query(ContactsContract.Data.CONTENT_URI,
                Array<String>(1) { ContactsContract.CommonDataKinds.Photo.PHOTO },
                ContactsContract.Data._ID + "=?",
                Array<String>(1) { photoID.toString() },
                null
        )

        if (photoCursor!!.moveToFirst()) {
            val imageBytes: ByteArray = photoCursor.getBlob(0)
            photoCursor.close()
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        }

        photoCursor.close()
        return null
    }

    /**
     * Load email address of given contact
     */
    fun loadEmailAddress(id: String, contentResolver: ContentResolver): ArrayList<String> {

        // Open content resolver to retrieve contactsCursor
        val emailsCursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                arrayOf(id),
                null)

        val emails: ArrayList<String> = ArrayList()

        if (emailsCursor != null) {
            while (emailsCursor.moveToNext()) {
                val email = emailsCursor.getString(emailsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                emails.add(email)

                Logger.debug(TAG, "ID: $id, Email: $email")
            }

            emailsCursor.close()
        }
        return emails
    }


    companion object {
        private const val TAG = "ContactsRetrieverImpl"
    }
}