package eg.foureg.circles.contacts

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import eg.foureg.circles.common.Logger
import io.reactivex.Observable


class ContactsEditor {
    fun insertNewContact(context: Context, contact: ContactData) {
        Observable.fromIterable(contact.phones)
                .blockingSubscribe { phoneNumber ->
                    val values = ContentValues()
//        values.put(Data.RAW_CONTACT_ID, 1)
                    values.put(ContactsContract.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
                    values.put(Phone.NUMBER, phoneNumber)
                    values.put(Phone.TYPE, Phone.TYPE_MOBILE)
                    values.put(Phone.LABEL, contact.name)
                    context.getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values)

                }

    }

    fun updateContactName(context: Context, contactID: String, contactNewName: String): Observable<Boolean> {

        return Observable.create {
            // Create content values object.
            val contentValues = ContentValues()

            contentValues.put(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, contactNewName)

            val whereClause = ContactsContract.Data.RAW_CONTACT_ID + " =? " + contactID

            val update = context.contentResolver.update(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    contentValues,
                    whereClause,
                    null)


        }


    }

    fun updateContactNumbers(context: Context, contactID: String, phoneNumber: String): Observable<Boolean> {

        return Observable.create {
            // Create content values object.
            val contentValues = ContentValues()

            contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)

            val whereClause = ContactsContract.Data.RAW_CONTACT_ID + " = " + contactID

            val update = context.contentResolver.update(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    contentValues,
                    whereClause,
                    null)

            Logger.error("_Contact", "" + update)


        }


    }

    fun deleteContact(context: Context, phoneNumber: String) {
        val contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber))
        val cur = context.getContentResolver().query(contactUri, null, null, null, null)
        try {
            if (cur!!.moveToFirst()) {
                do {

                    val lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY))
                    val uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey)
                    context.getContentResolver().delete(uri, null, null)

                } while (cur.moveToNext())

            }

        } catch (e: Exception) {
            println(e.stackTrace)
        } finally {
            cur!!.close()

        }

    }

    fun getContactByID(context: Context, contactID: String): ContactData {
        // Create where condition clause
        val whereClause = ContactsContract.RawContacts._ID + " = '" + contactID + "'"

        val contactsCursor = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                whereClause,
                null,
                null)

        val contactData = ContactData()

        if (contactsCursor != null) {
            // Get contact count that has same display name, generally it should be one.
            val queryResultCount = contactsCursor.getCount()

            // This check is used to avoid cursor index out of bounds exception. android.database.CursorIndexOutOfBoundsException
            if (queryResultCount > 0) {
                // Move to the first row in the result cursor.
                contactsCursor.moveToFirst()

                contactData.name = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                contactData.phones?.add(contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)))
                val emailID = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID))

                contactData.emails = ContactsRetriever().loadEmailAddress(emailID, context.contentResolver)

            }

            contactsCursor.close()
        }



        return contactData

    }
}