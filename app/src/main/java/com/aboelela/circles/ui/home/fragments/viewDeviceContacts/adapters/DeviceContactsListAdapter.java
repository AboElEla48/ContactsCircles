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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by aboelela on 14/07/17.
 * Adapter for recycler view that displays list of device contacts names
 */

public class DeviceContactsListAdapter extends RecyclerView.Adapter<DeviceContactsListAdapter.ViewHolder>
{
    public DeviceContactsListAdapter(List<String> contacts, boolean isSelectable) {

        deviceContacts = contacts;
        this.isSelectable = isSelectable;
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
                    if (isSelectable) {
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
                    else {
                        // TODO: open contact
                    }
                }
                return true;
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setText(deviceContacts.get(position));

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
     * get selected contacts
     *
     * @param receiver : receiver to receive selected contacts names
     */
    public void getSelectedContactsNames(Consumer<String> receiver) {
        Observable.fromIterable(selectedItems)
                .map(new Function<Integer, String>()
                {
                    @Override
                    public String apply(@NonNull Integer position) throws Exception {
                        return deviceContacts.get(position);
                    }
                }).blockingSubscribe(receiver);
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

    private List<String> deviceContacts;
    private boolean isSelectable;
    private ArrayList<Integer> selectedItems = new ArrayList<>();
}
