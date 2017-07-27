package com.aboelela.circles.ui.home.fragments.viewContact.adapters;

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
import com.mvvm.framework.utils.PhoneUtil;

import java.util.List;

/**
 * Created by aboelela on 26/07/17.
 * Adapter for phone list in contact details screen
 */
public class ContactDetailsPhonesListAdapter extends RecyclerView.Adapter<ContactDetailsPhonesListAdapter.ViewHolder>
{
    public ContactDetailsPhonesListAdapter(List<String> phones) {
        this.phones = phones;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_contact_details_string_list_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    // call phone
                    PhoneUtil.dialPhone(CirclesApplication.getInstance(), viewHolder.itemTextView.getText().toString());
                }
                return true;
            }
        });

        viewHolder.itemSMSImageView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    // call phone
                    PhoneUtil.sendSMS(CirclesApplication.getInstance(), viewHolder.itemTextView.getText().toString());
                }
                return true;
            }
        });

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemTextView.setText(phones.get(position));
    }

    @Override
    public int getItemCount() {
        return phones.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView itemTextView;
        ImageView itemSMSImageView;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            itemTextView = (TextView) cardView.findViewById(R.id.list_contact_details_string_item_text_view);
            itemSMSImageView = (ImageView) cardView.findViewById(R.id.list_contact_details_string_item_sms_image_view);
        }

        void setText(String text) {
            itemTextView.setText(text);
        }
    }

    private List<String> phones;
}
