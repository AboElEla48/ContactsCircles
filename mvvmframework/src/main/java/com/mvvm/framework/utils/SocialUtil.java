/**
 * 
 */
package com.mvvm.framework.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @author AboElEla
 * 
 */
public class SocialUtil
{
	private SocialUtil()
	{

	}

	/**
	 * Share image to social via sharing intent
	 * 
	 * @param context
	 * @param imageURi
	 * @param title
	 */
	public static void shareImageToSocial(Context context, Uri imageURi, String title, String mimeType)
	{
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
		shareIntent.putExtra(Intent.EXTRA_STREAM, imageURi);
		shareIntent.setType(mimeType); // i.e.
										// shareIntent.setType("image/jpeg");
		context.startActivity(Intent.createChooser(shareIntent, title));
	}

	/**
	 * Share text to sociak via sharing intent
	 * 
	 * @param context
	 * @param title
	 * @param text
	 */
	public static void shareTextToSocial(Context context, String title, String text)
	{
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
		shareIntent.putExtra(Intent.EXTRA_TEXT, text);
		context.startActivity(Intent.createChooser(shareIntent, title));
	}
}
