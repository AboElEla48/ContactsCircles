package eg.foureg.circles.feature.main.circles

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import eg.foureg.circles.R
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.feature.circle.models.CirclesModel
import org.koin.android.ext.android.inject

class CirclesActivity : AppCompatActivity() {

    val circlesModel : CirclesModel by inject()

    val fragmentsNavigator : CirclesActivityFragmentsNavigator = CirclesActivityFragmentsNavigator()
    lateinit var circleData: CircleData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circles)

        circleData = intent.extras!!.getParcelable(CirclesActivityMessages.DATA_PARAM_CIRCLE_DATA)!!

        fragmentsNavigator.setCircleContactsViewerFragment(this, circleData)
    }
}
