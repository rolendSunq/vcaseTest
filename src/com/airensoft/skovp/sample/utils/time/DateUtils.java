package com.airensoft.skovp.sample.utils.time;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils
{
	public static Date StringToDate(String stringDate)
	{
		Date date = null;
		try
		{
			String from = stringDate.replace("/", "-");
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = transFormat.parse(from);
		}
		catch (Exception e)
		{
			return null;
		}
		return date;
	}
	
	public static String TimestamptToString(Integer timestamp)
	{
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date date = new Date(timestamp * 1000L);
		return transFormat.format(date);
	}
}
