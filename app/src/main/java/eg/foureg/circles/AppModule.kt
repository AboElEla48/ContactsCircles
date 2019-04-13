package eg.foureg.circles

import eg.foureg.circles.circles.CirclesEditor
import eg.foureg.circles.circles.CirclesEditorImpl
import eg.foureg.circles.circles.CirclesRetriever
import eg.foureg.circles.circles.CirclesRetrieverImpl
import eg.foureg.circles.contacts.ContactsEditor
import eg.foureg.circles.contacts.ContactsEditorImpl
import eg.foureg.circles.contacts.ContactsRetrieverImpl
import eg.foureg.circles.feature.contacts.models.ContactsModel
import eg.foureg.circles.contacts.ContactsRetriever
import eg.foureg.circles.feature.circle.models.CirclesModel
import org.koin.dsl.module.module

val appContactsModule = module {
    single<ContactsRetriever> { ContactsRetrieverImpl() }
    single<ContactsEditor> { ContactsEditorImpl() }

    factory { ContactsModel(get(), get())}
}

val appCirclesModule = module {
    single<CirclesEditor> { CirclesEditorImpl() }
    single<CirclesRetriever> { CirclesRetrieverImpl() }

    factory { CirclesModel(get(), get()) }
}