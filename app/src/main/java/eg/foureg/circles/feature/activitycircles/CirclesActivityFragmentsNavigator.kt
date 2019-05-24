package eg.foureg.circles.feature.activitycircles

import eg.foureg.circles.R
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.feature.activitycircles.contactsviewer.CircleContactsViewerFragment

class CirclesActivityFragmentsNavigator {

    fun setCircleContactsViewerFragment(activity: CirclesActivity, circleData: CircleData) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        val circlesViwerFragment = CircleContactsViewerFragment.newInstance(circleData)
        transaction.replace(R.id.circles_activity_content_frame, circlesViwerFragment).commit()
    }
}