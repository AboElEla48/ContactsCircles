package eg.foureg.circles.feature.contacts.models

import android.content.Context
import eg.foureg.circles.contacts.ContactData
import eg.foureg.circles.contacts.ContactsEditor
import eg.foureg.circles.contacts.ContactsProvider
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

open class ContactsModel protected constructor() {

    var contactsList: ArrayList<ContactData> = ArrayList()
    lateinit var contactsProvider : ContactsProvider

    companion object {
        private val model: ContactsModel = ContactsModel()

        fun getInstance(provider: ContactsProvider): ContactsModel {
            model.contactsProvider = provider
            return model
        }
    }


    fun loadContacts(context: Context): Observable<ArrayList<ContactData>> {
        return if (contactsList.size == 0) {
            Observable.fromCallable { contactsProvider.loadContacts(context) }

        } else {
            Observable.fromCallable { contactsList }
        }

    }


    fun loadContactsImages(context: Context, contactsList: ArrayList<ContactData>?): Observable<ArrayList<ContactData>?> {
        return Observable.fromCallable { contactsProvider.loadContactsImages(context, contactsList) }
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

    fun addNewContact(context: Context, contactData: ContactData, listener : Observable<Boolean>) {
        ContactsEditor().insertNewContact(context, contactData)

        listener.subscribe()
    }

    fun deleteContact(context: Context, contactIndex: Int, phones : List<String>, listener : Observable<Boolean>) {
        Observable.fromIterable(phones)
                .subscribe { phoneNumber ->
                    ContactsEditor().deleteContact(context, phoneNumber)

                    // delete contact from loaded contacts list
                    contactsList.removeAt(contactIndex)

                    listener.subscribe()

                }.dispose()
    }
}
