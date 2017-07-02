package com.aboelela.circles.ui.home.fragments.viewCircles;

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
                .inflate(R.layout.list_circle_item, parent, false);
        TextView v = (TextView) view.findViewById(R.id.circle_item_textView);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(circlesModel.getCircles().get(position).getName());
    }

    @Override
    public int getItemCount() {
        return circlesModel.getCircles().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;

        public ViewHolder(TextView itemView) {
            super(itemView);
            textView = itemView;
        }
    }

    private CirclesModel circlesModel;

}
