package com.mvvm.framework.utils;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.mvvm.framework.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author Administrator
 */
public class ContactsUtil
{
    private ContactsUtil() {

    }

    /**
     * Save contact to device
     *
     * @param context : context to save contact
     * @param contact : contact to be saved
     * @return : saved contact Uri
     */
    public static Uri saveContactToDevice(Context context, ContactModel contact) {
        LogUtil.writeDebugLog(LOG_TAG, "saveContactToDevice");

        // Creates a new array of ContentProviderOperation objects.
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

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

        // Creates the display name for the new raw contact, as a StructuredName
        // data row.
        op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                // Sets the data row's MIME type to StructuredName
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                // Sets the data row's display name to the name in the UI.
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.contactName);

        // Builds the operation and adds it to the array of operations
        ops.add(op.build());

        for (String phoneNumber : contact.phones) {
            // Inserts the specified phone number and type as a Phone data row
            op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, "Other");

            // Builds the operation and adds it to the array of operations
            ops.add(op.build());
        }

        for (String email : contact.emails) {

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

        try {

            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {

            // Log exception
            LogUtil.writeErrorLog(LOG_TAG, "Exception encountered while inserting contact: " + e);
        }

        return null;

    }

    /**
     * Load contacts categorized by name
     *
     * @param context : the device context
     */
    public static Object loadDeviceContacts(final Context context,
                                            final Consumer<Map<String, ContactModel>> receiver) {

        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.Contacts.PHOTO_ID}, null, null, null);

        if (cursor == null) {
            return new Object();
        }

        final Map<String, ContactModel> contactsMap = new HashMap<>();
        Observable.fromIterable(RxCursorIterable.from(cursor))
                .subscribe(new Consumer<Cursor>()
                           {
                               @Override
                               public void accept(@io.reactivex.annotations.NonNull Cursor cursor) throws Exception {
                                   ContactModel contactModel;

                                   Long id;
                                   String contactName;
                                   String phone;
                                   String email = "";

                                   id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                                   contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                   phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                   try {
                                       email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME));
                                   } catch (Exception ex) {
                                       LogUtil.writeErrorLog(LOG_TAG, "Error getting email");
                                   }


                                   contactModel = contactsMap.get(contactName);
                                   if (contactModel == null) {
                                       contactModel = new ContactModel();
                                       contactModel.id = id;
                                       contactModel.contactName = contactName;
                                       contactsMap.put(contactName, contactModel);
                                   }

                                   if (phone.length() > 0) {
//                                       contactModel.bitmap = getPhoto(context, id);
                                       contactModel.bitmap = fetchThumbnail(fetchThumbnailId(context.getContentResolver(), phone), context.getContentResolver());

                                       contactModel.phones.add(phone);
                                   }

                                   if (email.length() > 0) {
                                       contactModel.phones.add(email);
                                   }
                               }
                           },
                        new Consumer<Throwable>()
                        {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                                LogUtil.writeErrorLog(LOG_TAG, "Error loading contacts from device");
                                throwable.printStackTrace();
                            }
                        }, new Action()
                        {
                            @Override
                            public void run() throws Exception {
                                receiver.accept(contactsMap);
                            }
                        });
        return new Object();

    }

    public static Bitmap getPhoto(Context context, Long contactId) {
        Uri contactPhotoUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        InputStream photoDataStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), contactPhotoUri);
        if (photoDataStream != null) {
            return BitmapFactory.decodeStream(photoDataStream);
        }
        else {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.test);
        }
    }

    private static final String[] PHOTO_ID_PROJECTION = new String[]{
            ContactsContract.Contacts.PHOTO_ID
    };

    private static final String[] PHOTO_BITMAP_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Photo.PHOTO
    };

    private static Bitmap fetchThumbnail(final int thumbnailId, ContentResolver contentResolver) {

        final Uri uri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, thumbnailId);
        final Cursor cursor = contentResolver.query(uri, PHOTO_BITMAP_PROJECTION, null, null, null);
        Bitmap thumbnail = null;
        if (cursor.moveToFirst()) {
            final byte[] thumbnailBytes = cursor.getBlob(0);
            if (thumbnailBytes != null) {
                thumbnail = BitmapFactory.decodeByteArray(thumbnailBytes, 0, thumbnailBytes.length);
            }
        }
        cursor.close();

        return thumbnail;

    }

    private static Integer fetchThumbnailId(ContentResolver contentResolver, String phoneNumber) {

        final Uri uri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        final Cursor cursor = contentResolver.query(uri, PHOTO_ID_PROJECTION, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");

        Integer thumbnailId = null;
        if (cursor.moveToFirst()) {
            thumbnailId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
        }
        cursor.close();
        return thumbnailId;

    }

    public static class ContactModel implements Comparable<ContactModel>, Parcelable
    {
        private long id;
        private String contactName = "";
        private ArrayList<String> phones = new ArrayList<>();
        private ArrayList<String> emails = new ArrayList<>();
        private Bitmap bitmap;

        public ContactModel() {
        }

        ContactModel(Parcel in) {
            contactName = in.readString();
            in.readStringList(phones);
            in.readStringList(emails);
        }

        public long getId() {
            return id;
        }

        public String getContactName() {
            return contactName;
        }

        public ArrayList<String> getEmails() {
            return emails;
        }

        public ArrayList<String> getPhones() {
            return phones;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        @Override
        public String toString() {
            String str = contactName;
            str += ",";
            for (String phone : phones) {
                str += phone + "-";
            }

            str += ",";
            for (String email : emails) {
                str += email + "-";
            }
            return str;
        }

        /**
         * Construct object from string
         *
         * @param str : the string of preferences to convert to object
         */
        public void fromString(String str) {
            int index;

            index = str.indexOf(",");
            contactName = str.substring(0, index);
            str = str.substring(index + 1);

            phones = new ArrayList<>();
            index = str.indexOf(",");
            String allPhones = str.substring(0, index);
            index = allPhones.indexOf("-");
            while (index > -1) {
                phones.add(allPhones.substring(0, index));
                allPhones = allPhones.substring(index + 1);
                index = allPhones.indexOf("-");
            }
            str = str.substring(index + 1);

            emails = new ArrayList<>();
            String allEmails = str;
            index = allEmails.indexOf("-");
            while (index > -1) {
                emails.add(allEmails.substring(0, index));
                allEmails = allEmails.substring(index + 1);
                index = allEmails.indexOf("-");
            }
        }

        @Override
        public int compareTo(@NonNull ContactModel o) {
            return contactName.compareTo(o.contactName);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(contactName);
            parcel.writeStringList(phones);
            parcel.writeStringList(emails);
        }

        public static final Creator<ContactModel> CREATOR = new Creator<ContactModel>()
        {
            @Override
            public ContactModel createFromParcel(Parcel in) {
                return new ContactModel(in);
            }

            @Override
            public ContactModel[] newArray(int size) {
                return new ContactModel[size];
            }
        };
    }

    private final static String LOG_TAG = "ContactsUtil";
}
