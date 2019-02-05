package eg.foureg.circles

import eg.foureg.circles.contacts.ContactsRetriever
import eg.foureg.circles.feature.contacts.models.ContactsModel
import eg.foureg.circles.contacts.ContactsProvider
import org.koin.dsl.module.module

val appModule = module {
    single<ContactsProvider> { ContactsRetriever() }

    factory { ContactsModel.getInstance(get())}
}