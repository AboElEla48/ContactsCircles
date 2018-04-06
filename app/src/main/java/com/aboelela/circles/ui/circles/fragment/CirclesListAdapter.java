package com.aboelela.circles.ui.circles.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aboelela.circles.R;
import com.aboelela.circles.model.data.Circle;

import java.util.List;

/**
 * Created by aboelela on 06/04/18.
 * Adapter for filling the list of circles
 */

public class CirclesListAdapter extends BaseAdapter
{
    public CirclesListAdapter(Context context, List<Circle> circles) {
        this.circles = circles;
        this.context = context;
    }

    @Override
    public int getCount() {
        return circles.size();
    }

    @Override
    public Object getItem(int i) {
        return circles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return circles.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_circle_item, null);
            setItemViewData(view, circles.get(position));
        }
        else {
            view = convertView;
        }
        return view;
    }

    /**
     * fill circle data into view
     * @param view : circle grid item view
     * @param circle : circle data
     */
    private void setItemViewData(View view, Circle circle) {
        TextView circleNameTextView = view.findViewById(R.id.grid_circle_item_text_view);
        circleNameTextView.setText(circle.getName());
    }

    private List<Circle> circles;
    private Context context;
}
