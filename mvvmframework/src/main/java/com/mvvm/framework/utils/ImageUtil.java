/**
 * 
 */
package com.mvvm.framework.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

/**
 * @author ahmed
 * 
 *         These are the utility functions needed to access images
 * 
 */
public final class ImageUtil
{
	private ImageUtil()
	{
	}

	/**
	 * Load image from device gallery and return the result to caller activity
	 * 
	 * @param activity
	 *            : Must be activity and handles the onActivityResult
	 * @param requestCode
	 *            : The requested code for result
	 */
	public static void loadImageFromGallery(Activity activity, int requestCode)
	{
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/jpeg|image/jpg|image/png|image/gif|image/bmp");
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * Load image from the given URi
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	public static Bitmap loadImageFromURi(Context context, Uri uri)
	{
		LogUtil.writeDebugLog(LOG_TAG, "Load image from URi: " + uri);

		try
		{
			return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
		}
		catch (FileNotFoundException ex)
		{
			LogUtil.writeErrorLog(LOG_TAG, ex.getMessage());
		}
		catch (IOException ex)
		{
			LogUtil.writeErrorLog(LOG_TAG, ex.getMessage());
		}

		return null;
	}
	
	/**
	 * Save given image to Media store and get its 
	 * @param context
	 * @param bitmap
	 * @param title
	 * @param description
	 * @return
	 */
	public static Uri saveImageToMediaStore(Context context, Bitmap bitmap, String title, String description)
	{
		LogUtil.writeDebugLog(LOG_TAG, "Save image to media store");
		
		String URL = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, title, description);
		
		return Uri.parse(URL);		
	}

	/**
	 * Load bitmap from given file path
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap loadImageFromPath(String path)
	{
		return BitmapFactory.decodeFile(path);
	}

	/**
	 * load image from assets
	 * 
	 * @param context
	 * @param assetsPath
	 * @return
	 */
	public static Bitmap loadImageFromAssets(Context context, String assetsPath)
	{
		InputStream inStream;
		try
		{
			inStream = context.getAssets().open(assetsPath);
			Bitmap bitmap = BitmapFactory.decodeStream(inStream);
			inStream.close();
			return bitmap;

		}
		catch (IOException e)
		{
			LogUtil.writeErrorLog(LOG_TAG, "Couldn't load image from assets " + assetsPath);
			LogUtil.writeErrorLog(LOG_TAG, e.getMessage());
		}

		return null;
	}

	/**
	 * Resize the given bitmap into the new width and height and keep the
	 * aspects ratio
	 * 
	 * @param srcBitmap
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static Bitmap resizeBitmap(Bitmap srcBitmap, float newWidth, float newHeight)
	{
		float srcWidth = srcBitmap.getWidth();
		float srcHeight = srcBitmap.getHeight();

		float ratio;

		if (srcWidth > srcHeight)
		{
			// Keep aspect ratio of width
			ratio = srcWidth / newWidth;
		}
		else
		{
			// Keep aspect ratio of height
			ratio = srcHeight / newHeight;
		}

		newWidth = srcWidth / ratio;
		newHeight = srcHeight / ratio;

		// create scale matrix
		Matrix matrix = new Matrix();
		matrix.postScale(ratio, ratio);

		// create new bitmap
		return Bitmap.createBitmap(srcBitmap, 0, 0, (int) srcWidth, (int) srcHeight, matrix, true);
	}

	/**
	 * convert Bitmap to Uri
	 * 
	 * @param bitmap
	 * @param extension
	 * @return
	 */
	public static Uri getUriFromBitmap(Context context, Bitmap bitmap, String extension, CompressFormat format)
	{
		String fileName = "image" + extension;
		String imageFullPath = context.getFilesDir() + "/" + fileName;
		saveBitmapToFile(bitmap, imageFullPath, format);
		File file = new File(imageFullPath);

		return Uri.fromFile(file);
	}

	/**
	 * Save bitmap to given file
	 * @param bitmap
	 * @param filePath
	 * @param format
	 */
	public static void saveBitmapToFile(Bitmap bitmap, String filePath, CompressFormat format)
	{
		FileOutputStream out = null;
		try
		{
			out = new FileOutputStream(filePath);
			bitmap.compress(format, 100, out);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (out != null)
				{
					out.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * create bitmap from given view
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap createBitmapFromView(View view)
	{
		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
		view.draw(canvas);
		return bitmap;
	}

	private final static String LOG_TAG = "ImageUtil";
}
