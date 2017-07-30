package com.aboelela.circles.ui.home.fragments.viewCircles.adapters;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aboelela.circles.CirclesApplication;
import com.aboelela.circles.R;
import com.aboelela.circles.data.entities.Circle;
import com.aboelela.circles.ui.home.HomeActivityMessagesHelper;

import java.util.List;

/**
 * Created by AboelelaA on 7/13/2017.
 * Adapter for circles grid
 */

public class CirclesGridAdapter extends BaseAdapter
{
    public CirclesGridAdapter(List<Circle> circles) {
        this.circles = circles;
    }

    @Override
    public int getCount() {
        return circles.size();
    }

    @Override
    public Object getItem(int position) {
        return circles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return circles.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(CirclesApplication.getInstance()).inflate(R.layout.grid_circles_grid_item, null);

            viewHolder = new ViewHolder();
            viewHolder.itemTextView = (TextView)convertView.findViewById(R.id.circle_grid_item_text_view);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.itemTextView.setText(circles.get(position).getName());
        viewHolder.itemTextView.setTag(position);

        viewHolder.itemTextView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View textView, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    // Show the contacts of this circle
                    HomeActivityMessagesHelper.sendMessageOpenCircleContacts(circles.get((int)textView.getTag()));
                }
                return true;
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView itemTextView;
    }

    private List<Circle> circles;

}
