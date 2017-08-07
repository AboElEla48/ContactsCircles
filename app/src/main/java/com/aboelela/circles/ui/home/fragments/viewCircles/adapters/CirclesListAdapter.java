package com.aboelela.circles.ui.home.fragments.viewCircles.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboelela.circles.R;
import com.aboelela.circles.data.entities.Circle;
import com.aboelela.circles.ui.home.HomeActivityMessagesHelper;
import com.mvvm.framework.utils.LogUtil;
import com.mvvm.framework.utils.swipe.OnSwipeListener;
import com.mvvm.framework.utils.swipe.SwipeAnimator;
import com.mvvm.framework.utils.swipe.SwipeDetector;
import com.mvvm.framework.utils.swipe.SwipeHorizontalDirection;

import java.util.List;

/**
 * Created by aboelela on 02/07/17.
 * Create adapter for recycler view
 */

public class CirclesListAdapter extends RecyclerView.Adapter<CirclesListAdapter.ViewHolder>
{
    public CirclesListAdapter(List<Circle> circles) {
        this.circles = circles;
        swipeDetector = new SwipeDetector();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_circle_list_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cardView.setTag(viewHolder);
        viewHolder.cardView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(final View view, MotionEvent motionEvent) {

                swipeDetector.detectSwipe(motionEvent, new OnSwipeListener()
                {
                    @Override
                    public void onActionDown(float actionX, float actionY) {
                        LogUtil.writeDebugLog(LOG_TAG, "Action Down");
                    }

                    @Override
                    public void onActionUp(float actionX, float actionY) {
                        LogUtil.writeDebugLog(LOG_TAG, "Action Up");

                        // Show the contacts of this circle
                        HomeActivityMessagesHelper.sendMessageOpenCircleContacts(circles.get(viewHolder.getAdapterPosition()));
                    }

                    @Override
                    public void onSwipeStarted(float initialX, float initialY) {

                    }

                    @Override
                    public void onSwipeDown(float initialX, float initialY, float currentX, float currentY, float delta) {

                    }

                    @Override
                    public void onSwipeUp(float initialX, float initialY, float currentX, float currentY, float delta) {

                    }

                    @Override
                    public void onSwipeLeft(float initialX, float initialY, float currentX, float currentY, float delta) {
                        switch (viewHolder.listItemMode) {
                            case MODE_Normal: {
                                viewHolder.listItemMode = ListItemMode.MODE_Edit;
                                viewHolder.itemEditorView.setVisibility(View.VISIBLE);
                                break;
                            }

                            case MODE_Delete: {
                                // finish undo mode and start swipe right
                                viewHolder.listItemMode = ListItemMode.MODE_Normal;
                                viewHolder.itemUndoView.setVisibility(View.GONE);
                                break;
                            }

                            case MODE_Edit: {

                                break;
                            }
                        }
                    }

                    @Override
                    public void onSwipeRight(float initialX, float initialY, float currentX, float currentY, float delta) {
                        switch (viewHolder.listItemMode) {
                            case MODE_Normal: {
                                viewHolder.listItemMode = ListItemMode.MODE_Delete;
                                viewHolder.itemUndoView.setVisibility(View.VISIBLE);
                                break;
                            }

                            case MODE_Delete: {
                                break;
                            }

                            case MODE_Edit: {
                                // finish edit mode
                                viewHolder.listItemMode = ListItemMode.MODE_Normal;
                                viewHolder.itemEditorView.setVisibility(View.GONE);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onSwipeFinished(float initialX, float initialY) {
                        switch (viewHolder.listItemMode) {
                            case MODE_Normal: {
                                //TODO: restore item back to original state
                                break;
                            }

                            case MODE_Edit: {
                                // Show editor mode
//                                SwipeAnimator.scaleItemHorizontalDelta(view,
//                                        250,
//                                        SwipeHorizontalDirection.Swipe_Left);
                                SwipeAnimator.moveItemHorizontalDelta(view, view.getWidth(),
                                        SwipeHorizontalDirection.Swipe_Left);
                                break;
                            }

                            case MODE_Delete: {
                                // Show undo mode
                                SwipeAnimator.moveItemHorizontalDelta(view, view.getWidth(),
                                        SwipeHorizontalDirection.Swipe_Right);
                                break;
                            }
                        }

                    }
                });

                return true;
            }
        });

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setText(circles.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return circles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView itemTextView;
        View itemUndoView;
        View itemEditorView;
        ListItemMode listItemMode;


        ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            itemTextView = (TextView) cardView.findViewById(R.id.circle_item_textView);
            itemUndoView = itemView.findViewById(R.id.circle_item_delete_layout);
            itemEditorView = itemView.findViewById(R.id.circle_item_edit_layout);
            listItemMode = ListItemMode.MODE_Normal;
        }

        void setText(String text) {
            itemTextView.setText(text);
        }
    }

    // Define the different modes of list item
    private enum ListItemMode
    {
        MODE_Normal, MODE_Delete, MODE_Edit
    }

    // Hold circles model
    private List<Circle> circles;

    private SwipeDetector swipeDetector;

    private static final String LOG_TAG = "CirclesListAdapter";

}
