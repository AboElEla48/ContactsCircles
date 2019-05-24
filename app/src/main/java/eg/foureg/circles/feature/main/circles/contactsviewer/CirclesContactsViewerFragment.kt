package eg.foureg.circles.feature.main.circles.contactsviewer


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import eg.foureg.circles.R
import eg.foureg.circles.circles.data.CircleData
import kotlinx.android.synthetic.main.fragment_circles_contacts_viewer.view.*


/**
 * This fragment to show list of circle name and list of contacts inside this circle
 *
 */
class CirclesContactsViewerFragment : Fragment() {
    lateinit var circleData: CircleData

    var circleContactsViewModel = CirclesContactsViewerViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {bundle ->
            circleData = bundle.getParcelable(PARAM_CIRCLE_DATA)!!

            circleContactsViewModel = ViewModelProviders.of(this).get(CirclesContactsViewerViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_circles_contacts_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        circleContactsViewModel.circleName.observe(this, Observer {str ->
            view.fragment_circles_contacts_viewer_circle_name_text_view.text = str
        })

        circleContactsViewModel.initCircle(circleData)

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters
         * @return A new instance of fragment CirclesContactsViewerFragment.
         */
        @JvmStatic
        fun newInstance(circleData: CircleData) =
                CirclesContactsViewerFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(PARAM_CIRCLE_DATA, circleData)
                    }
                }

        const val PARAM_CIRCLE_DATA = "PARAM_CIRCLE_DATA"
    }
}
