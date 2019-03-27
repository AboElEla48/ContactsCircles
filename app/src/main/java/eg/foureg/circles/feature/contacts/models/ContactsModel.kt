package eg.foureg.circles.feature.contacts.models

import android.content.Context
import eg.foureg.circles.contacts.ContactData
import eg.foureg.circles.contacts.ContactsEditor
import eg.foureg.circles.contacts.ContactsRetriever
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

open class ContactsModel(retriever: ContactsRetriever, editor: ContactsEditor) {

    var contactsList: ArrayList<ContactData> = ArrayList()
    var contactsRetriever : ContactsRetriever
    var contactsEditor: ContactsEditor

    init {
        contactsRetriever = retriever
        contactsEditor = editor
    }

    fun loadContacts(context: Context): Observable<ArrayList<ContactData>> {
        return Observable.fromCallable { contactsRetriever.loadContacts(context) }

    }

    fun loadContactsImages(context: Context, contactsList: ArrayList<ContactData>?): Observable<ArrayList<ContactData>?> {
        return Observable.fromCallable { contactsRetriever.loadContactsImages(context, contactsList) }
    }

    /**
     * Remove duplicate contacts
     */
    fun removeDuplicate(rawContacts: ArrayList<ContactData>): Observable<ArrayList<ContactData>> {
        return Observable.create { emitter: ObservableEmitter<ArrayList<ContactData>> ->
            val contactsMap: HashMap<String, ContactData> = HashMap()
            val contactsNamesKeysOrdered: ArrayList<String> = ArrayList()

            // loop contacts to remove duplicates
            Observable.fromIterable(rawContacts)
                    .blockingSubscribe { contact: ContactData ->
                        if (contactsMap.containsKey(contact.name)) {
                            // merge contact if same name exist before
                            val oldContact: ContactData? = contactsMap.get(contact.name)
                            oldContact?.mergeContact(contact)
                        } else {
                            // add new contact
                            contactsMap.put(contact.name, contact)

                            // add items ordered to retrieve them in the same order
                            contactsNamesKeysOrdered.add(contact.name)
                        }
                    }

            contactsList = ArrayList()
            Observable.fromIterable(contactsNamesKeysOrdered)
                    .subscribe { contactNameKey: String ->
                        val contact: ContactData? = contactsMap.get(contactNameKey)

                        if (contact != null) {
                            contactsList.add(contact)
                        }
                    }

            emitter.onNext(contactsList)
            emitter.onComplete()
        }
    }

    fun addNewContact(context: Context, contactData: ContactData) {
        contactsEditor.insertNewContact(context, contactData)
    }

    fun deleteContact(context: Context, contactRawID: String) {
        contactsEditor.deleteContact(context, contactRawID)
    }
}
