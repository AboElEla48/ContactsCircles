package eg.foureg.circles.feature.activitymain.content

import eg.foureg.circles.R
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.contacts.data.ContactData
import eg.foureg.circles.feature.circle.edit.CircleEditFragment
import eg.foureg.circles.feature.contacts.edit.ContactEditorFragment
import eg.foureg.circles.feature.contacts.view.ContactViewerFragment

class ContentActivityFragmentsNavigator {

    fun setContactViewFragment(contentActivity: ContentActivity, contact : ContactData){
        val transaction = contentActivity.supportFragmentManager.beginTransaction()
        val contactsListFragment = ContactViewerFragment.newInstance(contact)
        transaction.replace(R.id.content_activity_content_frame_layout, contactsListFragment).commit()
    }

    fun setContactEditorFragment(contentActivity: ContentActivity, contact: ContactData?){
        val transaction = contentActivity.supportFragmentManager.beginTransaction()
        val contactsListFragment = ContactEditorFragment.newInstance(contact)
        transaction.replace(R.id.content_activity_content_frame_layout, contactsListFragment).commit()
    }

    fun setCircleEditorFragment(contentActivity: ContentActivity, circle: CircleData?){
        val transaction = contentActivity.supportFragmentManager.beginTransaction()
        val circleFragment = CircleEditFragment.newInstance(circle)
        transaction.replace(R.id.content_activity_content_frame_layout, circleFragment).commit()
    }
}