package eg.foureg.circles.feature.contacts.edit


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import eg.foureg.circles.R
import eg.foureg.circles.common.ui.BaseFragment


/**
 * A simple [Fragment] subclass.
 * Use the [ContactEditorFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ContactEditorFragment : BaseFragment() {

    var contactIndex: Int? = 0
    var contactEditorViewModel = ContactEditorViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contactIndex = it.getInt(ContactEditorFragment.CONTACT_INDEX_PARAM)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_contact_editor, container, false)

        val contactImageView: ImageView = view.findViewById(R.id.fragment_contact_editor_image_view)
        val contactNameEditText: EditText = view.findViewById(R.id.fragment_content_editor_name_edit_view)
        val contactPhonesLayout: LinearLayout = view.findViewById(R.id.fragment_content_editor_phones_layout)
        val contactEmailsLayout: LinearLayout = view.findViewById(R.id.fragment_content_editor_emails_layout)

        contactEditorViewModel = ViewModelProviders.of(this).get(ContactEditorViewModel::class.java)

        contactEditorViewModel.image.observe(this, Observer { img: Bitmap? ->
            if (img != null) {
                contactImageView.setImageBitmap(img)
            }
        })

        contactEditorViewModel.contactName.observe(this, Observer { name: String? ->
            contactNameEditText.setText(name)
        })

        contactEditorViewModel.initContact(contactIndex)

        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         */
        @JvmStatic
        fun newInstance(contactIndex: Int) =
                ContactEditorFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ContactEditorFragment.CONTACT_INDEX_PARAM, contactIndex)

                    }
                }

        const val CONTACT_INDEX_PARAM = "CONTACT_INDEX_PARAM"
    }

}
