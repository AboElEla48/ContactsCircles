package eg.foureg.circles.feature.contacts.listing

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eg.foureg.circles.R
import eg.foureg.circles.common.message.data.Message
import eg.foureg.circles.common.message.server.MessageServer
import eg.foureg.circles.contacts.data.ContactData
import eg.foureg.circles.feature.main.MainActivity
import eg.foureg.circles.feature.main.MainActivityMessages
import kotlinx.android.synthetic.main.fragment_contacts_list_item.view.*
import kotlin.reflect.KClass

/**
 * Define Adapter for contacts list
 */
class ContactsListAdapter(val context: Context, val contacts: List<ContactData>?) : RecyclerView.Adapter<ViewHolder>() {
    override fun getItemCount(): Int {
        return contacts!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_contacts_list_item, parent, false))

        viewHolder.layout.setOnClickListener{ view: View? ->
            val itemIndex = view?.tag as Int

            val msg = Message()
            msg.id = MainActivityMessages.MSG_ID_VIEW_CONTACT_DETAILS
            msg.data.put(MainActivityMessages.DATA_PARAM_CONTACT_DATA, contacts!!.get(itemIndex))
            MessageServer.getInstance().sendMessage(MainActivity::class as KClass<Any>, msg)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.layout.tag = position
        if (contacts?.get(position)?.image != null) {
            holder.contactIageView.setImageBitmap(contacts.get(position).image)
        }
        holder.nameTextView.text = contacts?.get(position)?.name


    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var layout = view.contacts_list_item_layout
    var nameTextView = view.contacts_list_item_name_text_view
    var contactIageView = view.contacts_list_item_image_view
}