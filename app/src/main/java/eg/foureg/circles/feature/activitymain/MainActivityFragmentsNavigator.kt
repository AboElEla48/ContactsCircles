package eg.foureg.circles.feature.activitymain

import eg.foureg.circles.R
import eg.foureg.circles.feature.circle.listing.CirclesListFragment
import eg.foureg.circles.feature.contacts.listing.ContactsListFragment

/**
 * This is the navigator class that sets the fragments in Main activity
 */
class MainActivityFragmentsNavigator {

    val contactsListFragmentTag = "contactsListFragmentTag"

    fun setHeaderCirclesListFragment(mainActivity: MainActivity) {
        val transaction = mainActivity.supportFragmentManager.beginTransaction()
        val circlesViwerFragment = CirclesListFragment.newInstance()
        transaction.replace(R.id.main_activity_header_frame_layout, circlesViwerFragment).commit()
    }

    fun setContactsListFragment(mainActivity : MainActivity) {
        val transaction = mainActivity.supportFragmentManager.beginTransaction()
        val contactsListFragment = ContactsListFragment.newInstance()
        transaction.replace(R.id.main_activity_content_frame_layout, contactsListFragment, contactsListFragmentTag).commit()
    }

    fun removeContactListFragment(mainActivity: MainActivity) {
        val frg = mainActivity.supportFragmentManager.findFragmentByTag(contactsListFragmentTag)
        if(frg != null) {
            val transaction = mainActivity.supportFragmentManager.beginTransaction()
            transaction.remove(frg).commit()
        }
    }


}