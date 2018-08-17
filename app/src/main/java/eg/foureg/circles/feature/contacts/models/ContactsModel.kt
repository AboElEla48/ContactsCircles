package eg.foureg.circles.feature.contacts.models

import android.content.Context
import eg.foureg.circles.contacts.ContactData
import eg.foureg.circles.contacts.ContactsRetriever
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class ContactsModel {

    fun loadContacts(context: Context): Observable<List<ContactData>> {
        return Observable.just(ContactsRetriever().loadContacts(context))
                .subscribeOn(Schedulers.newThread())
                .flatMap { rawContacts: List<ContactData> -> removeDuplicate(rawContacts) }
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * Remove duplicate contacts
     */
    protected fun removeDuplicate(rawContacts: List<ContactData>): Observable<List<ContactData>> {
        return Observable.create { emitter: ObservableEmitter<List<ContactData>> ->
            val contactsMap: HashMap<String, ContactData> = HashMap()
            val contactsNamesKeysOrdered : ArrayList<String> = ArrayList()

            // loop contacts to remove duplicates
            Observable.fromIterable(rawContacts)
                    .blockingSubscribe({ contact: ContactData ->
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
                    })

            val nonDuplicateContacts: ArrayList<ContactData> = ArrayList()
            Observable.fromIterable(contactsNamesKeysOrdered)
                    .subscribe({ contactNameKey : String ->
                        val contact: ContactData?  = contactsMap.get(contactNameKey)

                        if(contact != null){
                            nonDuplicateContacts.add(contact)
                        } })

            emitter.onNext(nonDuplicateContacts)
            emitter.onComplete()
        }
    }
}
