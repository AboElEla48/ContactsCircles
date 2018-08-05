package eg.foureg.circles.ui.contacts.viewer

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import eg.foureg.circles.R
import eg.foureg.circles.contacts.ContactData

class ContactsListAdapter(val context: Context, val contacts : List<ContactData>) : RecyclerView.Adapter<ContactsListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_contacts_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView?.text = contacts.get(position).name
    }

    inner class ViewHolder (view:View): RecyclerView.ViewHolder(view) {
        var nameTextView : TextView? = null
    }
}