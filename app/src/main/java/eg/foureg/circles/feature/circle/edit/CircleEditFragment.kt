package eg.foureg.circles.feature.circle.edit


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import eg.foureg.circles.R
import eg.foureg.circles.circles.data.CircleData

/**
 * A fragment to add/edit circle
 *
 */
class CircleEditFragment : Fragment() {

    var editCircle : CircleData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            editCircle = it.getParcelable<CircleData>(CircleEditFragment.CIRCLE_DATA_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_circle_edit, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CircleEditFragment.
         */
        @JvmStatic
        fun newInstance(circleData: CircleData?) =
                CircleEditFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(CircleEditFragment.CIRCLE_DATA_PARAM, circleData)
                    }
                }

        const val CIRCLE_DATA_PARAM = "CIRCLE_DATA_PARAM"
    }
}
