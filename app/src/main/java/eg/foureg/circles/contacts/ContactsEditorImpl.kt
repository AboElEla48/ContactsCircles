package eg.foureg.circles.contacts

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.RawContacts
import eg.foureg.circles.common.Logger
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class ContactsEditorImpl : ContactsEditor {
    /**
     * Generate new contact Raw ID
     */
    private fun getRawContactID(context: Context): Uri {
        Logger.error(TAG, "getRawContactID() : Insert empty contact")

        val values = ContentValues()
        val rawContactUri = context.contentResolver.insert(
                RawContacts.CONTENT_URI, values)

        Logger.error(TAG, "Raw Contact uri $rawContactUri")
        return rawContactUri!!
    }

    /**
     * Insert contact by name
     */
    private fun updateContactDisplayName(context: Context, rawContactUri: Uri, displayName: String) : Uri {
        Logger.error(TAG, "updateContactDisplayName() : Update Contact at: $rawContactUri, with Name: $displayName")
        Logger.error(TAG, "updateContactDisplayName() : Original contact Uri: $rawContactUri")

        val contentValues = ContentValues()

        val rawContactId = ContentUris.parseId(rawContactUri).toInt()
        Logger.error(TAG, "updateContactDisplayName() : Raw Contact Id $rawContactId")

        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)

        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)

        // Put contact display name value.
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, displayName)

        val updatedContactUri = context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
        Logger.error(TAG, "updateContactDisplayName() : Updated contact URI by contact name: $updatedContactUri")

        return rawContactUri
    }


    /**
     * Update contact by Phone Number
     */
    private fun updateContactPhoneNumber(context: Context, rawContactUri: Uri, phoneNumber: String) : Uri {
        Logger.error(TAG, "updateContactPhoneNumber() : Update Contact at: $rawContactUri, with Phone Number: $phoneNumber")
        Logger.error(TAG, "updateContactPhoneNumber() : Original contact Uri: $rawContactUri")

        val contentValues = ContentValues()

        val rawContactId = ContentUris.parseId(rawContactUri).toInt()
        Logger.error(TAG, "updateContactPhoneNumber() : Raw Contact Id $rawContactId")

        // Put contact Phone Number value.
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)

        val whereClauseBuf = StringBuffer()
        whereClauseBuf.append(ContactsContract.Data.RAW_CONTACT_ID)
        whereClauseBuf.append("=")
        whereClauseBuf.append(rawContactId)

        Logger.error(TAG, "updateContactPhoneNumber() : Where Caluse: $whereClauseBuf")

        val updatedContacts = context.contentResolver.update(ContactsContract.Data.CONTENT_URI, contentValues,
                whereClauseBuf.toString(), null)
        Logger.error(TAG, "updateContactPhoneNumber() : Updated contact URI by contact Phone Number: $updatedContacts")

        return rawContactUri
    }

    /**
     * Insert new contact
     */
    override fun insertNewContact(context: Context, contact: ContactData) {
        Observable.fromIterable(contact.phones)
                .blockingSubscribe { phoneNumber ->
                    Observable.fromCallable { getRawContactID(context) }
                            .observeOn(Schedulers.io())
                            .map { rawContactUri ->
                                updateContactDisplayName(context, rawContactUri, contact.name)
                            }
                            .observeOn(Schedulers.io())
                            .map { rawContactUri ->
                                updateContactPhoneNumber(context, rawContactUri, phoneNumber)
                            }
                            .subscribe()


                }

    }

//    /**
    //     * Update contact name of given contact Id
    //     */
//    private fun updateContactName(context: Context, rawContactId: Int, contactName: String): Int {
//
////        val rawContactId = ContentUris.parseId(rawContactUri).toInt()
//
//        val values = ContentValues()
////        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
////        values.put(ContactsContract.Data.CONTACT_ID, rawContactId)
////        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
//        values.put(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, contactName)
//
//        Logger.error(TAG, "Contact Name to be updated")
//        val numberOfUpdatedContacts = context.contentResolver.update(ContactsContract.Data.CONTENT_URI,
//                values,
//                null,
////                ContactsContract.Data.RAW_CONTACT_ID + " = " + rawContactId
////                + " and " + ContactsContract.Data.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'",
//                null)
//        Logger.error(TAG, "Number of updated contacts by contact name: $numberOfUpdatedContacts")
//
//        return rawContactId
//    }
//
//    /**
//     * Update contact Number of given contact Id
//     */
//    private fun updateContactNumber(context: Context, rawContactId: Int, phoneNumber: String): Int {
//        val values = ContentValues()
//        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId + 1)
//
//        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
//        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
//
//        // change first name
//        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, "Zabady")
//        //change last name
//        values.put(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, "Family")
//
////        values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME_PRIMARY, "Contact Given Name")
//
//        Logger.error(TAG, "Contact Number to be updated")
//        val numberOfUpdatedContacts = context.contentResolver.insert(ContactsContract.Data.CONTENT_URI,
//                values)
////                ContactsContract.Data.RAW_CONTACT_ID + "=? ",
////                arrayOf("" + rawContactId))
//        Logger.error(TAG, "Number of updated contacts by contact number: $numberOfUpdatedContacts")
//
//        return rawContactId
//    }
//
//

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

            Logger.error(TAG, "Number of updated items $update")


        }


    }
//
//    fun updateContactNumbers(context: Context, contactID: String, phoneNumber: String): Observable<Boolean> {
//
//        return Observable.create {
//            // Create content values object.
//            val contentValues = ContentValues()
//
//            contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
//
//            val whereClause = ContactsContract.Data.RAW_CONTACT_ID + " = " + contactID
//
//            val update = context.contentResolver.update(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                    contentValues,
//                    whereClause,
//                    null)
//
//            Logger.error("_Contact", "" + update)
//
//
//        }
//
//
//    }

    fun deleteContact(context: Context, phoneNumber: String) {
        val contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber))
        val cur = context.contentResolver.query(contactUri, null, null, null, null)
        try {
            if (cur!!.moveToFirst()) {
                do {

                    val lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY))
                    val uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey)
                    context.contentResolver.delete(uri, null, null)

                } while (cur.moveToNext())

            }

        } catch (e: Exception) {
            println(e.stackTrace)
        } finally {
            cur!!.close()

        }

    }
//
//    fun getContactByID(context: Context, contactID: String): ContactData {
//        // Create where condition clause
//        val whereClause = ContactsContract.RawContacts._ID + " = '" + contactID + "'"
//
//        val contactsCursor = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                null,
//                whereClause,
//                null,
//                null)
//
//        val contactData = ContactData()
//
//        if (contactsCursor != null) {
//            // Get contact count that has same display name, generally it should be one.
//            val queryResultCount = contactsCursor.count
//
//            // This check is used to avoid cursor index out of bounds exception. android.database.CursorIndexOutOfBoundsException
//            if (queryResultCount > 0) {
//                // Move to the first row in the result cursor.
//                contactsCursor.moveToFirst()
//
//                contactData.name = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
//                contactData.phones?.add(contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)))
//                val emailID = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID))
//
//                contactData.emails = ContactsRetrieverImpl().loadEmailAddress(emailID, context.contentResolver)
//
//            }
//
//            contactsCursor.close()
//        }
//
//
//
//        return contactData
//
//    }

    companion object {
        private const val TAG = "ContactsEditorImpl"
    }
}