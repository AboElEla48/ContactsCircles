package com.mvvm.framework.utils.swipe;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by aboelela on 05/08/17.
 * Detector for swipe. It parses the motion event into swipe events
 */

public class SwipeDetector
{
    private float initialX = -1f;
    private float initialY = -1f;

    private float lastX = -1f;
    private float lastY = -1f;

    private boolean isPotentialAnimation = false;
    private boolean isAnimating = false;

    public void detectSwipe(MotionEvent motionEvent, OnSwipeListener swipeListener) {
        Log.e(LOG_TAG, "--> Action: " + motionEvent.getAction());

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE: {

                if(isAnimating) {
                    lastX = -1f;
                    lastY = -1f;

                    isAnimating = false;
                    isPotentialAnimation = false;

                    swipeListener.onSwipeFinished(initialX, initialY);
                }
                else {
                    swipeListener.onActionUp(motionEvent.getX(), motionEvent.getY());
                }
                break;
            }

            case MotionEvent.ACTION_DOWN: {

                lastX = motionEvent.getX();
                lastY = motionEvent.getY();

                initialX = lastX;
                initialY = lastY;
                isPotentialAnimation = true;
                swipeListener.onActionDown(initialX, initialY);

                break;
            }

            case MotionEvent.ACTION_MOVE: {

                if(isPotentialAnimation) {
                    isPotentialAnimation = false;
                    isAnimating = true;
                    swipeListener.onSwipeStarted(initialX, initialY);
                }
                else {

                    float dx = motionEvent.getX() - lastX;
                    float dy = motionEvent.getY() - lastY;

                    lastX = motionEvent.getX();
                    lastY = motionEvent.getY();

                    if (dx < 0) {
                        swipeListener.onSwipeLeft(initialX, initialY, lastX, lastY, dx);
                    }
                    else {
                        swipeListener.onSwipeRight(initialX, initialY, lastX, lastY, dx);
                    }

                    if (dy < 0) {
                        swipeListener.onSwipeUp(initialX, initialY, lastX, lastY, dy);
                    }
                    else {
                        swipeListener.onSwipeDown(initialX, initialY, lastX, lastY, dy);
                    }
                }

                break;
            }
        }
    }

    private String LOG_TAG = "SwipeAdapter";
}
