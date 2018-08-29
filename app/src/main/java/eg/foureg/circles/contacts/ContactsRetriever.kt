package eg.foureg.circles.contacts

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.ContactsContract
import eg.foureg.circles.common.Logger
import io.reactivex.Observable
import kotlin.collections.ArrayList
import android.content.ContentResolver


/**
 * Retriever class for contacts saved on device
 */
class ContactsRetriever {
    /**
     * Load contacts book
     */
    fun loadContacts(context: Context): ArrayList<ContactData> {

        // Open content resolver to retrieve contactsCursor
        val contactsCursor = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")

        val contactsList: ArrayList<ContactData> = ArrayList()

        // iterate contactsCursor
        while (contactsCursor!!.moveToNext()) {
//            val id = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts._ID))
            val id = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID))

            val name = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//            val email = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))

            Logger.error(TAG, "Name: " + name + ", Phone Number:" + phoneNumber)

            val photoID = contactsCursor.getInt(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_ID))

            // extract contact
            val contactData = ContactData()
            contactData.name = name

            contactData.phones?.add(phoneNumber)
            contactData.emails = loadEmailAddress(id, context.contentResolver)
//            contactData.emails?.add(email)
            contactData.photoID = photoID

            // add contact
            contactsList.add(contactData)
        }

        // close content provider
        contactsCursor.close()

        return contactsList
    }

    /**
     * load contacts images in different stream
     */
    fun loadContactsImages(context: Context, contactsList: ArrayList<ContactData>?): ArrayList<ContactData>? {
        Observable.fromIterable(contactsList)
                .blockingSubscribe({ contact: ContactData? ->
                    if (contact != null && contact.photoID != 0) {
                        contact.image = fetchContactImage(context, contact.photoID)
                    }
                })

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

    private fun loadEmailAddress(id: String, contentResolver: ContentResolver): ArrayList<String> {

        // Open content resolver to retrieve contactsCursor
        val emailsCursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                arrayOf(id),
                null)

        val emails: ArrayList<String> = ArrayList()

        while (emailsCursor.moveToNext()) {
            val email = emailsCursor.getString(emailsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
            emails.add(email)

            Logger.error(TAG, "ID: " + id + ", Email: " + email)
        }

        emailsCursor.close()
        return emails
    }

    companion object {
        private const val TAG = "ContactsRetriever"
    }
}