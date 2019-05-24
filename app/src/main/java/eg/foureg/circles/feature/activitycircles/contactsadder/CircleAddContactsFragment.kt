package eg.foureg.circles.feature.activitycircles.contactsadder


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import eg.foureg.circles.R


/**
 * This Fragment to add contacts to circle
 *
 */
class CircleAddContactsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_circle_add_contacts, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CircleAddContactsFragment.
         */
        @JvmStatic
        fun newInstance() =
                CircleAddContactsFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
