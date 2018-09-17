package eg.foureg.circles.feature.contacts.edit


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.jakewharton.rxbinding2.view.RxView
import eg.foureg.circles.R
import eg.foureg.circles.common.ui.BaseFragment
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_contacts_list_item.*


/**
 * A simple [Fragment] subclass.
 * Use the [ContactEditorFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ContactEditorFragment : BaseFragment() {

    var contactIndex: Int? = 0
    var contactEditorViewModel = ContactEditorViewModel()
    var phoneEditorsViewsList : ArrayList<EditText> = ArrayList()
    var emailEditorsViewsList : ArrayList<EditText> = ArrayList()

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

        val saveBtn : Button = view.findViewById(R.id.fragment_content_editor_save_button)

        contactEditorViewModel = ViewModelProviders.of(this).get(ContactEditorViewModel::class.java)

        contactEditorViewModel.image.observe(this, Observer { img: Bitmap? ->
            if (img != null) {
                contactImageView.setImageBitmap(img)
            }
        })

        contactEditorViewModel.contactName.observe(this, Observer { name: String? ->
            contactNameEditText.setText(name)
        })

        contactEditorViewModel.phones.observe(this, Observer { phonesList: List<String>? ->
            addPhonesLayout(contactPhonesLayout, inflater, phonesList)
        })

        contactEditorViewModel.emails.observe(this, Observer { emailsList: List<String>? ->
            addEmailsLayout(contactEmailsLayout, inflater, emailsList)
        })

        RxView.clicks(saveBtn)
                .subscribe({
                    saveContact(contactNameEditText)
                })

        contactEditorViewModel.initContact(contactIndex)

        return view
    }

    /**
     * Dynamically add phones edit items in phones layout
     */
    private fun addPhonesLayout(contactPhonesLayout: LinearLayout, inflater: LayoutInflater, phonesList : List<String>?) {
        Observable.fromIterable(phonesList)
                .subscribe({ phone: String ->
                    val phoneEditView : View = inflater.inflate(R.layout.fragment_contact_editor_phone_item, null, false)
                    val phoneEditText : EditText = phoneEditView.findViewById(R.id.fragment_content_editor_item_phone_edit_view)

                    phoneEditText.setText(phone)

                    phoneEditorsViewsList.add(phoneEditText)

                    contactPhonesLayout.addView(phoneEditView)
                })
    }

    /**
     * Dynamically add emails edit items in emails layout
     */
    private fun addEmailsLayout(contactEmailsLayout: LinearLayout, inflater: LayoutInflater, emailsList : List<String>?) {
        Observable.fromIterable(emailsList)
                .subscribe({ email: String ->
                    val emailEditView : View = inflater.inflate(R.layout.fragment_contact_editor_email_item, null, false)
                    val emailEditText : EditText = emailEditView.findViewById(R.id.fragment_content_editor_item_email_edit_view)

                    emailEditText.setText(email)

                    emailEditorsViewsList.add(emailEditText)

                    contactEmailsLayout.addView(emailEditView)
                })

    }

    private fun saveContact(contactNameEditText: EditText) {
        // get contact name
        contactEditorViewModel.contactName.value = contactNameEditText.text.toString()

        // get contact phones
        contactEditorViewModel.phones.value?.clear()
        Observable.fromIterable(phoneEditorsViewsList)
                .subscribe( { editText : EditText ->
                    contactEditorViewModel.phones.value?.add(editText.text.toString())
                })

        // get contact emails
        contactEditorViewModel.emails.value?.clear()
        Observable.fromIterable(emailEditorsViewsList)
                .subscribe( { editText : EditText ->
                    contactEditorViewModel.emails.value?.add(editText.text.toString())
                })

        contactEditorViewModel.saveContact()
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
