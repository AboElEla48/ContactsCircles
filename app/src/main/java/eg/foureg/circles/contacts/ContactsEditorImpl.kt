package eg.foureg.circles.contacts

import android.content.ContentProviderOperation
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
    private fun updateContactDisplayName(context: Context, rawContactUri: Uri, displayName: String): Uri {
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
    private fun updateContactPhoneNumber(context: Context, rawContactUri: Uri, phoneNumber: ContactPhoneNumber): Uri {
        Logger.error(TAG, "updateContactPhoneNumber() : Update Contact at: $rawContactUri, with Phone Number: ${phoneNumber.phoneNumber}")
        Logger.error(TAG, "updateContactPhoneNumber() : Original contact Uri: $rawContactUri")

        val rawContactId = ContentUris.parseId(rawContactUri).toInt()
        Logger.error(TAG, "updateContactPhoneNumber() : Raw Contact Id $rawContactId")


        val type = when (phoneNumber.phoneNumberType) {
            ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_MOBILE ->
                ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE
            ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_WORK ->
                ContactsContract.CommonDataKinds.Phone.TYPE_WORK
            ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_HOME ->
                ContactsContract.CommonDataKinds.Phone.TYPE_HOME
        }

        // add phone number
        val operations = ArrayList<ContentProviderOperation>()
        ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).apply {
            withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
            withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
            withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber.phoneNumber)
            withValue(ContactsContract.CommonDataKinds.Phone.TYPE, type)
            operations.add(build())
        }

        context.contentResolver.applyBatch(ContactsContract.AUTHORITY, operations)

        return rawContactUri
    }

    /**
     * Update contact by Email
     */
    private fun updateContactEmails(context: Context, rawContactUri: Uri, emails: List<String>?): Uri {
        Observable.fromIterable(emails)
                .filter { email ->
                    email.isNotEmpty()
                }
                .subscribe { email ->
                    Logger.error(TAG, "updateContactEmails() : Update Contact at: $rawContactUri, with Pemail: $email")
                    Logger.error(TAG, "updateContactEmails() : Original contact Uri: $rawContactUri")

                    val rawContactId = ContentUris.parseId(rawContactUri).toInt()
                    Logger.error(TAG, "updateContactEmails() : Raw Contact Id $rawContactId")

                    // add email
                    val operations = ArrayList<ContentProviderOperation>()
                    ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).apply {
                        withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                        withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                        withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
                        operations.add(build())
                    }

                    context.contentResolver.applyBatch(ContactsContract.AUTHORITY, operations)

                }


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
                            .observeOn(Schedulers.io())
                            .map { rawContactUri ->
                                updateContactEmails(context, rawContactUri, contact.emails)
                            }
                            .subscribe()


                }

    }


    /**
     * Update existing contact
     */
    override fun updateContact(context: Context, contact: ContactData) {
        Observable.fromCallable { contact.id }
                .observeOn(Schedulers.io())
                .map { id ->
                    deleteContact(context, id)
                }
                .map { insertNewContact(context, contact) }
    }

    /**
     * delete contact
     */
    override fun deleteContact(context: Context, contactID: String) : Uri {
        Logger.error(TAG, "deleteContact() : Deleted Contact at: $contactID")

        val contactUri = Uri.parse(contactID)
        Logger.error(TAG, "deleteContact() : Contact Uri: $contactUri")
        val numOfDeletedContacts = context.contentResolver.delete(contactUri, null, null)
        Logger.error(TAG, "deleteContact() : Number of deleted contacts: $numOfDeletedContacts")

        return contactUri

    }



    companion object {
        private const val TAG = "ContactsEditorImpl"
    }
}