package com.aboelela.circles.ui.home.fragments.viewCircles.adapters;

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
import com.aboelela.circles.data.entities.Circle;
import com.aboelela.circles.ui.home.HomeActivityMessagesHelper;

import java.util.List;

/**
 * Created by aboelela on 02/07/17.
 * Create adapter for recycler view
 */

public class CirclesListAdapter extends RecyclerView.Adapter<CirclesListAdapter.ViewHolder>
{
    public CirclesListAdapter(List<Circle> circles) {
        this.circles = circles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_circle_list_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // mark item as selected only
                    selectedItem = viewHolder.getAdapterPosition();
                    notifyItemChanged(viewHolder.getAdapterPosition());
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    // Show the contacts of this circle
                    HomeActivityMessagesHelper.sendMessageOpenCircleContacts(circles.get(viewHolder.getAdapterPosition()));
                }
                return true;
            }
        });

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setText(circles.get(position).getName());

        // set item selected if this is the selection index
        holder.itemView.setSelected(position == selectedItem);
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
        return circles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView itemTextView;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            itemTextView = (TextView) cardView.findViewById(R.id.circle_item_textView);
        }

        void setText(String text) {
            itemTextView.setText(text);
        }
    }

    // Hold circles model
    private List<Circle> circles;

    // Hold selected item index
    private int selectedItem = -1;

}
