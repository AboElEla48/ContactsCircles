package eg.foureg.circles.feature.main

import eg.foureg.circles.R
import eg.foureg.circles.feature.contacts.edit.ContactEditorFragment
import eg.foureg.circles.feature.main.MainActivity
import eg.foureg.circles.feature.contacts.listing.ContactsListFragment
import eg.foureg.circles.feature.contacts.view.ContactViewerFragment

/**
 * This is the navigator class that sets the fragments in Main activity
 */
class MainActivityFragmentsNavigator {

    fun setContactsListFragment(mainActivity : MainActivity) {
        val transaction = mainActivity.supportFragmentManager.beginTransaction()
        val contactsListFragment = ContactsListFragment.newInstance()
        transaction.replace(R.id.main_activity_content_frame_layout, contactsListFragment).commit()
    }

    fun setContactViewFragment(mainActivity: MainActivity, contactIndex : Int){
        val transaction = mainActivity.supportFragmentManager.beginTransaction()
        val contactsListFragment = ContactViewerFragment.newInstance(contactIndex)
        transaction.replace(R.id.main_activity_content_frame_layout, contactsListFragment).commit()
    }

    fun setContactEditorFragment(mainActivity: MainActivity, contactIndex : Int){
        val transaction = mainActivity.supportFragmentManager.beginTransaction()
        val contactsListFragment = ContactEditorFragment.newInstance(contactIndex)
        transaction.replace(R.id.main_activity_content_frame_layout, contactsListFragment).commit()
    }

}