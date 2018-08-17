package eg.foureg.circles.feature.contacts.viewer

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eg.foureg.circles.R
import eg.foureg.circles.contacts.ContactData
import kotlinx.android.synthetic.main.fragment_contacts_list_item.view.*

/**
 * Define Adapter for contacts list
 */
class ContactsListAdapter(val context: Context, val contacts: List<ContactData>?) : RecyclerView.Adapter<ViewHolder>() {
    override fun getItemCount(): Int {
        return contacts!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_contacts_list_item, parent, false))
        viewHolder.itemView.setTag(position)

        viewHolder.itemView.setOnClickListener({ view: View? ->
            val itemIndex = view?.tag as Int

        })

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.text = contacts?.get(position)?.name

        if (contacts?.get(position)?.image != null) {
            holder.contactIageView.setImageBitmap(contacts.get(position).image)
        }
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var nameTextView = view.contacts_list_item_name_text_view
    var contactIageView = view.contacts_list_item_image_view
}