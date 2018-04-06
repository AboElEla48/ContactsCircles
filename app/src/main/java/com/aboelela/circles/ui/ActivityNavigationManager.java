package com.aboelela.circles.ui;

import android.content.Context;
import android.content.Intent;

import com.aboelela.circles.ui.circles.CirclesListActivity;

/**
 * Created by aboelela on 29/06/17.
 * <p>
 * This is the manager for navigating from one activity to anotehr
 */

public final class ActivityNavigationManager
{
    private ActivityNavigationManager() {
    }

    /**
     * Start the home screen
     *
     * @param context : the caller context
     */
    public static void startCirclesHomeActivity(Context context) {
        Intent intent = new Intent(context, CirclesListActivity.class);
        context.startActivity(intent);
    }

}
