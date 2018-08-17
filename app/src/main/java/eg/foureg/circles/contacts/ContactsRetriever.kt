package eg.foureg.circles.contacts

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.ContactsContract
import eg.foureg.circles.common.Logger
import io.reactivex.Observable
import kotlin.collections.ArrayList

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
            val name = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val email = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS))

            val photoID = contactsCursor.getInt(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_ID))

            Logger.debug(TAG, "Contact: " + name + ", " + phoneNumber + ", " + email)

            // extract contact
            val contactData = ContactData()
            contactData.name = name
            contactData.phones?.add(phoneNumber)
            contactData.emails?.add(email)
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
    fun loadContactsImages(context: Context, contactsList: ArrayList<ContactData>?) : ArrayList<ContactData>?{
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

    companion object {
        private const val TAG = "ContactsRetriever"
    }
}