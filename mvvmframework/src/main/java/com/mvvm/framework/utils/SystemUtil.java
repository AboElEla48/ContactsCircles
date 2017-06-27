package com.mvvm.framework.utils;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;

/**
 * This utility is responsible for handling all system operations
 * 
 * @author ahmed
 * 
 */
public class SystemUtil
{
	private SystemUtil()
	{
	}

    public static String  getDeviceIMEI(Context context)
    {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

	/**
	 * copy text to clipboard
	 * @param context
	 * @param text
	 */
	public static void copyTextToClipboard(Context context, String text)
	{
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText(text, text);
            clipboard.setPrimaryClip(clip);
        }
	}

	/**
	 * Check if external storage exists or not
	 * 
	 * @return
	 */
	public static boolean isExternalStorageExists()
	{
		File file = Environment.getExternalStorageDirectory();
		return file.exists();
	}

	/**
	 * Convert the given relative path to full path on External storage
	 * 
	 * @param relativePath
	 * @return
	 */
	public static String getFullPathOfFileOnExternalStorage(String relativePath)
	{
		File file = new File(Environment.getExternalStorageDirectory() + "/" + relativePath);
		return file.getAbsolutePath();
	}

}
