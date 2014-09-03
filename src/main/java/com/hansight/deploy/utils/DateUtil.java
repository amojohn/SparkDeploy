package com.hansight.deploy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static Date addDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		date = c.getTime();
		return date;
	}

	public static Date addHour(Date date, int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, hour);
		date = c.getTime();
		return date;
	}

	public static Date addMonth(Date date, int month) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, month);
		date = c.getTime();
		return date;
	}

	/**
	 * @param date
	 * @param date2
	 * @return
	 */
	public static boolean before(Date date, Date date2) {
		return date.compareTo(date2) < 0;
	}

	public static String format(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(format);
		return sdf.format(date);
	}

	public static String format(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static Date parse(String dateStr, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern(pattern);
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date parse(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(dateStr);
	}

	public static boolean sameDay(Date d1, Date d2) {
		String s1 = DateUtil.format(d1, "yyyy-MM-dd");
		String s2 = DateUtil.format(d2, "yyyy-MM-dd");
		return s1.equals(s2);
	}

	public static Date getCurrentMonthStart() {
		String d = format(new Date(), "yyyy-MM");
		Date date = parse(d + "-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
		return date;
	}

	public static Date getCurrentWeekStart() {
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		try {
			int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
			c.add(Calendar.DATE, -weekday);
			c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c.getTime();
	}

	public static Date getTodayStart() {
		String d = format(new Date(), "yyyy-MM-dd");
		Date date = parse(d + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
		return date;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(format(getCurrentWeekStart()));
	}

}
