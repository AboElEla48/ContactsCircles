package eg.foureg.circles.contacts

import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract
import eg.foureg.circles.common.Logger
import io.reactivex.Observable


class ContactsEditor {
    fun updateContactName(context: Context, contactID : String, contactNewName : String) : Observable<Boolean> {

        return Observable.create { // Create content values object.
            val contentValues = ContentValues()

            contentValues.put(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, contactNewName)

            val whereClause = ContactsContract.Data.RAW_CONTACT_ID + " =? " + contactID

            val update = context.contentResolver.update(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    contentValues,
                    whereClause,
                    null)


        }


    }

    fun updateContactNumbers(context: Context, contactID: String, phoneNumber: String) : Observable<Boolean> {

        return Observable.create { // Create content values object.
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

    private fun deleteContact(context: Context, rawContactId:String, givenName: String, familyName: String) {


        //******************************* delete data table related data ****************************************
        // Data table content process uri.
        val dataContentUri = ContactsContract.Data.CONTENT_URI

        // Create data table where clause.
        val dataWhereClauseBuf = StringBuffer()
        dataWhereClauseBuf.append(ContactsContract.Data.RAW_CONTACT_ID)
        dataWhereClauseBuf.append(" = ")
        dataWhereClauseBuf.append(rawContactId)

        // Delete all this contact related data in data table.
        context.contentResolver.delete(dataContentUri, dataWhereClauseBuf.toString(), null)


        //******************************** delete raw_contacts table related data ***************************************
        // raw_contacts table content process uri.
        val rawContactUri = ContactsContract.RawContacts.CONTENT_URI

        // Create raw_contacts table where clause.
        val rawContactWhereClause = StringBuffer()
        rawContactWhereClause.append(ContactsContract.RawContacts._ID)
        rawContactWhereClause.append(" = ")
        rawContactWhereClause.append(rawContactId)

        // Delete raw_contacts table related data.
        context.contentResolver.delete(rawContactUri, rawContactWhereClause.toString(), null)

        //******************************** delete contacts table related data ***************************************
        // contacts table content process uri.
        val contactUri = ContactsContract.Contacts.CONTENT_URI

        // Create contacts table where clause.
        val contactWhereClause = StringBuffer()
        contactWhereClause.append(ContactsContract.Contacts._ID)
        contactWhereClause.append(" = ")
        contactWhereClause.append(rawContactId)

        // Delete raw_contacts table related data.
        context.contentResolver.delete(contactUri, contactWhereClause.toString(), null)

    }

    fun getContactByID(context: Context, contactID : String) : ContactData {
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