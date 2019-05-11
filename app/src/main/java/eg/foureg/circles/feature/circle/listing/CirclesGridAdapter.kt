package eg.foureg.circles.feature.circle.listing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import eg.foureg.circles.R
import eg.foureg.circles.circles.data.CircleData
import kotlinx.android.synthetic.main.fragment_circles_grid_item.view.*

class CirclesGridAdapter constructor (val context: Context, val circles: List<CircleData>?) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_circles_grid_item, null)

        val circle = circles!!.get(position)

        view.fragment_circles_grid_item_text_view.text = circle.name

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