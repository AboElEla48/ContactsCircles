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
import eg.foureg.circles.contacts.data.ContactData
import eg.foureg.circles.contacts.data.ContactPhoneNumber
import eg.foureg.circles.feature.contacts.models.ContactsModel
import eg.foureg.circles.feature.main.content.ContentActivity
import eg.foureg.circles.feature.main.content.ContentActivityMessages
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_contact_editor.*
import kotlinx.android.synthetic.main.fragment_contact_editor.view.*
import kotlinx.android.synthetic.main.view_email_editor.view.*
import kotlinx.android.synthetic.main.view_phone_number_editor.view.*
import org.koin.android.ext.android.inject
import kotlin.reflect.KClass


/**
 * Fragment for editing editContact
 *
 */
class ContactEditorFragment : BaseFragment() {

    private var contactEditorViewModel = ContactEditorViewModel()
    private var phoneEditorsViewsList: ArrayList<EditText> = ArrayList()
    private var phoneEditorTypesSpinnerViewsList: ArrayList<Spinner> = ArrayList()
    private var emailEditorsViewsList: ArrayList<EditText> = ArrayList()

    private var editContact: ContactData? = null

    private var listOfDisposables: ArrayList<Disposable> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            editContact = it.getParcelable<ContactData>(CONTACT_DATA_PARAM)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_contact_editor, container, false)

        contactEditorViewModel = ViewModelProviders.of(this).get(ContactEditorViewModel::class.java)
        contactEditorViewModel.image.observe(this, Observer { img: Bitmap? ->
            if (img != null) {
                view.fragment_contact_editor_image_view.setImageBitmap(img)
            }
        })
        contactEditorViewModel.contactName.observe(this, Observer { name: String? ->
            view.fragment_content_editor_name_edit_view.setText(name)
        })
        contactEditorViewModel.phones.observe(this, Observer { phonesList: List<ContactPhoneNumber>? ->
            bindPhonesLayout(container, view.fragment_content_editor_phones_layout, inflater, phonesList)
        })
        contactEditorViewModel.emails.observe(this, Observer { emailsList: List<String>? ->
            bindEmailsLayout(container, view.fragment_content_editor_emails_layout, inflater, emailsList)
        })

        // handle save editContact button
        listOfDisposables.add(RxView.clicks(view.fragment_content_editor_save_button)
                .subscribe {
                    saveContact(view.fragment_content_editor_name_edit_view)
                })

        // handle add phone number to add new layout to phone layout
        listOfDisposables.add(RxView.clicks(view.fragment_content_editor_phones_layout_add_phone_btn)
                .subscribe {
                    addPhoneView(inflater, container, view.fragment_content_editor_phones_layout, null)
                })

        // handle add emailto add new layout to emails layout
        listOfDisposables.add(RxView.clicks(view.fragment_content_editor_emails_layout_add_email_btn)
                .subscribe {
                    addEmailView(inflater, container, view.fragment_content_editor_emails_layout, "")
                })

        if (editContact == null) {
            // add empt phone and email fields for editing
            addPhoneView(inflater, container, view.fragment_content_editor_phones_layout, null)
            addEmailView(inflater, container, view.fragment_content_editor_emails_layout, "")
        } else {
            // init edit editContact if this isn't a new editContact
            contactEditorViewModel.initContact(activity as Context, editContact)
        }

        return view
    }

    /**
     * Add phone view with edit text and phone type spinner to define new phone
     */
    private fun addPhoneView(inflater: LayoutInflater, container: ViewGroup?, contactPhonesLayout: LinearLayout,
                             phone: ContactPhoneNumber?) {
        val phoneEditView: View = inflater.inflate(R.layout.view_phone_number_editor, container, false)

        phoneEditView.view_phone_number_editor_edit_text.setText(phone?.phoneNumber)

        when (phone?.phoneNumberType) {
            ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_MOBILE ->
                phoneEditView.view_phone_number_editor_type_spinner.setSelection(SPINNER_TYPE_MOBILE)

            ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_HOME ->
                phoneEditView.view_phone_number_editor_type_spinner.setSelection(SPINNER_TYPE_HOME)

            ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_WORK ->
                phoneEditView.view_phone_number_editor_type_spinner.setSelection(SPINNER_TYPE_WORK)

        }

        phoneEditorsViewsList.add(phoneEditView.view_phone_number_editor_edit_text)
        phoneEditorTypesSpinnerViewsList.add(phoneEditView.view_phone_number_editor_type_spinner)

        phoneEditView.view_phone_number_editor_delete_detail_image_view.setOnClickListener {
            contactPhonesLayout.removeView(phoneEditView)
            phoneEditorsViewsList.remove(phoneEditView.view_phone_number_editor_edit_text)
            phoneEditorTypesSpinnerViewsList.remove(phoneEditView.view_phone_number_editor_type_spinner)
        }

        contactPhonesLayout.addView(phoneEditView)

    }

    /**
     * Dynamically add phones edit items in phones layout
     */
    private fun bindPhonesLayout(container: ViewGroup?, contactPhonesLayout: LinearLayout, inflater: LayoutInflater,
                                 phonesList: List<ContactPhoneNumber>?) {
        listOfDisposables.add(Observable.fromIterable(phonesList)
                .subscribe { phone: ContactPhoneNumber ->
                    if (phone.phoneNumber.isNotEmpty()) {
                        addPhoneView(inflater, container, contactPhonesLayout, phone)
                    }
                })
    }

    /**
     * Add view with edit text for email
     */
    private fun addEmailView(inflater: LayoutInflater, container: ViewGroup?, contactEmailsLayout: LinearLayout,
                             email: String) {
        val emailEditView: View = inflater.inflate(R.layout.view_email_editor, container, false)

        emailEditView.view_email_editor_email_edit_view.setText(email)

        emailEditorsViewsList.add(emailEditView.view_email_editor_email_edit_view)

        emailEditView.view_email_editor_delete_detail_image_view.setOnClickListener {
            contactEmailsLayout.removeView(emailEditView)
            emailEditorsViewsList.remove(emailEditView.view_email_editor_email_edit_view)
        }

        contactEmailsLayout.addView(emailEditView)
    }

    /**
     * Dynamically add emails edit items in emails layout
     */
    private fun bindEmailsLayout(container: ViewGroup?, contactEmailsLayout: LinearLayout, inflater: LayoutInflater, emailsList: List<String>?) {
        listOfDisposables.add(Observable.fromIterable(emailsList)
                .subscribe { email: String ->
                    addEmailView(inflater, container, contactEmailsLayout, email)
                })

    }

    private fun saveContact(contactNameEditText: EditText) {
        // get editContact name
        contactEditorViewModel.contactName.value = contactNameEditText.text.toString()

        collectContactPhoneNumbers()

        collectContactEmails()

        fragment_contact_editor_loading_progress.visibility = View.VISIBLE

        // save contact
        val contactData = ContactData()
        contactData.name = contactEditorViewModel.contactName.value.toString()
        contactData.phones = contactEditorViewModel.phones.value
        contactData.emails = contactEditorViewModel.emails.value

        if (editContact != null) {
            listOfDisposables.add(contactEditorViewModel.updateContact(activity as Context, editContact?.phones, contactData)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe { contactSavedAndExit() })

        } else {
            listOfDisposables.add(contactEditorViewModel.saveContact(activity as Context, contactData)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe { contactSavedAndExit() })
        }




    }

    private fun contactSavedAndExit() {
        fragment_contact_editor_loading_progress.visibility = View.GONE

        val msg = Message()
        msg.id = ContentActivityMessages.MSG_ID_CLOSE_CONTENT_ACTIVITY_AND_REFRESH_CONTACTS
        MessageServer.getInstance().sendMessage(ContentActivity::class.java, msg)
    }


    private fun collectContactPhoneNumbers() {
        // get editContact phones
        contactEditorViewModel.phones.value = ArrayList()
        listOfDisposables.add(Observable.fromIterable(phoneEditorsViewsList)
                .subscribe { view ->
                    contactEditorViewModel.phones.value!!.add(ContactPhoneNumber("-1", view.text.toString(),
                            ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_MOBILE))
                })

        var index = -1
        listOfDisposables.add(Observable.fromIterable(phoneEditorTypesSpinnerViewsList)
                .subscribe { view ->
                    index++
                    when (view.selectedItemPosition) {
                        SPINNER_TYPE_MOBILE -> contactEditorViewModel.phones.value!!.get(index).phoneNumberType =
                                ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_MOBILE
                        SPINNER_TYPE_HOME -> contactEditorViewModel.phones.value!!.get(index).phoneNumberType =
                                ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_HOME
                        SPINNER_TYPE_WORK -> contactEditorViewModel.phones.value!!.get(index).phoneNumberType =
                                ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_WORK
                        else -> {
                            contactEditorViewModel.phones.value!!.get(index).phoneNumberType =
                                    ContactPhoneNumber.PHONE_NUM_TYPE.PHONE_NUM_TYPE_MOBILE
                        }
                    }
                })
    }

    private fun collectContactEmails() {
        // get editContact emails
        contactEditorViewModel.emails.value = ArrayList()
        listOfDisposables.add(Observable.fromIterable(emailEditorsViewsList)
                .subscribe { editText: EditText ->
                    contactEditorViewModel.emails.value?.add(editText.text.toString())
                })

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
        fun newInstance(contact: ContactData?) =
                ContactEditorFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(CONTACT_DATA_PARAM, contact)

                    }
                }

        const val CONTACT_DATA_PARAM = "CONTACT_DATA_PARAM"

        const val SPINNER_TYPE_MOBILE = 0
        const val SPINNER_TYPE_HOME = 1
        const val SPINNER_TYPE_WORK = 2
    }

}
