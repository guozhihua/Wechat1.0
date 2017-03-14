package com.weixin.utils.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 日历 工具类
 * 
 * @author pc
 *
 */
public class CalendarUtil {

	private Date date;
	private Calendar calendar;
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();
    
    private static Logger logger = Logger.getLogger(CalendarUtil.class);

	public CalendarUtil(Date date) {
		this.date = date;
		this.calendar = Calendar.getInstance();
		calendar.setTime(date);
	}

	/**
	 * 获取日期的字符串格式
	 * 
	 * @param format
	 *            日期格式
	 * @return
	 */
	public String getString(String format) {
		String str = new SimpleDateFormat(format).format(date);
		return str;
	}

	public Integer getYear() {

		int year = calendar.get(Calendar.YEAR);

		return year;
	}

	public Integer getMonth() {

		int month = calendar.get(Calendar.MONTH) + 1;

		return month;
	}

	public Integer getDay() {

		int month = calendar.get(Calendar.DAY_OF_MONTH);

		return month;
	}

	/**
	 * 星期 （星期日 为 0）
	 * 
	 * @return
	 */
	public Integer getDayOfWeek() {
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		return week;
	}

	/**
	 * 本月的第一天
	 * 
	 * @return
	 */
	public String getFirstDayOfMonth(String format) {
		Calendar newcalendar = Calendar.getInstance();
		newcalendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		String str = new SimpleDateFormat(format).format(newcalendar.getTime());
		return str;
	}

	/**
	 * 本月的最后一天
	 * 
	 * @return
	 */
	public String getLastDayOfMonth(String format) {

		Calendar newcalendar = Calendar.getInstance();
		newcalendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		String str = new SimpleDateFormat(format).format(newcalendar.getTime());
		return str;
		 
	}
	
	
	/**
	 * 前后变化几天
	 * @param changeDay 变化的天数 （往后为正数 ，往前为负数）
	 * @param format 时间格式
	 * @return 
	 */
	public String getChangeDay(int changeDay,String format) {
		
		Calendar newcalendar = Calendar.getInstance();
		newcalendar.setTime(calendar.getTime());
		newcalendar.add(Calendar.DAY_OF_MONTH, changeDay);
		String str = new SimpleDateFormat(format).format(newcalendar.getTime());
		return str;
		
	}

	/**
	 * 下一天
	 * 
	 * @return
	 */
	public String getNextDay(String format) {
		Calendar newcalendar = Calendar.getInstance();
		newcalendar.setTime(calendar.getTime());
		newcalendar.add(Calendar.DAY_OF_MONTH, 1);
		String str = new SimpleDateFormat(format).format(newcalendar.getTime());
		return str;
	}

	/**
	 * 上一天
	 * 
	 * @return
	 */
	public String getLastDay(String format) {

		Calendar newcalendar = Calendar.getInstance();
		newcalendar.setTime(calendar.getTime());
		newcalendar.add(Calendar.DAY_OF_MONTH, -1);
		String str = new SimpleDateFormat(format).format(newcalendar.getTime());
		return str;
	}
 
	public static DateFormat getDateFormat() {
		DateFormat df = threadLocal.get();
		if (df == null) {
			df = new SimpleDateFormat(DATE_FORMAT);
			threadLocal.set(df);
		}
		return df;
	}

    public static String formatDate(Date date) {
    	if(date != null) {
    		return getDateFormat().format(date);
    	}
    	return null;
    }

	public static Date parse(String strDate) {
		try {
			return getDateFormat().parse(strDate);
		} catch (ParseException e) {
			logger.error("parse strDate error: " + e.getMessage());
			return null;
		}
	}

	public static void main(String[] args) {
		CalendarUtil cu = new CalendarUtil(new Date());
		System.out.println(cu.getYear());
		System.out.println(cu.getMonth());
		System.out.println(cu.getDay());
		System.out.println(cu.getDayOfWeek());
		System.out.println(cu.getFirstDayOfMonth("yyyy-MM-dd"));
		System.out.println(cu.getLastDayOfMonth("yyyy-MM-dd"));
		System.out.println(cu.getNextDay("yyyy-MM-dd"));
		System.out.println(cu.getLastDay("yyyy-MM-dd"));
	}
}
