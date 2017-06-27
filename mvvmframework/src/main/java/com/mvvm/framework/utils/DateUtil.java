/**
 * 
 */
package com.mvvm.framework.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author ahmed
 * 
 */
public class DateUtil
{
	private DateUtil()
	{
	}

	/**
	 * Get the current date time
	 * 
	 * @return
	 */
	public static Date getNowDateTime()
	{
		return Calendar.getInstance().getTime();
	}

	/**
	 * format the current date time into string
	 * 
	 * @return
	 */
	public static String getNowDateTimeFormatted()
	{
		Calendar calendar = Calendar.getInstance();
		return getDateTimeFormatted(calendar);
	}

	/**
	 * Get the given date time as string formatted
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateTimeFormatted(Calendar calendar)
	{

		return "" + calendar.get(Calendar.DAY_OF_MONTH) + ":" + calendar.get(Calendar.MONTH) + ":" + calendar.get(Calendar.YEAR) + " - ("
				+ calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + " "
				+ (calendar.get(Calendar.HOUR_OF_DAY) >= 12 ? "PM" : "AM") + ")";
	}

	/**
	 * Convet date to string
	 * 
	 * @param date
	 * @param seperator
	 * @return
	 */
	public static String getDateAsString(Calendar date, String seperator)
	{
		return date.get(Calendar.YEAR) + seperator + (date.get(Calendar.MONTH) + 1) + seperator + date.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Convert date from string
	 * 
	 * @param str
	 * @param seperator
	 * @return
	 */
	public static Calendar getDateFromString(String str, String seperator)
	{
		Calendar calendar = Calendar.getInstance();

		if (str.length() > 0)
		{
			int day, month, year, internalIndex;

			internalIndex = str.indexOf(seperator);
			year = Integer.parseInt(str.substring(0, internalIndex));
			str = str.substring(internalIndex + seperator.length() );

			internalIndex = str.indexOf(seperator);
			month = Integer.parseInt(str.substring(0, internalIndex));
			str = str.substring(internalIndex + seperator.length() );

			day = Integer.parseInt(str);

			calendar.set(year, month - 1, day);
		}

		return calendar;
	}
}
