package eg.foureg.circles.feature.contacts.view


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import eg.foureg.circles.R
import eg.foureg.circles.common.ui.BaseFragment
import eg.foureg.circles.contacts.ContactData
import eg.foureg.circles.feature.contacts.models.ContactsModel

/**
 * A simple [Fragment] subclass.
 * Use the [ContactViewerFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ContactViewerFragment : BaseFragment() {

    var contactViewViewModel = ContactViewViewModel()
    var contactIndex : Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contactIndex = it.getInt(CONTACT_INDEX_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_contact_viewer, container, false)

        val contactImageView : ImageView = view.findViewById(R.id.fragment_contact_viewer_image_view)
        val contactNameTextView : TextView = view.findViewById(R.id.fragment_content_view_name_text_view)
        val contactPhonesLayout : LinearLayout = view.findViewById(R.id.fragment_content_view_phones_layout)
        val contactEmailsLayout : LinearLayout = view.findViewById(R.id.fragment_content_view_emails_layout)

        contactViewViewModel = ViewModelProviders.of(this).get(ContactViewViewModel::class.java)

        contactViewViewModel.image.observe(this, Observer { img : Bitmap? ->
            if(img != null){
            contactImageView.setImageBitmap(img)}
        })

        contactViewViewModel.contactName.observe(this, Observer { name : String? ->
            contactNameTextView.setText(name) })

        contactViewViewModel.initContact(contactIndex)

        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ContactViewerFragment.
         */
        @JvmStatic
        fun newInstance(contactIndex : Int) =
                ContactViewerFragment().apply {
                    arguments = Bundle().apply {
                        putInt(CONTACT_INDEX_PARAM, contactIndex)
                    }
                }

        const val CONTACT_INDEX_PARAM = "CONTACT_INDEX_PARAM"
    }
}
