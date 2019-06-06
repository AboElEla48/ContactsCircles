package eg.foureg.circles.feature.activitycircles.contactsadder

import android.content.Context
import android.widget.ArrayAdapter
import eg.foureg.circles.circles.data.CircleData

class CircleAddContactsListAdapter constructor(context: Context, contacts: List<String>) :
        ArrayAdapter<String>(context, android.R.layout.simple_list_item_multiple_choice, contacts) {
    val contactsNames = contacts

}