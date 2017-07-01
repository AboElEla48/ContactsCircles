/**
 * 
 */
package com.mvvm.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ahmed
 * 
 */
public class PreferencesUtil
{
	private PreferencesUtil()
	{
	}

	/**
	 * Save integer value to the given key in preferences
	 * 
	 * @param context
	 * @param key
	 * @param val
	 */
	public static void saveInteger(Context context, String key, int val)
	{
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.putInt(key, val);
		editor.commit();
	}

	/**
	 * Save string value to the given key in preferences
	 * 
	 * @param context
	 * @param key
	 * @param val
	 */
	public static void saveString(Context context, String key, String val)
	{
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.putString(key, val);
		editor.commit();
	}

	/**
	 * Save boolean value to the given key in preferences
	 * 
	 * @param context
	 * @param key
	 * @param val
	 */
	public static void saveBoolean(Context context, String key, boolean val)
	{
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.putBoolean(key, val);
		editor.commit();
	}

	/**
	 * Save array of strings to the given key in preferences
	 * 
	 * @param context
	 * @param key
	 */
	public static void saveSet(Context context, String key, List<String> values)
	{
		String valuesStr = "";
		for (String str : values)
		{
			valuesStr += str + PREF_UTIL_SEPARATOR;
		}

		saveString(context, key, valuesStr);
	}

	/**
	 * Save integer value to the given key in preferences
	 * 
	 * @param context
	 * @param key
	 */
	public static Integer getInteger(Context context, String key, int defValue)
	{
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getInt(key, defValue);
	}

	/**
	 * Save string value to the given key in preferences
	 * 
	 * @param context
	 * @param key
	 */
	public static String getString(Context context, String key, String defValue)
	{
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getString(key, defValue);
	}

	/**
	 * Save boolean value to the given key in preferences
	 * 
	 * @param context
	 * @param key
	 */
	public static boolean getBoolean(Context context, String key, boolean defValue)
	{
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getBoolean(key, defValue);
	}

	/**
	 * Save array of strings to the given key in preferences
	 * 
	 * @param context
	 * @param key
	 */
	public static List<String> getSet(Context context, String key)
	{
		ArrayList<String> values = new ArrayList<String>();
		String valuesStr = getString(context, key, "");

		int index;
		String val;
		while (valuesStr.length() > 0)
		{
			index = valuesStr.indexOf(PREF_UTIL_SEPARATOR);
			if (index > -1)
			{
				val = valuesStr.substring(0, index);
				values.add(val);
				valuesStr = valuesStr.substring(index + PREF_UTIL_SEPARATOR.length());
			}
			else
			{
				values.add(valuesStr);
				break;
			}
		}

		return values;
	}

	public final static String PREF_UTIL_SEPARATOR = "^&ABCZX9%_+=,";
}
