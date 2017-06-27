/**
 * 
 */
package com.mvvm.framework.utils;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * @author Administrator
 * 
 */
public class ContactsUtil
{
	private ContactsUtil()
	{

	}

	/**
	 * Save contact to device
	 * 
	 * @param context
	 * @param contact
	 * @return
	 */
	public static Uri saveContactToDevice(Context context, ContactModel contact)
	{
		LogUtil.writeDebugLog(LOG_TAG, "saveContactToDevice");

		// Creates a new array of ContentProviderOperation objects.
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

		/*
		 * Creates a new raw contact with its account type (server type) and
		 * account name (user's account). Remember that the display name is not
		 * stored in this row, but in a StructuredName data row. No other data
		 * is required.
		 */
		ContentProviderOperation.Builder op = ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, "Other")
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, contact.contactName);

		ops.add(op.build());
		//
		// for (String phoneNumber : contact.phones)
		// {
		// op =
		// ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
		// 0)
		// op = op.withValue(ContactsContract.Data.MIMETYPE,
		// ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
		// .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
		// phoneNumber)
		// .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, "Other");
		// }

		// for (String address : contact.addresses)
		// {
		// op = op.withValue(ContactsContract.Data.MIMETYPE,
		// ContactsContract.CommonDataKinds..CONTENT_ITEM_TYPE)
		// .withValue(ContactsContract.CommonDataKinds.DISPLAY_NAME, address)
		// .withValue(ContactsContract.CommonDataKinds.Email.TYPE, "Other");
		// }

		// for (String email : contact.emails)
		// {
		// op = op.withValue(ContactsContract.Data.MIMETYPE,
		// ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
		// .withValue(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME,
		// email)
		// .withValue(ContactsContract.CommonDataKinds.Email.TYPE, "Other");
		// }

		// Builds the operation and adds it to the array of operations
		// ops.add(op.build());

		// Creates the display name for the new raw contact, as a StructuredName
		// data row.
		op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		// Sets the data row's MIME type to StructuredName
				.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				// Sets the data row's display name to the name in the UI.
				.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.contactName);

		// Builds the operation and adds it to the array of operations
		ops.add(op.build());

		for (String phoneNumber : contact.phones)
		{
			// Inserts the specified phone number and type as a Phone data row
			op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
					.withValue(ContactsContract.CommonDataKinds.Phone.TYPE, "Other");

			// Builds the operation and adds it to the array of operations
			ops.add(op.build());
		}

		for (String email : contact.emails)
		{

			// Inserts the specified email and type as a Phone data row
			op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME, email)
					.withValue(ContactsContract.CommonDataKinds.Email.TYPE, "Other");
			
			/*
			 * Demonstrates a yield point. At the end of this insert, the batch
			 * operation's thread will yield priority to other threads. Use after
			 * every set of operations that affect a single contact, to avoid
			 * degrading performance.
			 */
			op.withYieldAllowed(true);

			// Builds the operation and adds it to the array of operations
			ops.add(op.build());

		}

		try
		{

			context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		}
		catch (Exception e)
		{

			// Log exception
			LogUtil.writeErrorLog(LOG_TAG, "Exception encountered while inserting contact: " + e);
		}

		return null;

	}

	public static class ContactModel
	{
		public String contactName;
		public ArrayList<String> phones = new ArrayList<String>();
		public ArrayList<String> addresses = new ArrayList<String>();
		public ArrayList<String> emails = new ArrayList<String>();

	}

	private final static String LOG_TAG = "ContactsUtil";
}
