package com.aboelela.circles.ui.home.fragments.viewCircleContacts.adapters;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboelela.circles.CirclesApplication;
import com.aboelela.circles.R;
import com.mvvm.framework.utils.ContactsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aboelela on 22/07/17.
 * Adapter for list displaying circle contacts
 */

public class CircleContactsListAdapter extends RecyclerView.Adapter<CircleContactsListAdapter.ViewHolder>
{
    public CircleContactsListAdapter(@NonNull List<ContactsUtil.ContactModel> contacts) {
        circleContacts = contacts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_circle_contacts_list_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // mark item as selected only
                    if (selectedItems.contains(viewHolder.getAdapterPosition())) {
                        // item is selected, deselect it
                        selectedItems.remove(selectedItems.indexOf(viewHolder.getAdapterPosition()));
                    }
                    else {
                        // mark item as selected
                        selectedItems.add(viewHolder.getAdapterPosition());
                    }
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    // TODO open contact details

                }
                return true;
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setText(circleContacts.get(position).getContactName());

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
        return circleContacts.size();
    }

    /**
     * View holder to display contacts
     */
    class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView itemTextView;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            itemTextView = (TextView) cardView.findViewById(R.id.circle_contact_item_textView);
        }

        void setText(String text) {
            itemTextView.setText(text);
        }
    }

    private List<ContactsUtil.ContactModel> circleContacts;
    private ArrayList<Integer> selectedItems = new ArrayList<>();
}
