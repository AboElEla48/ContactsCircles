package eg.foureg.circles.feature.contacts.view


import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import eg.foureg.circles.R
import eg.foureg.circles.common.message.data.Message
import eg.foureg.circles.common.message.server.MessageServer
import eg.foureg.circles.common.ui.BaseFragment
import eg.foureg.circles.contacts.ContactData
import eg.foureg.circles.contacts.ContactPhoneNumber
import eg.foureg.circles.feature.main.MainActivity
import eg.foureg.circles.feature.main.MainActivityMessages
import io.reactivex.Observable
import kotlin.reflect.KClass

/**
 * Contact Viewer fragment
 *
 */
class ContactViewerFragment : BaseFragment() {

    var contactViewViewModel = ContactViewViewModel()
    lateinit var contact:ContactData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contact = it.getParcelable(CONTACT_DATA_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_contact_viewer, container, false)

        val contactImageView: ImageView = view.findViewById(R.id.fragment_contact_viewer_image_view)
        val contactNameTextView: TextView = view.findViewById(R.id.fragment_content_view_name_text_view)
        val contactPhonesLayout: LinearLayout = view.findViewById(R.id.fragment_content_view_phones_layout)
        val contactEmailsLayout: LinearLayout = view.findViewById(R.id.fragment_content_view_emails_layout)

        contactViewViewModel = ViewModelProviders.of(this).get(ContactViewViewModel::class.java)

        contactViewViewModel.image.observe(this, Observer { img: Bitmap? ->
            if (img != null) {
                contactImageView.setImageBitmap(img)
            }
        })

        contactViewViewModel.contactName.observe(this, Observer { name: String? ->
            contactNameTextView.setText(name)
        })

        contactViewViewModel.phones.observe(this, Observer { phones: List<ContactPhoneNumber>? ->
            Observable.fromIterable(phones)
                    .subscribe{ phoneNumber: ContactPhoneNumber ->
                        val phoneView: View = inflater.inflate(R.layout.view_contact_view_phone_item, null, false)
                        val phoneTextView: TextView = phoneView.findViewById(R.id.fragment_contact_view_phone_item_phone_text_view)
                        val phoneTypeTextView: TextView = phoneView.findViewById(R.id.fragment_contact_view_phone_item_phone_type_text_view)

                        phoneTextView.text = phoneNumber.phoneNumber
                        if(phoneNumber.phoneNumber.isNotEmpty()) {
                            phoneTypeTextView.text = resources.getStringArray(R.array.txt_phone_types_arr).get(phoneNumber.phoneNumberType.ordinal)
                            contactPhonesLayout.addView(phoneView)
                        }

                    }
        })

        contactViewViewModel.emails.observe(this, Observer { emails: List<String>? ->
            Observable.fromIterable(emails)
                    .subscribe{ email: String ->
                        val emailView: View = inflater.inflate(R.layout.view_contact_view_email_item, null, false)
                        val emailTextView: TextView = emailView.findViewById(R.id.fragment_contact_view_phone_item_email_text_view)
                        emailTextView.text = email
                        contactEmailsLayout.addView(emailView)
                    }
        })

        contactViewViewModel.initContact(activity as Context, contact)

        setHasOptionsMenu(true)

        return view
    }

    /**
     * Inflate options menu
     */
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.fragment_contact_viewer_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item_contact_viewer_edit -> {
                val msg = Message()

                msg.id = MainActivityMessages.MSG_ID_EDIT_CONTACT_DETAILS
                msg.data.put(MainActivityMessages.DATA_PARAM_CONTACT_DATA, contact)
                MessageServer.getInstance().sendMessage(MainActivity::class as KClass<Any>, msg)
            }

            R.id.menu_item_contact_viewer_delete -> {
                deleteContact()
            }
        }
        return super.onOptionsItemSelected(item)

    }

    /**
     * Delete current displayed contact
     */
    private fun deleteContact(){

        // Initialize a new instance of
        val builder = AlertDialog.Builder(context)

        // Set the alert dialog title
        builder.setTitle(getString(R.string.txt_confirm_delete_contact_title))

        // Display a message on alert dialog
        builder.setMessage(getString(R.string.txt_confirm_delete_contact_msg))

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton(getString(R.string.txt_confirm_delete_ok_btn)){ _, _ ->
            contactViewViewModel.deleteContact(context!!).subscribe{
                val msg = Message()
                msg.id = MainActivityMessages.MSG_ID_VIEW_CONTACTS_List
                MessageServer.getInstance().sendMessage(MainActivity::class as KClass<Any>, msg)
            }
        }


        // Display a negative button on alert dialog
        builder.setNegativeButton(getString(R.string.txt_confirm_delete_no_btn)){ _, _ ->
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ContactViewerFragment.
         */
        @JvmStatic
        fun newInstance(contact : ContactData) =
                ContactViewerFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(CONTACT_DATA_PARAM, contact)
                    }
                }

        const val CONTACT_DATA_PARAM = "CONTACT_DATA_PARAM"
    }
}
