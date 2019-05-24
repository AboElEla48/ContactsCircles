package eg.foureg.circles.feature.main.circles.contactsviewer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import eg.foureg.circles.R
import eg.foureg.circles.contacts.data.ContactData

class CircleContactsGridAdapter constructor (val context: Context, val circles: List<ContactData>?) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_circle_contacts_view_contact_grid_item, null)

        return view
    }

    override fun getItem(position: Int): Any {
        return circles!!.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return circles!!.size
    }
}