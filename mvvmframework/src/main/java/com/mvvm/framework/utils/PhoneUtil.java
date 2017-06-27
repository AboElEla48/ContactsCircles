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
	 * @param context
	 * @param phoneNumber
	 */
	public static void dialPhone(Context context, String phoneNumber)
	{
		Uri number = Uri.parse("tel:" + phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        context.startActivity(callIntent);
	}
}
