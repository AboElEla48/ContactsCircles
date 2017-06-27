/**
 * 
 */
package com.mvvm.framework.utils;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * @author ahmed
 * 
 */
public class CameraUtil
{
	private CameraUtil()
	{

	}

	/**
	 * Capture image and return the result to caller activity
	 * 
	 * @param activity
	 *            : caller activity
	 * @param imageFilePath
	 *            : the path of output image file
	 * @param resultCode
	 *            : the required result code for result to caller activity
	 */
	public static void captureCameraImage(Activity activity, String imageFilePath, int resultCode)
	{
		File file = new File(imageFilePath);
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		activity.startActivityForResult(intent, resultCode);
	}

    /**
     * capture image from camera
     * @param activity
     * @param requestCode
     */
    public  static void captureCameraImage(Activity activity, int requestCode)
    {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(cameraIntent, requestCode);

        // Put this code into your activity result code
        /*
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            */
    }
}
