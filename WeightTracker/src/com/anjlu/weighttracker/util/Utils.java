package com.anjlu.weighttracker.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public Utils() {
	}

	public static String formatDate(Date date) {

		SimpleDateFormat iso8601Format = new SimpleDateFormat(
				"yyyy-MM-dd");

		return iso8601Format.format(date);
	}
	
	public static String toFormat(String date, String format){
		SimpleDateFormat iso8601Format = new SimpleDateFormat(
				"yyyy-MM-dd");
		SimpleDateFormat outFormat = new SimpleDateFormat(format);
		Date outDate;
		try {
			outDate = iso8601Format.parse(date);
			return outFormat.format(outDate);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return date;
	}

}
