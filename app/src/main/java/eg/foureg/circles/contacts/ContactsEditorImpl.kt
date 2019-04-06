package eg.foureg.circles.contacts

import android.content.ContentProviderOperation
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.RawContacts
import eg.foureg.circles.common.Logger
import eg.foureg.circles.contacts.data.ContactData
import eg.foureg.circles.contacts.data.ContactPhoneNumber
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class ContactsEditorImpl : ContactsEditor {
    /**
     * Generate new contact Raw ID
     */
    private fun getRawContactID(context: Context): Uri {
        Logger.debug(TAG, "getRawContactID() : Insert empty contact")

        val values = ContentValues()
        val rawContactUri = context.contentResolver.insert(
                RawContacts.CONTENT_URI, values)

        Logger.debug(TAG, "Raw Contact uri $rawContactUri")
        return rawContactUri!!
    }

    /**
     * Insert contact by name
     */
    private fun updateContactDisplayName(context: Context, rawContactUri: Uri, displayName: String): Uri {
        Logger.debug(TAG, "updateContactDisplayName() : Update Contact at: $rawContactUri, with Name: $displayName")
        Logger.debug(TAG, "updateContactDisplayName() : Original contact Uri: $rawContactUri")

        val contentValues = ContentValues()

        val rawContactId = ContentUris.parseId(rawContactUri).toInt()
        Logger.debug(TAG, "updateContactDisplayName() : Raw Contact Id $rawContactId")

        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)

        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)

        // Put contact display name value.
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, displayName)

        val updatedContactUri = context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
        Logger.debug(TAG, "updateContactDisplayName() : Updated contact URI by contact name: $updatedContactUri")

        return rawContactUri
    }


    /**
     * Update contact by Phone Number
     */
    private fun updateContactPhoneNumber(context: Context, rawContactUri: Uri, phoneNumber: ContactPhoneNumber): Uri {
        Logger.debug(TAG, "updateContactPhoneNumber() : Update Contact at: $rawContactUri, with Phone Number: ${phoneNumber.phoneNumber}")
        Logger.debug(TAG, "updateContactPhoneNumber() : Original contact Uri: $rawContactUri")

        val rawContactId = ContentUris.parseId(rawContactUri).toInt()
        Logger.debug(TAG, "updateContactPhoneNumber() : Raw Contact Id $rawContactId")


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
     * Update contact Email
     */
    private fun updateContactEmail(context: Context, rawContactUri: Uri, email: String): Uri {
        Logger.debug(TAG, "updateContactEmails() : Update Contact at: $rawContactUri, with Pemail: $email")
        Logger.debug(TAG, "updateContactEmails() : Original contact Uri: $rawContactUri")

        val rawContactId = ContentUris.parseId(rawContactUri).toInt()
        Logger.debug(TAG, "updateContactEmails() : Raw Contact Id $rawContactId")

        // add email
        val operations = ArrayList<ContentProviderOperation>()
        ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).apply {
            withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
            withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
            withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
            operations.add(build())
        }

        context.contentResolver.applyBatch(ContactsContract.AUTHORITY, operations)

        return rawContactUri
    }

    private fun insertNewContactPhoneNumbers(context: Context, name: String, phones: List<ContactPhoneNumber>?): Observable<Boolean> {

        return Observable.create<Boolean> { emitter ->
            Observable.fromIterable(phones)
                    .filter { phoneNumber ->
                        phoneNumber.phoneNumber.isNotEmpty()
                    }
                    .blockingSubscribe { phoneNumber ->
                        Observable.fromCallable { getRawContactID(context) }
                                .observeOn(Schedulers.io())
                                .map { rawContactUri ->
                                    updateContactDisplayName(context, rawContactUri, name)
                                }
                                .observeOn(Schedulers.io())
                                .map { rawContactUri ->
                                    updateContactPhoneNumber(context, rawContactUri, phoneNumber)
                                }
                                .subscribe()
                    }

            emitter.onNext(true)
        }

    }

    private fun insertNewContactEmails(context: Context, name: String, emails:List<String> ?): Observable<Boolean> {
        return Observable.create<Boolean> {emitter ->
            Observable.fromIterable(emails)
                    .filter { email ->
                        email.isNotEmpty()
                    }
                    .blockingSubscribe { email ->
                        Observable.fromCallable { getRawContactID(context) }
                                .observeOn(Schedulers.io())
                                .map { rawContactUri ->
                                    updateContactDisplayName(context, rawContactUri, name)
                                }
                                .observeOn(Schedulers.io())
                                .map { rawContactUri ->
                                    val emptyPhoneNumber: ContactPhoneNumber = ContactPhoneNumber(rawContactUri.toString(), "",
                                            ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_MOBILE)
                                    updateContactPhoneNumber(context, rawContactUri, emptyPhoneNumber)
                                }
                                .observeOn(Schedulers.io())
                                .map { rawContactUri ->
                                    updateContactEmail(context, rawContactUri, email)
                                }
                                .subscribe()


                    }
        }
    }

    /**
     * Insert new contact
     */
    override fun insertNewContact(context: Context, contact: ContactData): Observable<Boolean> {
        return Observable.create<Boolean> { emitter ->
            insertNewContactPhoneNumbers(context, contact.name, contact.phones)
                    .subscribe{
                        insertNewContactEmails(context, contact.name, contact.emails)
                                .subscribe()


                    }
            Observable.just("")
                    .delay(2, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { emitter.onNext(true) }

        }



    }


    /**
     * delete contact
     */
    override fun deleteContact(context: Context, contactID: String): Observable<Boolean> {
        return Observable.create<Boolean> { emitter ->
            Logger.debug(TAG, "deleteContact() : Deleted Contact at: $contactID")

            val contactUri = Uri.parse(contactID)
            Logger.debug(TAG, "deleteContact() : Contact Uri: $contactUri")
            val numOfDeletedContacts = context.contentResolver.delete(contactUri, null, null)
            Logger.debug(TAG, "deleteContact() : Number of deleted contacts: $numOfDeletedContacts")

            emitter.onNext(true)

        }


    }


    companion object {
        private const val TAG = "ContactsEditorImpl"
    }
}