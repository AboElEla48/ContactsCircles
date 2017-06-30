package com.aboelela.circles.data;

import android.content.SharedPreferences;

import com.aboelela.circles.CirclesApplication;

/**
 * Created by aboelela on 29/06/17.
 * This is the manager for getting/setting all data in preferences
 */

public class PreferencesManager
{
    private static final String PREFERENCES_NAME = "Circles";
    private static class Prefs {
        public final static String PREF_IS_CIRCLES_DEFINED = "PREF_IS_CIRCLES_DEFINED";
        public final static boolean PREF_IS_CIRCLES_DEFINED_DEFAULT = false;
    }

    /**
     * Is there any circles defined
     * @return
     */
    public static boolean isCirclesDefined() {
        SharedPreferences preferences = CirclesApplication.getInstance().getSharedPreferences(PREFERENCES_NAME, 0);
        return preferences.getBoolean(Prefs.PREF_IS_CIRCLES_DEFINED, Prefs.PREF_IS_CIRCLES_DEFINED_DEFAULT);
    }

    /**
     * set flag to indicate whether there are circles defined or not
     * @param isCirclesDefined
     */
    public static void setIsCirclesDefined(boolean isCirclesDefined) {
        SharedPreferences preferences = CirclesApplication.getInstance().getSharedPreferences(PREFERENCES_NAME, 0);
        preferences.edit().putBoolean(Prefs.PREF_IS_CIRCLES_DEFINED, isCirclesDefined).commit();
    }
}
