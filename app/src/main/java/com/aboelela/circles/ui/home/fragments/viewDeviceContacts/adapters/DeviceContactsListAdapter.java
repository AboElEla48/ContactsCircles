package com.aboelela.circles.ui.home.fragments.viewDeviceContacts.adapters;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboelela.circles.CirclesApplication;
import com.aboelela.circles.R;
import com.mvvm.framework.utils.ContactsUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by aboelela on 14/07/17.
 * Adapter for recycler view that displays list of device contacts
 */

public class DeviceContactsListAdapter extends RecyclerView.Adapter<DeviceContactsListAdapter.ViewHolder>
{
    public DeviceContactsListAdapter(List<ContactsUtil.ContactModel> contacts) {
        Set<ContactsUtil.ContactModel> set = new HashSet<>(contacts);
        deviceContacts = new ArrayList<>(set);

        Collections.sort(deviceContacts);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_device_contacts_list_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (selectedItems.contains(viewHolder.getAdapterPosition())) {
                        // item is selected, deselect it
                        selectedItems.remove(selectedItems.indexOf(viewHolder.getAdapterPosition()));
                    }
                    else {
                        // mark item as selected
                        selectedItems.add(viewHolder.getAdapterPosition());
                    }

                    notifyItemChanged(viewHolder.getAdapterPosition());
                }
                return true;
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setText(deviceContacts.get(position).contactName);

        // set item selected if this is the selection index
        holder.itemView.setSelected(selectedItems.contains(position));
        if (holder.itemView.isSelected()) {
            holder.itemTextView.setTextColor(ContextCompat.getColor(CirclesApplication.getInstance(),
                    R.color.colorTextSelectionItem));
        }
        else {
            holder.itemTextView.setTextColor(ContextCompat.getColor(CirclesApplication.getInstance(),
                    R.color.colorPureBlack));
        }
    }

    @Override
    public int getItemCount() {
        return deviceContacts.size();
    }


    /**
     * View Holder for device contact item
     */
    class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        ImageView itemImageView;
        TextView itemTextView;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            itemTextView = (TextView) cardView.findViewById(R.id.device_contact_list_item_textView);
            itemImageView = (ImageView) cardView.findViewById(R.id.device_contact_list_item_imageView);
        }

        void setText(String text) {
            itemTextView.setText(text);
        }

        void setImage(Drawable drawable) {
            itemImageView.setImageDrawable(drawable);
        }
    }

    private List<ContactsUtil.ContactModel> deviceContacts;
    private ArrayList<Integer> selectedItems = new ArrayList<>();
}
