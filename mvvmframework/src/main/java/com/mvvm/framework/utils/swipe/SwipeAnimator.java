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

}
