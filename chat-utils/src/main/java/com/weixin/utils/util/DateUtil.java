package com.weixin.utils.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author YanXue 2012-08-09
 * @version 1.0 日期工具类
 * */
public class DateUtil {
	public final static String FORMAT_ALL = "yyyy-MM-dd HH:mm:ss";

	public final static String FORMAT_YEAR_MON_DAY = "yyyy-MM-dd";
	
	private final static String FORMAT_YEAR_MON = "yyyy-MM";
	
	private final static String FORMAT_YEARMONDAY = "yyMMdd";

	private final static String FORMAT_YEARMONDAYDAY = "yyyyMMdd";
	public final static String dayNames[] = { "0", "1", "2", "3", "4", "5", "6" }; // 0：周日
	
	public static final String				HM				= "HH:mm";
	public static final String				YMDHM			= "yyyy-MM-dd HH:mm";
	public static final String				YMDHMS			= "yyyyMMddHHmmss";
	public static final SimpleDateFormat	DF_SHORT_CN		= new SimpleDateFormat(FORMAT_YEAR_MON_DAY);
	public static final SimpleDateFormat	SDF_YMDHM		= new SimpleDateFormat(YMDHM);
	public static final SimpleDateFormat	SDF_HM			= new SimpleDateFormat(HM);
	public static final SimpleDateFormat	DF_CN			= new SimpleDateFormat(FORMAT_ALL);
	public static final int					REALTIME		= 0;

	public static final int					HOURLY			= 1;
	public static final int					DAILY			= 2;
	public static final int					BIWEEKLY		= 3;
	public static final int					WEEKLY			= 4;
	public static final int					MONTHLY			= 5;
	public static final int					QUARTLY			= 6;
	public static final int					BIYEARLY		= 7;
	public static final int					YEARLY			= 8;
	
	public static final long				day				= 86400000l;

	// 返回日期型当前时间
	public static Date getCurrentDate() {
		return getCurrentDate(FORMAT_ALL);
	}

	// 返回字符串的当前时间
	public static String getCurrentTime() {
		return getCurrentTime(FORMAT_ALL);
	}
	public static String getCurrentTimes() {
		return getCurrentTime(YMDHMS);
	}

	/** 返回字符串的当前时间
	 * @return YYYY-MM-DD 格式日期 
	 */
	public static String getYearMonthDate() {
		return getCurrentTime(FORMAT_YEAR_MON_DAY);
	}
	//返回字符串的当前年月
	public static String getYearMonth(){
		return getCurrentTime(FORMAT_YEAR_MON);
	}
	@SuppressWarnings("deprecation")
	public static String getYearMonthDateBill(Date date) {
		try {
			String dateBill = (date.getYear() + 1900) + "年"
					+ (date.getMonth() + 1) + "月" + date.getDate() + "日";
			return dateBill;
		} catch (Exception e) {
			return null;
		}
	}

	public static String getYearMonthDate(Date date) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_YEAR_MON_DAY);
			return dateFormat.format(date.getTime());
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getCurrentTime(Date date) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_ALL);
			return dateFormat.format(date.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	public static String getDateFormat(Date date, String format) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	// 返回字符串的当前时间
	public static String getYearMonthDateYYMMDD() {
		return getCurrentTime(FORMAT_YEARMONDAY);
	}

	// 返回字符串的当前时间
	public static String getYearMonthDateYYYYMMDD() {
		return getCurrentTime(FORMAT_YEARMONDAYDAY);
	}

	/**
	 * @return 当前时间，格式：yyyy-MM-dd
	 * */
	public static Date getYearMonthDay() {
		return getCurrentDate(FORMAT_YEAR_MON_DAY);
	}

	/**
	 * @return 当前时间，格式：yyyy-MM-dd HH:mm:ss
	 * */
	public static Date getCurrentDate(String format) {
		try {
			Calendar date = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.parse(dateFormat.format(date.getTime()));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @return 当前时间，格式：yyyy-MM-dd HH:mm:ss
	 * */
	public static Date getDate(String date, String pattern) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.parse(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date parseString2Date(String date){
		return getDate(date, FORMAT_YEAR_MON_DAY);
	}
	
	
	/**
	 * @return 当前时间，格式：yyyy-MM-dd HH:mm:ss
	 * */
	public static String getCurrentTime(String format) {
		try {
			Calendar date = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date.getTime());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * @return 当前时间，格式：yyyy-MM-dd HH:mm:ss
	 * */
	public static String formatDate(Date date, String format) {
		try {
			Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date.getTime());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * @return long格式的时间数据
	 * */
	public static String getTimeInMillis() {
		Calendar date = Calendar.getInstance();
		return date.getTimeInMillis() + "";
	}

	/**
	 * 获取增加天数以后的日期
	 * */
	public static String getDateAddDays(int days) {
		try {
			Calendar date = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					FORMAT_YEAR_MON_DAY);
			date.add(Calendar.DAY_OF_MONTH, days);
			return dateFormat.format(date.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取增加月数以后的日期
	 * */
	public static String getDateAddMonths(int months) {
		try {
			Calendar date = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					FORMAT_YEAR_MON_DAY);
			date.add(Calendar.MONTH, months);
			return dateFormat.format(date.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	public static String lastToday(String begindate, String enddate) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_YEAR_MON_DAY);
		Calendar cal = Calendar.getInstance();
		Date bdate = null;
		Date edate = null;
		try {
			bdate = df.parse(begindate);
			edate = df.parse(enddate);
			long times = edate.getTime() - bdate.getTime();
			long days = times / (1000 * 60 * 60 * 24);
			cal.setTime(bdate);
			cal.add(Calendar.DAY_OF_YEAR, -(int) days);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return df.format(cal.getTime());
	}

	/**
	 * 获取去年年份
	 * 
	 * @param mdate
	 * @return
	 */
	public static String lastYear(String mdate) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_YEAR_MON_DAY);
		Calendar cal = Calendar.getInstance();
		Date date = null;
		try {
			date = df.parse(mdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(date);
		cal.add(Calendar.YEAR, -1);
		return df.format(cal.getTime());
	}
	/**
	 * 获取指定日期的前一天
	 * */
	public static String lastToday(String mdate) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_YEAR_MON_DAY);
		Calendar cal = Calendar.getInstance();
		Date date = null;
		try {
			date = df.parse(mdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return df.format(cal.getTime());
	}
	/**
	 * 获取上周今天
	 * */
	public static String lastWeekToday(String mdate) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_YEAR_MON_DAY);
		Calendar cal = Calendar.getInstance();
		Date date = null;
		try {
			date = df.parse(mdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(date);
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		return df.format(cal.getTime());
	}

	/**
	 * 获取上月今天
	 * */
	public static String lastMonthToday(String mdate) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_YEAR_MON_DAY);
		Calendar cal = Calendar.getInstance();
		Date date = null;
		try {
			date = df.parse(mdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		return df.format(cal.getTime());
	}

	/**
	 * 获取此周第一天
	 * @param mdate
	 * @return
	 * @throws ParseException
	 */
	public static String weekFirstDay(String mdate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_YEAR_MON_DAY);
		Calendar cal = Calendar.getInstance();
		Date date = df.parse(mdate);
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return df.format(cal.getTime());
	}

	/**
	 * 获取此周最后一天
	 * @param mdate
	 * @return
	 * @throws ParseException
	 */
	public static String weekLastDay(String mdate) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_YEAR_MON_DAY);
		Calendar cal = Calendar.getInstance();
		Date date = null;
		try {
			date = df.parse(mdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		return df.format(cal.getTime());
	}

	/**
	 * 获取月最后一天
	 * 
	 * @param mdate
	 * @return
	 */
	public static String monthEndDay(String mdate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_YEAR_MON_DAY);
		Calendar cal = Calendar.getInstance();
		Date date = df.parse(mdate);
		cal.setTime(date);
		// 某年某月的最后一天
		int endDay = cal.getActualMaximum(Calendar.DATE);
		cal.set(Calendar.DAY_OF_MONTH, endDay);
		return df.format(cal.getTime());
	}

	/**
	 * 获取月第一天
	 * @add YanXue
	 * @param mdate
	 * @return
	 */
	public static String monthStartDay(String mdate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_YEAR_MON_DAY);
		Calendar cal = Calendar.getInstance();
		Date date = df.parse(mdate);
		cal.setTime(date);
		// 某年某月的最后一天
		int endDay = cal.getActualMinimum(Calendar.DATE);
		cal.set(Calendar.DAY_OF_MONTH, endDay);
		return df.format(cal.getTime());
	}

	/**
	 * 取得当月天数
	 * @add YanXue
	 * */
	public static int getCurrentMonthDays() {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, 1);// 把日期设置为当月第一天
			calendar.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
			int maxDate = calendar.get(Calendar.DATE);
			return maxDate;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 根据月份取得当月天数
	 * @add YanXue
	 * 
	 * */
	public static int getCurrentMonthDays(String yearMonth) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(FORMAT_YEAR_MON);
			Date date = df.parse(yearMonth);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DATE, 1);// 把日期设置为当月第一天
			calendar.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
			int maxDate = calendar.get(Calendar.DATE);
			return maxDate;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void main(String[] args) {
		long s = getDaysBetween("2014-12-09", "2014-12-04");
		System.out.println(s);
	}
	/**
	 * 取得今天在当月中是第几天
	 * */
	public static int getCurrentDays() {
		Calendar a = Calendar.getInstance();
		int currDay = a.get(Calendar.DATE);
		return currDay;
	}

	/**
	 * 根据日期获得星期
	 * 
	 * @param curDate
	 * @return
	 */

	public static String getWeek(String curDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(curDate));
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			return dayNames[dayOfWeek - 1];
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 比较两个日期之间的大小
	 * @return true 前者大于后者 false 则不大于
	 * */
	public static boolean getCompareDate(String startDate, String endDate){
		DateFormat df = DateFormat.getDateInstance();
		try {
			if(startDate != null && endDate != null && !"".equals(startDate) && !"".equals(endDate)){
				long date1 = df.parse(startDate).getTime();
				long date2 = df.parse(endDate).getTime();
				if(date1 > date2){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
	}
	/**
	 * 与当前日期比较日期之间的大小
	 * @return true 前者大于后者 false 则不大于
	 * */
	public static boolean getCompareDate(String date){
		return getCompareDate(date, getYearMonthDate());
	}
	
	public static Long getDaysBetween(String startDate, String endDate) {
		DateFormat df = DateFormat.getDateInstance();
		long date1;
		long date2;
		long jiange = 0;// 间隔时间(毫秒)
		try {
			date1 = df.parse(startDate).getTime();
			date2 = df.parse(endDate).getTime();
			if (date1 > date2) {
				jiange = date1 - date2;
			} else {
				jiange = date2 - date1;
			}
		} catch (ParseException e) {
			return 0L;
		}
		return jiange / (60 * 60 * 24 * 1000);
	}
	public static Long getDaysMinusBetween(String startDate, String endDate) {
		long jiange = 0;
		try {
			Date start = parseString2Date(startDate);
			Date end = parseString2Date(endDate);
			jiange = end.getTime() - start.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
		return jiange / (60 * 60 * 24 * 1000);
	}
	
	
	/***********************2014-09-01 Add By zhanglei Start*************************/
	/**
	 * Calendar -> String 全格式日期
	 */
	public static String format(Calendar cal)
	{
		return format(cal.getTime());
	}

	/**
	 * Calendar,String -> String
	 */
	public static String format(Calendar cal, String pattern)
	{
		return format(cal.getTime(), pattern);
	}

	/**
	 * Calendar,DateFormat -> String
	 */
	public static String format(Calendar cal, DateFormat df)
	{
		return format(cal.getTime(), df);
	}

	/**
	 * Date -> String
	 */
	public static String format(Date date)
	{
		return format(date, DF_CN);
	}

	/**
	 * Date,String -> String
	 */
	public static String format(Date date, String pattern)
	{
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return format(date, df);
	}

	public static String format(long ts, DateFormat df)
	{
		return format(new Date(ts), df);
	}
	
	public static String format(long ts, String format,Locale local)
	{
		SimpleDateFormat df = new SimpleDateFormat(format, local);
		return format(new Date(ts), df);
	}

	/**
	 * Date,DateFormat -> String
	 */
	public static String format(Date date, DateFormat df)
	{
		if(date==null)return "";
		return getRealDateFormat(df).format(date);
	}

	/**
	 * @param minsec TS
	 * @return 间隔分钟数
	 */
	public static long getGapMinutes(long minsec)
	{
		return minsec / 60000;
	}

	public static long getGapMinByAddtime(long addtime)
	{
		return getGapMinByAddtime(addtime, System.currentTimeMillis());
	}

	public static long getGapMinByAddtime(long addtime, long current)
	{
		return getGapMinutes(current - addtime);
	}

	public static String getGapMinStirngByAddtime(long addtime, long current)
	{
		return getMinStirngBySubTime(getGapMinutes(current - addtime));
	}

	public static String getMinStirngBySubTime(long min)
	{
		long hour = min / 60;
		long restmin = min % 60;
		return (hour > 0 ? hour + "时" : "") + restmin + "分";
	}

	public static Calendar parseDateString(String str, String format)
	{
		if (str == null)
		{
			return null;
		}
		Date date = null;
		SimpleDateFormat df = new SimpleDateFormat(format);
		try
		{
			date = getRealDateFormat(df).parse(str);
		}
		catch (Exception ex)
		{

		}
		if (date == null)
		{
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * returns the current date 
	 */
	public static String getToday()
	{
		return format(new Date());
	}

	public static Date getYesterday()
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);

		return cal.getTime();
	}

	/**
	 * @return 本月第一天Calendar
	 */
	public static Calendar getFirstDayOfMonth()
	{
		Calendar cal = getNow();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal;
	}
	/**
	 * 获取某月的第一天
	 * @param month
	 * @return Calendar
	 * */
	public static Calendar getFirstDayOfMonth(int month)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal;
	}

	public static Calendar getNow()
	{
		return Calendar.getInstance();
	}

	/**
	 * add some month from the date
	 */
	public static Date addMonth(Date date, int n) throws Exception
	{
		Calendar cal = getNow();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	public static int daysBetween(Date returnDate)
	{
		return daysBetween(null, returnDate);
	}

	public static long tirmDay(Calendar time)
	{// 得到当天的0点时间
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		time.set(Calendar.MILLISECOND, 0);
		return time.getTimeInMillis();
	}

	public static int daysBetween(Date now, Date returnDate)
	{
		if (returnDate == null)
			return 0;

		Calendar cNow = getNow();
		Calendar cReturnDate = getNow();
		if (now != null)
		{
			cNow.setTime(now);
		}
		cReturnDate.setTime(returnDate);
		setTimeToMidnight(cNow);
		setTimeToMidnight(cReturnDate);
		long nowMs = cNow.getTimeInMillis();
		long returnMs = cReturnDate.getTimeInMillis();
		return millisecondsToDays(nowMs - returnMs);
	}

	/**
	 * @param intervalMs
	 * @return TS转成天数
	 */
	private static int millisecondsToDays(long intervalMs)
	{
		return (int) (intervalMs / (1000 * 86400));
	}

	/**
	 * 时间设置成当天凌晨0点
	 * @param calendar
	 */
	private static void setTimeToMidnight(Calendar calendar)
	{
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
	}

	public static String formatDate(Object obj, String format)
	{
		String result = "";
		try
		{
			Date date = (Date) obj;
			result = format(date, format);
		}
		catch (Exception e)
		{

		}
		return result;
	}

	public static String formatDate(Object obj)
	{
		return formatDate(obj, FORMAT_YEAR_MON_DAY);
	}

	public static String getSunday(String date)
	{
		Calendar c = DateUtil.parseDateString(date, "yyyy-MM-dd");
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
		{
			dayofweek = 0;
		}
		c.add(Calendar.DATE, -dayofweek);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}

	public static Calendar getStartTime(Calendar calendar, int interval)
	{
		if (calendar == null)
			return null;
		Calendar fromtime = Calendar.getInstance();
		fromtime.setTimeZone(calendar.getTimeZone());
		fromtime.set(Calendar.MILLISECOND, 0);
		int y = calendar.get(Calendar.YEAR);
		int m = calendar.get(Calendar.MONTH);
		int d = calendar.get(Calendar.DAY_OF_MONTH);
		if (interval == DAILY || interval == HOURLY)
		{
			fromtime.set(y, m, d, 0, 0, 0);
		}
		else if (interval == WEEKLY)
		{
			fromtime.set(y, m, d, 0, 0, 0);
			fromtime.add(Calendar.DATE, 1 + Calendar.SUNDAY - fromtime.get(Calendar.DAY_OF_WEEK));
		}
		else if (interval == MONTHLY)
		{
			fromtime.set(y, m, 1, 0, 0, 0);
		}
		else if (interval == BIWEEKLY)
		{
			fromtime.set(y, m, d, 0, 0, 0);
			fromtime.add(Calendar.WEEK_OF_YEAR, (-1) * (fromtime.get(Calendar.WEEK_OF_YEAR) + 1) % 2);
			fromtime.add(Calendar.DATE, Calendar.SUNDAY - fromtime.get(Calendar.DAY_OF_WEEK));
		}
		else if (interval == YEARLY)
		{
			fromtime.set(y, m, d, 0, 0, 0);
		}
		else if (interval == QUARTLY)
		{
			fromtime.set(y, (m / 3) * 3, 1, 0, 0, 0);
		}
		else if (interval == BIYEARLY)
		{
			fromtime.set(y, (m / 6) * 6, 1, 0, 0, 0);
		}
		return fromtime;
	}

	public static Calendar getEndTime(Calendar calendar, int interval)
	{
		if (calendar == null)
			return null;
		Calendar endtime = Calendar.getInstance();
		endtime.setTimeZone(calendar.getTimeZone());
		endtime.set(Calendar.MILLISECOND, 0);
		int y = calendar.get(Calendar.YEAR);
		int m = calendar.get(Calendar.MONTH);
		int d = calendar.get(Calendar.DAY_OF_MONTH);
		if (interval == DAILY)
		{
			endtime.set(y, m, d, 0, 0, 0);
			endtime.add(Calendar.DAY_OF_MONTH, 1);
		}
		else if (interval == WEEKLY)
		{
			endtime.set(y, m, d, 0, 0, 0);
			endtime.add(Calendar.DATE, 2 + Calendar.SATURDAY - endtime.get(Calendar.DAY_OF_WEEK));
		}
		else if (interval == MONTHLY)
		{
			endtime.set(y, m, 1, 0, 0, 0);
			endtime.add(Calendar.MONTH, 1);
		}
		else if (interval == BIWEEKLY)
		{
			endtime.set(y, m, d, 0, 0, 0);
			endtime.add(Calendar.WEEK_OF_YEAR, endtime.get(Calendar.WEEK_OF_YEAR) % 2);
			endtime.add(Calendar.DATE, 1 + Calendar.SATURDAY - endtime.get(Calendar.DAY_OF_WEEK));
		}
		else if (interval == YEARLY)
		{
			endtime.set(y + 1, m, d, 0, 0, 0);
		}
		else if (interval == QUARTLY)
		{
			if (m / 3 == 3)
			{
				endtime.set(y + 1, 0, 1, 0, 0, 0);
			}
			else
			{
				endtime.set(y, (m / 3 + 1) * 3, 1, 0, 0, 0);
			}
		}
		else if (interval == BIYEARLY)
		{
			if (m / 6 == 1)
			{
				endtime.set(y + 1, 0, 1, 0, 0, 0);
			}
			else
			{
				endtime.set(y, (m / 6 + 1) * 6, 1, 0, 0, 0);
			}
		}
		return endtime;
	}

	public static long getDays(String startdate, String enddate, String format)
	{
		Calendar s1 = DateUtil.parseDateString(startdate, format);
		Calendar s2 = DateUtil.parseDateString(enddate, format);
		if (s1 != null && s2 != null)
		{
			return getDays(s1.getTimeInMillis(), s2.getTimeInMillis());
		}
		return 0;
	}

	public static long getMonthDays(String date, String format)
	{
		Calendar cal = DateUtil.parseDateString(date, format);
		if (cal != null)
		{
			Calendar starttime = DateUtil.getStartTime(cal, DateUtil.MONTHLY);
			Calendar endtime = DateUtil.getEndTime(cal, DateUtil.MONTHLY);
			return getDays(starttime.getTimeInMillis(), endtime.getTimeInMillis());
		}
		return 0;
	}

	/**
	 * @param startdate 
	 * @param enddate
	 * @return
	 */
	public static long getDays(long startdate, long enddate)
	{
		return (enddate - startdate) / day;
	}

	public static String format(Long l, String pattern)
	{
		if(l==null)return "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(l);
		return format(cal.getTime(), pattern);
	}
	
	public static DateFormat getRealDateFormat(DateFormat df)
	{
		return df==null?new SimpleDateFormat(FORMAT_YEAR_MON_DAY,Locale.US):df;
	}

	/**
	 * 两个时间String相差
	 * @param bdate   2004-03-01 10:10:00
	 * @param smdate  2004-09-01 10:10:01
	 * @param formate 格式
	 * @return  中间相差:N天N小时N分钟N秒
	 */
    public static String daysBetween(String bdate,String smdate,String formate) throws ParseException
    {
    	SimpleDateFormat df = new SimpleDateFormat(formate);
		Date now=null;
		Date date=null;
		String result="";
		try {
			 now = df.parse(smdate);
			 date = df.parse(bdate);
			 long l=now.getTime()-date.getTime();
			 long day=l/(24*60*60*1000);
			 long hour=(l/(60*60*1000)-day*24);
			 long min=((l/(60*1000))-day*24*60-hour*60);
			 long s=(l/1000-day*24*60*60-hour*60*60-min*60);

			 result= day==0?"":day+"天";
			 result+=hour==0?"":hour+"小时";
			 result+=min==0?"":min+"分";
			 result+=s==0?"":s+"秒";
		} catch (ParseException e) {
			result="";
		}
		return result;
    }  
    
    /**
     * 获取当前月与之前的N-1个月份
     * @param n 获取月份数
     * @return 'yyyy-MM' 201409
     * */
    public static List<String> getCurrentMonths(int n){
		List<String> months=new ArrayList<String>();
		for(int i=n-1;i>=0;i--){
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH,i-n+1);
		    int month = cal.get(Calendar.MONTH) + 1;
		    int year = cal.get(Calendar.YEAR);
		    months.add(year+""+(month<10?"0"+month:""+month));
		}
		return months;
	}
    
    /**
     * 获取当年的12个月份
     * @param year 年份
     * @return 'yyyy-MM' 2014-01...2014-12
     * */
    public static List<String> get12MonthsInCurrentYear(int year){
		List<String> months = new ArrayList<String>();
		for(int i=1; i<=12; i++){
		    months.add(year+"-"+(i<10?"0"+i:""+i));
		}
		return months;
	}
    
    /**
     * @param year int 如 2014
     * @return Date 获取某年最后一天
     * */
    public static Date getCurrYearLastDay(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		
		return currYearLast;
	}
    
    
    /**  
     * 某个时间点的下个月的第一天  
     * @param day  
     */  
    public static Date firstDayInNextMonth(Date day){   
        Calendar c = Calendar.getInstance();   
        c.setTime(day);   
        c.set(Calendar.MONTH, c.get(Calendar.MONTH)+1);   
        c.set(Calendar.DAY_OF_MONTH, 1);   
        c.set(Calendar.HOUR_OF_DAY, 0);   
        c.set(Calendar.MINUTE, 0);   
        c.set(Calendar.SECOND, 0);   
        return c.getTime();   
    }   
    
    /**  
     * 获取某天在星期中的排序值：  
     * 星期日 -> 星期六 对应为：1 -> 7  
     * @param date  
     */  
    public static int getDateInWeek(Date date) {   
        Calendar c = Calendar.getInstance();   
        c.setTime(date);   
        return c.get(Calendar.DAY_OF_WEEK);   
    } 
    
    /**  
     * 获取指定日期后n天的凌晨  
     * @param date  
     * @param n  
     */  
    public static Date getDateNextDay(Date date, int n) {   
        Calendar c = Calendar.getInstance();   
        c.setTime(date);   
        c.add(Calendar.DATE, n);   
        return c.getTime();   
    }   
       
    /**  
     * 获取下n个月后的日期  
     * @param n 月份偏移量  
     */  
    public static Date getDateNextMonth(int n) {   
        Calendar now = Calendar.getInstance();   
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + n);// 设置时间向前进n个月   
        now.set(Calendar.HOUR_OF_DAY, 0);   
        now.set(Calendar.MINUTE, 0);   
        now.set(Calendar.SECOND, 0);   
        return now.getTime();   
    }   
  
    /**  
     * 获取今天在本月中的号码  
     * @return  
     */  
    public static int getDateOfMoth() {   
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);   
    }   
       
    /**  
     * 本月的所有日期集合  
     * @return  
     */  
    public static List<Date> getDatesInMonth() {   
        List<Date> dates = new ArrayList<Date>();
        
        Calendar c = Calendar.getInstance();   
        c.set(Calendar.DATE, 1);//设置代表的日期为1号   
        // 获得当前月的最大日期数   
        int maxDay = c.getActualMaximum(Calendar.DATE);   
  
        for (int i = 1; i <= maxDay; i++) {   
            c.set(Calendar.DATE, i);   
            dates.add(c.getTime());   
        }   
        
        return dates;   
    } 
    
    /**  
     * 获取某个时间所在的月份  
     * @param date  
     */  
    public static int getMonth(Date date) {   
        Calendar c = Calendar.getInstance();   
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;   
    } 
    
    /**  
     * 获取系统当前时间所在的年份  
     * @return  
     */  
    public static int getYear() {   
        return Calendar.getInstance().get(Calendar.YEAR);   
    }   
  
    /**  
     * 获取本月最后一天  
     * @return  
     */  
    public static Date getMonthEnd() {   
        int length = getDateOfMoth();   
        Calendar c = Calendar.getInstance();   
        c.set(Calendar.DATE, length);   
        c.set(Calendar.HOUR, 24);   
        c.set(Calendar.MINUTE, 0);   
        c.set(Calendar.SECOND, 0);   
        return c.getTime();   
    }   
  
    /**  
     * 获取某个时间所在月份的最后一秒  
     * @param date   
     * @return  
     */  
    public static Date getMonthEnd(Date date){   
        if(date == null){   
            return null;   
        }   
        Calendar start = Calendar.getInstance();   
        start.setTime(date);   
        start.set(Calendar.MONTH, start.get(Calendar.MONTH)+1);   
        start.set(Calendar.DAY_OF_MONTH, 1);   
        start.set(Calendar.HOUR, 0);   
        start.set(Calendar.MINUTE, 0);   
        start.set(Calendar.SECOND, 0);   
        return start.getTime();        
    }   
           
    /**  
     * 获取某个时间所在月份的第一天凌晨  
     * @param date   
     */  
    public static Date getMonthStart(Date date){   
        if(date == null){   
            return null;   
        }   
        Calendar start = Calendar.getInstance();   
        start.setTime(date);   
        start.set(Calendar.DAY_OF_MONTH, 1);   
        start.set(Calendar.HOUR, 0);   
        start.set(Calendar.MINUTE, 0);   
        start.set(Calendar.SECOND, 0);   
        return start.getTime();        
    }   
       
    /**  
     * 获取今天凌晨  
     * @return  
     */  
    public static Date getMorning() {   
        return getMorning(new Date());   
    }   
  
    /**  
     * 获取指定时间当天的凌晨  
     * @param date 给定时间当天的凌晨  
     */  
    public static Date getMorning(Date date) {   
        Calendar c = Calendar.getInstance();   
        c.setTime(date);   
        c.set(Calendar.HOUR_OF_DAY, 0);   
        c.set(Calendar.MINUTE, 0);   
        c.set(Calendar.SECOND, 0);   
        return c.getTime();   
    }   
  
    /**  
     * 获取当前时间N天后的凌晨  
     */  
    public static Date getMorningNextDate(int n) {   
        Calendar now = Calendar.getInstance();   
        now.set(Calendar.DATE, now.get(Calendar.DATE) + n); //设置时间向前进n天   
        now.set(Calendar.HOUR_OF_DAY, 0);   
        now.set(Calendar.MINUTE, 0);   
        now.set(Calendar.SECOND, 0);   
        return now.getTime();   
    }   
       
    /**  
     * 系统当前时间过N个月后的时间  
     * @param nextStep 月份偏移量  
     * @return  
     */  
    public static Date getNextMonth(int nextStep){   
        Calendar c = Calendar.getInstance();   
        int m = c.get(Calendar.MONTH);   
        c.set(Calendar.MONTH, m + nextStep);   
        return c.getTime();   
    }   
  
    /**  
     * 计算给定时间推进一个月对应的时间  
     * @param date 给定时间  
     * @return 某时间过一个月后所在的时间  
     */  
    public static Date getNextMonthToday(Date date){   
        Calendar c = Calendar.getInstance();   
        c.setTime(date);   
        c.set(Calendar.MONTH, c.get(Calendar.MONTH)+1);        
        return c.getTime();   
    }   
       
    /**  
     * 获取给定时间所在的年份  
     * @param date 时间  
     * @return 时间所在的年份  
     */  
    public static int getYear(Date date){   
        Calendar c = Calendar.getInstance();   
        c.setTime(date);   
        return c.get(Calendar.YEAR);   
    }   
  
    /**  
     * 获取某年分的最后一天结束的时间  
     * @param year 年份  
     * @return 指定年份的最后一天结束  
     */  
    public static Date getYearEnd(int year) {   
        Calendar c = Calendar.getInstance();   
        c.set(Calendar.YEAR, year);   
        c.set(Calendar.MONTH,Calendar.DECEMBER);   
        c.set(Calendar.DAY_OF_MONTH, 31);   
        c.set(Calendar.HOUR_OF_DAY, 23);   
        c.set(Calendar.MINUTE, 59);   
        c.set(Calendar.SECOND, 59);   
        return c.getTime();   
    }   
  
    /**  
     * 获取指定年份的第一天凌晨  
     * @param year 年份  
     * @return 指定年份的第一天凌晨  
     */  
    public static Date getYearStart(int year) {   
        Calendar c = Calendar.getInstance();   
        c.set(Calendar.YEAR, year);   
        c.set(Calendar.MONTH, Calendar.JANUARY);   
        c.set(Calendar.DAY_OF_MONTH,1);   
        c.set(Calendar.HOUR_OF_DAY, 0);   
        c.set(Calendar.MINUTE, 0);   
        c.set(Calendar.SECOND, 0);   
        return c.getTime();   
    }   
    
    /**
     * 判断是否当月最后一天
     * @param theDate
     */
    public static boolean month(Calendar theDate){
		boolean flag = false;
		int NowDate = theDate.get(Calendar.DAY_OF_MONTH);//所在月份的天数
        int maxDay = theDate.getActualMaximum(Calendar.DATE);//获得当前月的最大日期数   
        
//		theDate.set(Calendar.DATE, 1);//把日期设置为当月第一天
//		theDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
//		int MaxDate=theDate.get(Calendar.DAY_OF_MONTH); 
        
        if(NowDate == maxDay){
        	flag = true ;
        }
		return flag;
	}

    /**
	 * 获取当前时间后的多少天
	 * @return
     */
	public static  Date addDays(int addDays){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH,addDays);
		return  c.getTime();
	}

	/***********************2014-09-01 Add By zhanglei End***************************/
	
}