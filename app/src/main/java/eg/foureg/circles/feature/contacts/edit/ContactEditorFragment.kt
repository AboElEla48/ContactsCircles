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
import eg.foureg.circles.contacts.ContactData
import eg.foureg.circles.contacts.ContactPhoneNumber
import eg.foureg.circles.feature.contacts.models.ContactsModel
import eg.foureg.circles.feature.main.MainActivity
import eg.foureg.circles.feature.main.MainActivityMessages
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.inject
import kotlin.reflect.KClass


/**
 * Fragment for editing contact
 *
 */
class ContactEditorFragment : BaseFragment() {

    private var contactIndex: Int? = 0
    private var contactEditorViewModel = ContactEditorViewModel()
    private var phoneEditorsViewsList: ArrayList<EditText> = ArrayList()
    private var phoneEditorTypesSpinnerViewsList: ArrayList<Spinner> = ArrayList()
    private var emailEditorsViewsList: ArrayList<EditText> = ArrayList()

    private lateinit var progressBar: ProgressBar

    private var listOfDisposables: ArrayList<Disposable> = ArrayList()

    val contactsModel : ContactsModel by inject()

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
        val addPhoneNumber: ImageView = view.findViewById(R.id.fragment_content_editor_phones_layout_add_phone_btn)

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
        contactEditorViewModel.phones.observe(this, Observer { phonesList: List<ContactPhoneNumber>? ->
            bindPhonesLayout(container, contactPhonesLayout, inflater, phonesList)
        })
        contactEditorViewModel.emails.observe(this, Observer { emailsList: List<String>? ->
            bindEmailsLayout(container, contactEmailsLayout, inflater, emailsList)
        })

        // handle save contact button
        listOfDisposables.add(RxView.clicks(saveBtn)
                .subscribe {
                    saveContact(contactNameEditText)
                })

        // handle add phone number to add new layout to phone layout
        listOfDisposables.add(RxView.clicks(addPhoneNumber)
                .subscribe {
                    addPhoneView(inflater, container, contactPhonesLayout, null)

//                    val phoneEditView: View = inflater.inflate(R.layout.view_phone_number_editor, container, false)
//                    val phoneEditText: EditText = phoneEditView.findViewById(R.id.view_phone_number_editor_edit_text)
//                    val phoneTypeSpinner: Spinner = phoneEditView.findViewById(R.id.view_phone_number_editor_type_spinner)
//
//                    phoneEditorsViewsList.add(phoneEditText)
//                    phoneEditorTypesSpinnerViewsList.add(phoneTypeSpinner)
//
//                    contactPhonesLayout.addView(phoneEditView)
                })

        // init edit contact if this isn't a new contact
        contactEditorViewModel.initContact(activity as Context, contactIndex)

        if(contactIndex == -1) {
            addPhoneView(inflater, container, contactPhonesLayout, null)
        }

        return view
    }

    private fun addPhoneView(inflater: LayoutInflater, container: ViewGroup?, contactPhonesLayout: LinearLayout,
                             phone: ContactPhoneNumber?) {
        val phoneEditView: View = inflater.inflate(R.layout.view_phone_number_editor, container, false)
        val phoneEditText: EditText = phoneEditView.findViewById(R.id.view_phone_number_editor_edit_text)
        val phoneTypeSpinner: Spinner = phoneEditView.findViewById(R.id.view_phone_number_editor_type_spinner)

        phoneEditText.setText(phone?.phoneNumber)

        phoneEditorsViewsList.add(phoneEditText)
        phoneEditorTypesSpinnerViewsList.add(phoneTypeSpinner)

        contactPhonesLayout.addView(phoneEditView)

    }

    /**
     * Dynamically add phones edit items in phones layout
     */
    private fun bindPhonesLayout(container: ViewGroup?, contactPhonesLayout: LinearLayout, inflater: LayoutInflater,
                                 phonesList: List<ContactPhoneNumber>?) {
        listOfDisposables.add(Observable.fromIterable(phonesList)
                .subscribe { phone: ContactPhoneNumber ->
                    addPhoneView(inflater, container, contactPhonesLayout, phone)
                })
    }

    /**
     * Dynamically add emails edit items in emails layout
     */
    private fun bindEmailsLayout(container: ViewGroup?, contactEmailsLayout: LinearLayout, inflater: LayoutInflater, emailsList: List<String>?) {
        listOfDisposables.add(Observable.fromIterable(emailsList)
                .subscribe { email: String ->
                    val emailEditView: View = inflater.inflate(R.layout.fragment_contact_editor_email_item, container, false)
                    val emailEditText: EditText = emailEditView.findViewById(R.id.fragment_content_editor_item_email_edit_view)

                    emailEditText.setText(email)

                    emailEditorsViewsList.add(emailEditText)

                    contactEmailsLayout.addView(emailEditView)
                })

    }

    private fun saveContact(contactNameEditText: EditText) {
        // get contact name
        contactEditorViewModel.contactName.value = contactNameEditText.text.toString()

        // get contact phones
        contactEditorViewModel.phones.value = ArrayList()
        listOfDisposables.add(Observable.fromIterable(phoneEditorsViewsList)
                .subscribe{ view ->
                    contactEditorViewModel.phones.value!!.add(ContactPhoneNumber(view.text.toString(),
                            ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_MOBILE))
                })

        var index = -1
        listOfDisposables.add(Observable.fromIterable(phoneEditorTypesSpinnerViewsList)
                .subscribe { view ->
                    index ++
                    when (view.selectedItemPosition) {
                        0 -> contactEditorViewModel.phones.value!!.get(index).phoneNumberType =
                                ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_MOBILE
                        1 -> contactEditorViewModel.phones.value!!.get(index).phoneNumberType =
                                ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_HOME
                        2 -> contactEditorViewModel.phones.value!!.get(index).phoneNumberType =
                                ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_WORK
                        else -> {
                            contactEditorViewModel.phones.value!!.get(index).phoneNumberType =
                                ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_MOBILE
                        }
                    }
                })



//        // get contact emails
//        contactEditorViewModel.emails.value?.clear()
//        listOfDisposables.add(Observable.fromIterable(emailEditorsViewsList)
//                .subscribe{ editText: EditText ->
//                    contactEditorViewModel.emails.value?.add(editText.text.toString())
//                })

        progressBar.visibility = View.VISIBLE

        // save contact
        if(contactIndex == -1) {

            // New contact
            val contactData = ContactData()
            contactData.name = contactEditorViewModel.contactName.value.toString()
            contactData.phones = contactEditorViewModel.phones.value
            contactEditorViewModel.saveContact(activity as Context, contactData)
        }
        else {
            // existing contact
//            contactEditorViewModel.updateContact(activity as Context, contactIndex)
//                    .subscribeOn(Schedulers.computation())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe()
        }
        progressBar.visibility = View.GONE

        val msg = Message()
        msg.id = MainActivityMessages.MSG_ID_VIEW_CONTACTS_List
        MessageServer.getInstance().sendMessage(MainActivity::class as KClass<Any>, msg)
    }

    override fun onDestroy() {
        super.onDestroy()

        for (itemDisposable in listOfDisposables) {
            itemDisposable.dispose()
        }
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
