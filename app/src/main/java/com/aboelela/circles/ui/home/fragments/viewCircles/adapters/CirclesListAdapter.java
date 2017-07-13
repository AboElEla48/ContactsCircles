package com.aboelela.circles.ui.home.fragments.viewCircles.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboelela.circles.R;
import com.aboelela.circles.data.CirclesModel;

/**
 * Created by aboelela on 02/07/17.
 * Create adapter for recycler view
 */

public class CirclesListAdapter extends RecyclerView.Adapter<CirclesListAdapter.ViewHolder>
{
    public CirclesListAdapter(CirclesModel circlesModel) {
        this.circlesModel = circlesModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_circle_list_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setText(circlesModel.getCircles().get(position).getName());
    }

    @Override
    public int getItemCount() {
        return circlesModel.getCircles().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }

        void setText(String text) {
            ((TextView)cardView.findViewById(R.id.circle_item_textView)).setText(text);
        }
    }

    private CirclesModel circlesModel;

}
