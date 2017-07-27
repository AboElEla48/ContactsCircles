package com.mvvm.framework.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PhoneUtil
{
	private PhoneUtil()
	{
		
	}
	
	/**
	 * Dial given phone number
	 * @param context : Context to use for call
	 * @param phoneNumber : phone number to call
	 *                    Need permission "android.permission.CALL_PHONE"
	 */
	public static void dialPhone(Context context, String phoneNumber)
	{
		Uri number = Uri.parse("tel:" + phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(callIntent);
	}
}
