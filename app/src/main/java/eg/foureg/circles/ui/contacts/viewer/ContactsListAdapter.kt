package eg.foureg.circles.ui.contacts.viewer

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import eg.foureg.circles.R
import eg.foureg.circles.contacts.ContactData
import kotlinx.android.synthetic.main.fragment_contacts_list_item.view.*


class ContactsListAdapter(val context: Context, val contacts : List<ContactData>) : RecyclerView.Adapter<ViewHolder>() {
    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_contacts_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.text = contacts.get(position).name
    }

}

class ViewHolder (view:View): RecyclerView.ViewHolder(view) {
    var nameTextView = view.contacts_list_item_name_text_view
}