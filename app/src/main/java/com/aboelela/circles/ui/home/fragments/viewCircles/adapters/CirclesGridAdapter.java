package com.aboelela.circles.ui.home.fragments.viewCircles.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aboelela.circles.CirclesApplication;
import com.aboelela.circles.R;
import com.aboelela.circles.data.CirclesModel;

/**
 * Created by AboelelaA on 7/13/2017.
 * Adapter for circles grid
 */

public class CirclesGridAdapter extends BaseAdapter
{
    private CirclesModel circlesModel;

    public CirclesGridAdapter(CirclesModel model) {
        circlesModel = model;
    }

    @Override
    public int getCount() {
        return circlesModel.getCircles().size();
    }

    @Override
    public Object getItem(int position) {
        return circlesModel.getCircles();
    }

    @Override
    public long getItemId(int position) {
        return circlesModel.getCircles().get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(CirclesApplication.getInstance()).inflate(R.layout.fragment_circles_grid_item, null);

            viewHolder = new ViewHolder();
            viewHolder.itemTextView = (TextView)convertView.findViewById(R.id.circle_grid_item_text_view);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.itemTextView.setText(circlesModel.getCircles().get(position).getName());

        return convertView;
    }

    private class ViewHolder {
        TextView itemTextView;
    }

}
