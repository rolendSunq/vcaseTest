package com.airensoft.skovp.sample.utils.common;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class TimestampManager
{
	public static int A_MINUTE = 60;
	public static int A_HOUR = 60 * 60;
	public static int A_DAY = 60 * 60 * 24;
	public static int A_MONTH = 60 * 60 * 24 * 30;
	
	public static Integer addDayAtTimestamp(Integer timestamp, Integer addDay)
	{
		Integer addedTimestamp;
		Date date = new Date((timestamp * 1000L));
		
		Calendar cal = new GregorianCalendar();  
       	cal.setTime(date);
       	
       	cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + addDay);
       	
       	addedTimestamp = (int)(cal.getTimeInMillis() / 1000);
       	
		return addedTimestamp;
	}
	
	public static Integer addSecondsAtCurrentTimestamp(Integer addSeconds)
	{
		Integer addedTimestamp;
		
		Calendar cal = new GregorianCalendar();  
       	
       	cal.set(Calendar.SECOND, Calendar.SECOND + addSeconds);
       	
       	addedTimestamp = (int)(cal.getTimeInMillis() / 1000);
       	
		return addedTimestamp;
	}
	
	public static Integer addSecondsAtTimestamp(Integer timestamp, Integer addSeconds)
	{
		Integer addedTimestamp;
		Date date = new Date((timestamp * 1000L));
		
		Calendar cal = new GregorianCalendar();  
		cal.setTime(date);
		
		cal.set(Calendar.SECOND, Calendar.SECOND + addSeconds);
		
		addedTimestamp = (int)(cal.getTimeInMillis() / 1000);
		
		return addedTimestamp;
	}
	
	public static Integer getNowTimestamp()
	{
		Integer timestamp;
		Calendar today = Calendar.getInstance();
		
//		today.set(Calendar.HOUR_OF_DAY, 0);
//		today.set(Calendar.MINUTE, 0);
//		today.set(Calendar.SECOND, 0);
		
		timestamp = (int)(today.getTimeInMillis() / 1000);
		
		return timestamp;
	}
	
	public static Integer getTodayTimestampExceptTime()
	{
		Integer timestamp;
		Calendar today = Calendar.getInstance();
		
		today.set(Calendar.HOUR_OF_DAY, 23);
		today.set(Calendar.MINUTE, 59);
		today.set(Calendar.SECOND, 59);
		
		timestamp = (int)(today.getTimeInMillis() / 1000);
		
		return timestamp;	
	}
	
	public static Integer getTodayTimestampStartTime()
	{
		Integer timestamp;
		Calendar today = Calendar.getInstance();
		
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		
		timestamp = (int)(today.getTimeInMillis() / 1000);
		
		return timestamp;	
	}
	
	public static Integer get2037Timestamp()
	{
		Integer timestamp;
		Calendar year2037 = Calendar.getInstance();
		
		year2037.set(Calendar.YEAR, 2037);
		
		timestamp = (int)(year2037.getTimeInMillis() / 1000);
		
		return timestamp;	
	}
	
	static public String TimeToTimestamp(String date, String time)
	{
		// 10/13/2010", "1:00 PM -> "2010-09-23 19:49:18"
		try
		{
			StringBuffer timestamp = new StringBuffer();
			
			if(date != null && date.length() > 0)
			{
				StringTokenizer dateTokenizer = new StringTokenizer(date, "/");
				while(dateTokenizer.hasMoreTokens())
				{
					String day = dateTokenizer.nextToken().trim();
					String month = dateTokenizer.nextToken().trim();
					String year = dateTokenizer.nextToken().trim();
	
					timestamp.append(year + "/" + month + "/" + day + " ");
				}
			}
			else
			{
				timestamp.append("1900:00:00 ");
			}
			
			if(time != null && time.length() > 0)
			{
				String amPm = time.substring(time.length()-2,time.length());
				time = time.substring(0,time.length()-2);
				
				Integer plusTime = 0;
				if(amPm.equals("PM"))
				{
					plusTime = 12;
				}
				
				
				StringTokenizer timeTokenizer = new StringTokenizer(time, ":");
				while(timeTokenizer.hasMoreTokens())
				{
					timestamp.append(Integer.parseInt(timeTokenizer.nextToken().trim())+plusTime);
					timestamp.append(":");
					timestamp.append(timeTokenizer.nextToken().trim());
					timestamp.append(":");
					timestamp.append("00");
				}
			}
			else
			{
				timestamp.append("00:00:00");
			}
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
		}
		 
		return "";
	}
	
	public static String TimestampToDateString(Integer timestamp) 
	{
		Date date = new Date((timestamp * 1000L));
		
		Calendar cal = new GregorianCalendar();  
       	cal.setTime(date);
		
		return cal.get(Calendar.YEAR) + "-" + 
		(String.format("%02d", cal.get(Calendar.MONTH)+1)) + "-" + 
		String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)) + " " + 
		String.format("%02d", cal.get(Calendar.HOUR))+ ":" + 
		String.format("%02d", cal.get(Calendar.MINUTE)) + ":" +
		String.format("%02d", cal.get(Calendar.SECOND));
	}
	
	public static String TimestampToOnlyDateString(Integer timestamp) 
	{
		Date date = new Date((timestamp * 1000L));
		
		Calendar cal = new GregorianCalendar();  
		cal.setTime(date);
		
		return cal.get(Calendar.YEAR) + "-" + 
		(String.format("%02d", cal.get(Calendar.MONTH)+1)) + "-" + 
		String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));
	}
	
	public static int getYearFromTimestamp(Integer timestamp)
	{
		Date date = new Date((timestamp * 1000L));
		
		Calendar cal = new GregorianCalendar();  
		cal.setTime(date);

		return cal.get(Calendar.YEAR);
	}
	
	public static int getMonthFromTimestamp(Integer timestamp)
	{
		Date date = new Date((timestamp * 1000L));
		
		Calendar cal = new GregorianCalendar();  
		cal.setTime(date);

		return cal.get(Calendar.MONTH);
	}
	
	public static int getDayFromTimestamp(Integer timestamp)
	{
		Date date = new Date((timestamp * 1000L));
		
		Calendar cal = new GregorianCalendar();  
		cal.setTime(date);

		return cal.get(Calendar.DAY_OF_MONTH);
	}		
}
