package com.aboelela.circles.ui;

import android.content.Context;
import android.content.Intent;

import com.aboelela.circles.ui.home.HomeActivity;
import com.mvvm.framework.utils.permissions.PermissionsActivity;

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
     * @param context : the caller context
     */
    public static void startCirclesHomeActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    /**
     * start permissions activity
     * @param context: the caller context
     */
    public static void showPermissionsActivity(Context context, String[] permissions, String[] messages) {
        Intent intent = new Intent(context, PermissionsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PermissionsActivity.Bundle_Permission_Str_Arr_Key, permissions);
        intent.putExtra(PermissionsActivity.Bundle_Permission_Msgs_Str_Arr_Key, messages);
        context.startActivity(intent);
    }
}
