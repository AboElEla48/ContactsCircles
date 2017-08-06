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
     * swipe item in x direction
     * @param view : the view to swipe
     * @param initialX : the initial x event
     * @param currentX : the current x position
     * @param delta : the horizontal x value of swipe
     */
    public static void moveItemHorizontal(View view, float initialX, float currentX,
                                          final float delta) {
        ViewCompat.animate(view)
                .translationXBy(currentX - initialX + delta )
                .start();
    }

    /**
     * move item completely horizontal
     * @param view : the view to move
     * @param direction : the direction of move
     */
    public static void moveItemFullHorizontalWidth(View view, SwipeHorizontalDirection direction) {
        // swipe completely
        int val = (direction == SwipeHorizontalDirection.Swipe_Left) ? -1 : 1;
        ViewCompat.animate(view)
                .translationXBy(view.getWidth() * val)
                .start();
    }
}
