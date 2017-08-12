package com.mvvm.framework.utils.swipe;

import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by aboelela on 05/08/17.
 * Animator to move swipe view
 */

public final class SwipeAnimator
{

    /**
     * move item horizontal with given width
     *
     * @param view      : the view to move
     * @param delta     : the horizontal value to move item with
     * @param direction : the direction of move
     */
    public static void moveItemHorizontalDelta(View view, float delta, SwipeHorizontalDirection direction) {
        // swipe direction
        int val = (direction == SwipeHorizontalDirection.Swipe_Left) ? -1 : 1;
        ViewCompat.animate(view)
                .translationXBy(delta * val)
                .start();
    }

    /**
     * Put item to specific place horizontal
     * @param view : the view to move
     * @param xValue
     */
    public static void moveItemHorizontalToPosition(View view, float xValue) {
        ViewCompat.animate(view)
                .x(xValue)
        .start();
    }

    public static void scaleItemHorizontalPercentageDelta(View view, float percentage, SwipeHorizontalDirection direction) {
        int val = (direction == SwipeHorizontalDirection.Swipe_Left) ? -1 : 1;
        ViewCompat.setPivotX(view, 0);
        ViewCompat.setPivotY(view, 0);
        ViewCompat.animate(view)
                .scaleXBy(percentage * val)
                .start();
    }

}
