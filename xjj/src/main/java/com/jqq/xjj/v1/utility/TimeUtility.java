package com.jqq.xjj.v1.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtility {
	
	public static String getTimeStamp(String timeStamp, int days) {
		return String.valueOf(Long.parseLong(timeStamp) + calculateDay(days));
	}
	
	public static long calculateDay(int days) {
		return days * 24 * 60 * 60 * 1000;
	}
	
	public static Boolean isDeadline(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentTime = sdf.format(new Date());
		String timeStamp = sdf.format(new Date(Long.parseLong(time)));
		return currentTime.compareTo(timeStamp) < 0; 
	}
	
	public static String convertTime(long timeStamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date(timeStamp));
	}
	
	public static String timeToTimeStamp(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = sdf.parse(time);
			return String.valueOf(date.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
}
