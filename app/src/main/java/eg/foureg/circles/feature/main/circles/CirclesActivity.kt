package eg.foureg.circles.feature.main.circles

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import eg.foureg.circles.R
import eg.foureg.circles.circles.data.CircleData

class CirclesActivity : AppCompatActivity() {

    val fragmentsNavigator : CirclesActivityFragmentsNavigator = CirclesActivityFragmentsNavigator()
    lateinit var circleData: CircleData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circles)

        circleData = intent.extras!!.getParcelable(CirclesActivityMessages.DATA_PARAM_CIRCLE_DATA)!!

        fragmentsNavigator.setCircleContactsViewerFragment(this, circleData)
    }
}
