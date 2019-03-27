package eg.foureg.circles

import eg.foureg.circles.contacts.ContactsEditor
import eg.foureg.circles.contacts.ContactsEditorImpl
import eg.foureg.circles.contacts.ContactsRetrieverImpl
import eg.foureg.circles.feature.contacts.models.ContactsModel
import eg.foureg.circles.contacts.ContactsRetriever
import org.koin.dsl.module.module

val appModule = module {
    single<ContactsRetriever> { ContactsRetrieverImpl() }
    single<ContactsEditor> { ContactsEditorImpl() }

    factory { ContactsModel(get(), get())}
}