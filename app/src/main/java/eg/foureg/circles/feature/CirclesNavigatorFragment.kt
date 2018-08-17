package eg.foureg.circles.feature


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eg.foureg.circles.R
import eg.foureg.circles.common.ui.BaseFragment


/**
 * This fragment to display the contact circles into navigator
 *
 */
class CirclesNavigatorFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_circles_navigator, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CirclesNavigatorFragment.
         */
        @JvmStatic
        fun newInstance() =
                CirclesNavigatorFragment().apply {
                    arguments = Bundle().apply {}
                }
    }
}
