package com.aboelela.circles.ui.home.fragments.viewCircleContacts.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aboelela.circles.CirclesApplication;
import com.aboelela.circles.R;
import com.mvvm.framework.utils.ContactsUtil;

import java.util.List;

/**
 * Created by aboelela on 23/07/17.
 * Adapter for grid displaying circle contacts
 */

public class CircleContactsGridAdapter extends BaseAdapter
{
    public CircleContactsGridAdapter(@NonNull List<ContactsUtil.ContactModel> contacts) {
        circleContacts = contacts;
    }

    private @NonNull List<ContactsUtil.ContactModel> circleContacts;

    @Override
    public int getCount() {
        return circleContacts.size();
    }

    @Override
    public Object getItem(int position) {
        return circleContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(CirclesApplication.getInstance()).inflate(R.layout.grid_circle_contacts_grid_item, null);

            viewHolder = new ViewHolder();
            viewHolder.itemTextView = (TextView)convertView.findViewById(R.id.circle_contact_grid_item_text_view);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.itemTextView.setText(circleContacts.get(position).getContactName());
        viewHolder.itemTextView.setTag(position);

        viewHolder.itemTextView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View textView, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    // TODO: Show contact details

                }
                return true;
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView itemTextView;
    }
}
