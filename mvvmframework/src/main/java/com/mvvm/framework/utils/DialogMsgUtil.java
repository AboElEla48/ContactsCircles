/**
 * 
 */
package com.mvvm.framework.utils;

import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * @author AboElEla
 * 
 */
public class DialogMsgUtil
{
	private DialogMsgUtil()
	{
	}

	/**
	 * Create template dialog builder
	 * 
	 * @param context
	 * @param title
	 * @param msg
	 * @param dialogButtons
	 * @param iconDrawableResId
	 * @return
	 */
	public static AlertDialog createAlertDialog(Context context, String title, String msg, List<DialogMsgButton> dialogButtons, int iconDrawableResId)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(msg);

		for (DialogMsgButton dialogMsgButton : dialogButtons)
		{
			alertDialog.setButton(dialogMsgButton.mWhichButton, dialogMsgButton.mButtonLabel, dialogMsgButton.mListener);
		}

		alertDialog.setIcon(iconDrawableResId);

		return alertDialog;
	}

	/**
	 * Define the dialog builder buttons structure
	 * 
	 * @author AboElEla
	 * 
	 */
	public static class DialogMsgButton
	{
		private int mWhichButton;
		private DialogInterface.OnClickListener mListener;
		private String mButtonLabel;

		public DialogMsgButton(int which, String label, DialogInterface.OnClickListener listener)
		{
			mWhichButton = which;
			mListener = listener;
			mButtonLabel = label;
		}

    }

	/**
	 * Create progress dialog
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @return
	 */
	public static ProgressDialog createProgressDialog(Context context, String title, String message)
	{
		return ProgressDialog.show(context, title, message);
	}

	/**
	 * Show info message
	 * 
	 * @param context
	 * @param message
	 */
	public static void showInfoMessage(Context context, CharSequence message)
	{
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
}
