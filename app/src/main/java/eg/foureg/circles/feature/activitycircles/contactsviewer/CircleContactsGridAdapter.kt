package eg.foureg.circles.feature.activitycircles.contactsviewer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import eg.foureg.circles.R
import eg.foureg.circles.contacts.data.ContactData
import kotlinx.android.synthetic.main.fragment_circle_contacts_view_contact_grid_item.view.*

class CircleContactsGridAdapter constructor (val context: Context, val contacts: List<ContactData>?) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_circle_contacts_view_contact_grid_item, null)

        view.fragment_circle_contacts_view_grid_item_text_view.text = contacts!!.get(position).name

        return view
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