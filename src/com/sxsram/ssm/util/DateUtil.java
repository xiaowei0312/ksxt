package com.sxsram.ssm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static Date getCurrentDate() {
		return new Date();
	}

	public static int getIntervalDays(Date fDate, Date oDate) {

		if (null == fDate || null == oDate) {

			return -1;

		}

		long intervalMilli = oDate.getTime() - fDate.getTime();

		return (int) (intervalMilli / (24 * 60 * 60 * 1000));

	}

	public static boolean dateGraterThan(Date d1, Date d2) {
		return d1.getTime() > d2.getTime();
	}

	public static Date strToDate(String str, String strFormat) {
		SimpleDateFormat format = new SimpleDateFormat(strFormat);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String dateToStr(Date date, String strFormat) {
		SimpleDateFormat format = new SimpleDateFormat(strFormat);
		return format.format(date);
	}
}






