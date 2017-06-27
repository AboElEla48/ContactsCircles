/**
 * 
 */
package com.mvvm.framework.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

/**
 * @author ahmed
 * 
 * This utility is responsible for handling all file system operations 
 */
public class FileUtil
{
	private FileUtil()
	{
	}

	/**
	 * Check if the given file exists on storage or not
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isFileExists(String filePath)
	{
		File file = new File(filePath);
		return file.exists();
	}

	/**
	 * Check if the given file exists on storage or not
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isFileExistsInAssets(Context context, String assetsFilePath)
	{
		InputStream inputStream;
		try
		{
			inputStream = context.getAssets().open(assetsFilePath);
			inputStream.close();
		} catch (IOException e)
		{
			return false;
		}

		return true;

	}
	
	/**
	 * Create file at the given path
	 * @param filePath
	 * @return
	 */
	public static boolean createFile(String filePath)
	{
		File file = new File(filePath);
		file.mkdirs();
		try
		{
			return file.createNewFile();
		} catch (IOException e)
		{
		}
		return false;
	}

}
