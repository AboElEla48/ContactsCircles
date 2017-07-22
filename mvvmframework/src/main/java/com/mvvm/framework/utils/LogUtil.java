package com.mvvm.framework.utils;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author ahmed
 * 
 */
public class LogUtil
{
	/**
	 * Write debug message
	 * 
	 * @param logTag : Tag for log
	 * @param logMessage : message for log
	 */
	public static void writeDebugLog(String logTag, String logMessage)
	{
		if (mUseDebugLog)
		{
			Log.d("" + logTag, "" + logMessage);
		}

		if (mUseFileLog)
		{
			writeToFile(logTag, logMessage);
		}
	}

	/**
	 * Write Error message
	 * 
	 * @param logTag : Tag for log
	 * @param logMessage : message for log
	 */
	public static void writeErrorLog(String logTag, String logMessage)
	{
		if (mUseErrorLog)
		{
			Log.e("" + logTag, "" + logMessage);
		}

		if (mUseFileLog)
		{
			writeToFile(logTag, logMessage);
		}
	}

    /**
     * Log error exception
     * @param logTag tag for log
     * @param ex exception to log
     */
    public static void writeErrorLog(String logTag, Exception ex)
    {
        ex.printStackTrace();

        if (mUseErrorLog)
        {
            Log.e("" + logTag, "Exception Message: " + ex.getMessage());
        }

        if (mUseFileLog)
        {
            writeToFile(logTag, ex.getMessage());
        }
    }

	/**
	 * Log error exception
	 * @param logTag tag to log
	 * @param ex exception to log
	 */
	public static void writeErrorLog(String logTag, String message, Exception ex)
	{
		//ex.printStackTrace();

		if (mUseErrorLog)
		{
			Log.e("" + logTag, message, ex);
		}

		if (mUseFileLog)
		{
			writeToFile(logTag, ex.getMessage());
		}
	}

	/**
	 * Write Warning message
	 * 
	 * @param logTag tag to log
	 * @param logMessage message to log
	 */
	public static void writeWarningLog(String logTag, String logMessage)
	{
		if (mUseErrorLog)
		{
			Log.w("" + logTag, "" + logMessage);
		}

		if (mUseFileLog)
		{
			writeToFile(logTag, logMessage);
		}
	}

	/**
	 * Write Info message
	 * 
	 * @param logTag tag to log
	 * @param logMessage message to log
	 */
	public static void writeInfoLog(String logTag, String logMessage)
	{
		if (mUseInfoLog)
		{
			Log.i("" + logTag, "" + logMessage);
		}
		
		if (mUseFileLog)
		{
			writeToFile(logTag, logMessage);
		}
	}

	/**
	 * Write given log to log file stream
	 * 
	 * @param logTag log to log
	 * @param logMessage message to log
	 */
	private static void writeToFile(String logTag, String logMessage)
	{
		// Check the existence of external storage for log
		if (SystemUtil.isExternalStorageExists())
		{
			// get the full path of log file
			String logFileFullPath = SystemUtil.getFullPathOfFileOnExternalStorage(Log_File_Path);

			try
			{
				FileOutputStream fileOut = new FileOutputStream(logFileFullPath, true);
				DataOutputStream outStream = new DataOutputStream(fileOut);

				// write log line
				outStream.writeBytes(DateUtil.getNowDateTimeFormatted() + ":\t" + logTag + "\t\t\t" + logMessage);

				// write log separation
				outStream.writeBytes("\n");

				// close stream
				fileOut.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

		}

	}

	// Define compile time flag to enable/disable log on files
	private final static boolean MASTER_LOG = true;
	private final static boolean mUseFileLog = MASTER_LOG & false;
	private final static boolean mUseDebugLog = MASTER_LOG & true;
	private final static boolean mUseErrorLog = MASTER_LOG & true;
	private final static boolean mUseInfoLog = MASTER_LOG & true;



	// Define the name of log file
	private final static String Log_File_Path = "Util_log.txt";
}
