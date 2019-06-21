package eg.foureg.circles.feature.activitycircles.contactsviewer

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import eg.foureg.circles.R
import eg.foureg.circles.contacts.data.ContactData
import eg.foureg.circles.feature.phone.caller.PhoneCaller
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_circle_contacts_view_contact_grid_item.view.*
import kotlin.collections.ArrayList



class CircleContactsGridAdapter constructor(val context: Context, val contacts: List<ContactData>?) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_circle_contacts_view_contact_grid_item, null)

        view.fragment_circle_contacts_view_grid_item_text_view.text = contacts!!.get(position).name

        view.tag = contacts.get(position)

        view.setOnClickListener { view ->
            val cData: ContactData = view.tag as ContactData
            callContact(cData)
        }

        return view
    }

    private fun callContact(contactData: ContactData) {
        if (contactData.phones?.size == 1) {
            PhoneCaller.callPhone(context, contactData.phones?.get(0)?.phoneNumber!!)
        } else {
            val strs = ArrayList<String>()
            Observable.fromIterable(contactData.phones)
                    .blockingSubscribe { phone ->
                        strs.add(phone.phoneNumber)
                    }

            showPhonesDialog(strs)
        }
    }

    private fun showPhonesDialog(contacts: ArrayList<String>) {

        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.txt_phone_numbers_list_dialog_title)

        val strList = contacts.toArray(arrayOfNulls<CharSequence>(contacts.size))

        builder.setItems(strList) { _, which ->
            val selected = contacts.toArray()[which] as String

            PhoneCaller.callPhone(context, selected)
        }

        builder.show()


    }


    override fun getItem(position: Int): Any {
        return contacts!!.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return contacts!!.size
    }
}