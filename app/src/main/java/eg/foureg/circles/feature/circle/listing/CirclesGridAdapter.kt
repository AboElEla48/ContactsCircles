package eg.foureg.circles.feature.circle.listing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.jakewharton.rxbinding2.view.RxView
import eg.foureg.circles.R
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.common.message.data.Message
import eg.foureg.circles.common.message.server.MessageServer
import eg.foureg.circles.feature.main.MainActivity
import eg.foureg.circles.feature.main.MainActivityMessages
import kotlinx.android.synthetic.main.fragment_circles_grid_item.view.*

class CirclesGridAdapter constructor (val context: Context, val circles: List<CircleData>?) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_circles_grid_item, null)

        val circle = circles!!.get(position)

        view.fragment_circles_grid_item_text_view.text = circle.name

        view.tag = circle

        RxView.clicks(view).subscribe {v ->
            val viewCircle : CircleData = view.tag as CircleData
            val msg = Message()
            msg.id = MainActivityMessages.MSG_ID_VIEW_CIRCLE_CONTACTS_LIST
            msg.data.put(MainActivityMessages.DATA_PARAM_CIRCLE_DATA, circle)

            MessageServer.getInstance().sendMessage(MainActivity::class.java, msg)
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return circles!!.get(position)
    }

    override fun getItemId(position: Int): Long {
        return circles!!.get(position).circleID.toLong()
    }

    override fun getCount(): Int {
        return circles!!.size
    }

}