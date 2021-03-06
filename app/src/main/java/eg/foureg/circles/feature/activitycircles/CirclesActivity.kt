package eg.foureg.circles.feature.activitycircles

import android.os.Bundle
import eg.foureg.circles.R
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.common.message.data.Message
import eg.foureg.circles.common.ui.BaseActivity
import eg.foureg.circles.contacts.data.ContactData
import eg.foureg.circles.feature.circle.models.CirclesModel
import org.koin.android.ext.android.inject

class CirclesActivity : BaseActivity() {

    val circlesModel : CirclesModel by inject()

    val fragmentsNavigator : CirclesActivityFragmentsNavigator = CirclesActivityFragmentsNavigator()
    lateinit var circleData: CircleData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circles)

        circleData = intent.extras!!.getParcelable(CirclesActivityMessages.DATA_PARAM_CIRCLE_DATA)!!

        fragmentsNavigator.setCircleContactsViewerFragment(this, circleData)
    }

    override fun handleMessage(message: Message) {
        super.handleMessage(message)
        when(message.id) {
            CirclesActivityMessages.MSG_ID_ADD_CONCTACTS_TO_CIRCLE -> {
                val contactsList: ArrayList<ContactData> = message.data.get(CirclesActivityMessages.DATA_PARAM_CIRCLE_CONTACTS_NAMES_DATA) as ArrayList<ContactData>
                fragmentsNavigator.setCircleAddContactsFragment(this, circleData, contactsList)
            }

            CirclesActivityMessages.MSG_ID_SHOW_CIRCLE_CONTACTS -> {
                fragmentsNavigator.setCircleContactsViewerFragment(this, circleData)
            }
        }
    }
}
