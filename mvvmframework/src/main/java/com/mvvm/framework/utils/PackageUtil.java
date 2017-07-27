package com.mvvm.framework.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by AboelelaA on 7/27/2017.
 * Utility for packages
 */

public final class PackageUtil
{
    private PackageUtil() {
    }

    /**
     * get list of permissions of current application
     * @param appContext : application context
     * @return : list of permissions in manifest file
     */
    public static String[] getApplicationRequestedPermissions(Context appContext) {
        try {
            PackageInfo pkgInfo = appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), PackageManager.GET_PERMISSIONS);
            if (pkgInfo.requestedPermissions  != null) {
                return pkgInfo.requestedPermissions;
            }
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return new String[]{};
    }
}
