/**
 * 
 */
package com.mvvm.framework.utils;

import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * @author AboElEla
 * 
 */
public class MailUtil
{
	private MailUtil()
	{

	}

	/**
	 * Send Email
	 * 
	 * @param context
	 * @param mailSubject
	 * @param mailBody
	 * @param recipients
	 */
	public static void sendMail(Context context, String mailSubject, String mailBody, List<String> recipients)
	{
		String[] mails = new String[recipients.size()];
		mails = recipients.toArray(mails);
				
		Intent intentEmail = new Intent(Intent.ACTION_SEND);
		intentEmail.putExtra(Intent.EXTRA_EMAIL, mails);
		intentEmail.putExtra(Intent.EXTRA_SUBJECT, mailSubject);
		intentEmail.putExtra(Intent.EXTRA_TEXT, mailBody);
		intentEmail.setType("message/rfc822");
		context.startActivity(Intent.createChooser(intentEmail, "Choose an email provider :"));
	}
}
