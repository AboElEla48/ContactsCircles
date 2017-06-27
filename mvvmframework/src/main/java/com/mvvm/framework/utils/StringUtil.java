package com.mvvm.framework.utils;

public class StringUtil
{
	private StringUtil()
	{
		
	}
	
	/**
	 * format time into the format hh : mm : sec
	 * @param hours
	 * @param minutes
	 * @param seconds
	 * @return
	 */
	public static String formatTime(int hours, int minutes, int seconds)
	{
		return getNumAsString(hours, 2) + " : " + getNumAsString(minutes, 2) + " : " + getNumAsString(seconds, 2);
	}
	
	/**
	 * Convert number to string with the given number of digits
	 * 
	 * @param num
	 * @param numOfDigits
	 * @return
	 */
	public static String getNumAsString(int num, int numOfDigits)
	{
		String str = "";
		
		if(num < 0)
		{
			num *=  -1;
			str = "-";		//add sign to final string
		}
		
		int originNum = num;
		
		//Count the number of digits in the given number 
		int numOfGivenDigits = 0;
		while(true)
		{
			num = num / 10;
			++numOfGivenDigits;
			
			if(num == 0)
				break;
		}
		
		int numOfZeros = numOfDigits - numOfGivenDigits;
		
		while(numOfZeros > 0)
		{
			str += "0";
			--numOfZeros;
		}
		
		str += originNum;
		
		return str;
	}

	/**
	 * Capitalize the first character of each word in given string
	 * @param string
	 * @return
	 */
	public static String capitalizeStringFirstChars(String string) {
		char[] chars = string.toLowerCase().toCharArray();
		boolean found = false;
		for (int i = 0; i < chars.length; i++) {
			if (!found && Character.isLetter(chars[i])) {
				chars[i] = Character.toUpperCase(chars[i]);
				found = true;
			} else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
				found = false;
			}
		}
		return String.valueOf(chars);
	}
}
