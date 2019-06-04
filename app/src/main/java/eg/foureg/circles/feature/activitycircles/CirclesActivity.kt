package eg.foureg.circles.feature.activitycircles

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import eg.foureg.circles.R
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.common.message.data.Message
import eg.foureg.circles.common.ui.BaseActivity
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
                fragmentsNavigator.setCircleAddContactsFragment(this, circleData)
            }
        }
    }
}
