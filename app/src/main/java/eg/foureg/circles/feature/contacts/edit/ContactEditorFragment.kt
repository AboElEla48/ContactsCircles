package eg.foureg.circles.feature.contacts.edit


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.jakewharton.rxbinding2.view.RxView
import eg.foureg.circles.R
import eg.foureg.circles.common.message.data.Message
import eg.foureg.circles.common.message.server.MessageServer
import eg.foureg.circles.common.ui.BaseFragment
import eg.foureg.circles.feature.main.MainActivity
import eg.foureg.circles.feature.main.MainActivityMessages
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.reflect.KClass


/**
 * Fragment for editing contact
 *
 */
class ContactEditorFragment : BaseFragment() {

    private var contactIndex: Int? = 0
    private var contactEditorViewModel = ContactEditorViewModel()
    private var phoneEditorsViewsList: ArrayList<EditText> = ArrayList()
    private var emailEditorsViewsList: ArrayList<EditText> = ArrayList()

    private lateinit var progressBar: ProgressBar

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

        val saveBtn: Button = view.findViewById(R.id.fragment_content_editor_save_button)

        progressBar = view.findViewById(R.id.fragment_contact_editor_loading_progress)

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
            addPhonesLayout(container, contactPhonesLayout, inflater, phonesList)
        })

        contactEditorViewModel.emails.observe(this, Observer { emailsList: List<String>? ->
            addEmailsLayout(container, contactEmailsLayout, inflater, emailsList)
        })

        RxView.clicks(saveBtn)
                .subscribe{
                    saveContact(contactNameEditText)
                }

        contactEditorViewModel.initContact(contactIndex)

        return view
    }

    /**
     * Dynamically add phones edit items in phones layout
     */
    private fun addPhonesLayout(container: ViewGroup?, contactPhonesLayout: LinearLayout, inflater: LayoutInflater, phonesList: List<String>?) {
        Observable.fromIterable(phonesList)
                .subscribe { phone: String ->
                    val phoneEditView: View = inflater.inflate(R.layout.fragment_contact_editor_phone_item, container, false)
                    val phoneEditText: EditText = phoneEditView.findViewById(R.id.fragment_content_editor_item_phone_edit_view)

                    phoneEditText.setText(phone)

                    phoneEditorsViewsList.add(phoneEditText)

                    contactPhonesLayout.addView(phoneEditView)
                }
    }

    /**
     * Dynamically add emails edit items in emails layout
     */
    private fun addEmailsLayout(container: ViewGroup?, contactEmailsLayout: LinearLayout, inflater: LayoutInflater, emailsList: List<String>?) {
        Observable.fromIterable(emailsList)
                .subscribe { email: String ->
                    val emailEditView: View = inflater.inflate(R.layout.fragment_contact_editor_email_item, container, false)
                    val emailEditText: EditText = emailEditView.findViewById(R.id.fragment_content_editor_item_email_edit_view)

                    emailEditText.setText(email)

                    emailEditorsViewsList.add(emailEditText)

                    contactEmailsLayout.addView(emailEditView)
                }

    }

    private fun saveContact(contactNameEditText: EditText) {
        // get contact name
        contactEditorViewModel.contactName.value = contactNameEditText.text.toString()

        // get contact phones
        contactEditorViewModel.phones.value?.clear()
        Observable.fromIterable(phoneEditorsViewsList)
                .subscribe { editText: EditText ->
                    contactEditorViewModel.phones.value?.add(editText.text.toString())
                }

        progressBar.visibility = View.VISIBLE

        // get contact emails
        contactEditorViewModel.emails.value?.clear()
        Observable.fromIterable(emailEditorsViewsList)
                .subscribe{ editText: EditText ->
                    contactEditorViewModel.emails.value?.add(editText.text.toString())
                }

        // save contact
        contactEditorViewModel.saveContact(activity as Context, contactIndex)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

        progressBar.visibility = View.GONE

        val msg = Message()
        msg.id = MainActivityMessages.MSG_ID_VIEW_CONTACTS_List
        MessageServer.getInstance().sendMessage(MainActivity::class as KClass<Any>, msg)
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
