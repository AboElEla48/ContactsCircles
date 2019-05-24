package eg.foureg.circles.feature.main.circles

import eg.foureg.circles.R
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.feature.main.circles.contactsviewer.CirclesContactsViewerFragment

class CirclesActivityFragmentsNavigator {

    fun setCircleContactsViewerFragment(activity: CirclesActivity, circleData: CircleData) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        val circlesViwerFragment = CirclesContactsViewerFragment.newInstance(circleData)
        transaction.replace(R.id.circles_activity_content_frame, circlesViwerFragment).commit()
    }
}