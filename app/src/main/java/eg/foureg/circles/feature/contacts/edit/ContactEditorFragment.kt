package eg.foureg.circles.feature.contacts.edit


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import eg.foureg.circles.R
import eg.foureg.circles.common.ui.BaseFragment


/**
 * A simple [Fragment] subclass.
 * Use the [ContactEditorFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ContactEditorFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_editor, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         */
        @JvmStatic
        fun newInstance() =
                ContactEditorFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}
