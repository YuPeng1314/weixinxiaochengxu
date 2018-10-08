package com.huayu.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author luozehua
 *
 * @time 2016年9月1日-上午9:59:47
 */
public class DateTimeUtils {

	private static final Logger logger = Logger.getLogger(DateTimeUtils.class);
	// 默认日期格式
	public static final String DATE_DEFAULT_FORMAT = "yyyy-MM-dd";

	// 默认时间格式
	public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String TIME_DEFAULT_FORMAT = "HH:mm:ss";

	/**
	 * 
	 * @Description:判断<firstDate>时间点是否在<secondDate>时间点之前 如果此 firstDate
	 *                                                   的时间在参数<secondDate>表示的时间之前，则返回小于
	 *                                                   0 的值
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return
	 * @ReturnType boolean
	 * @author:
	 * @Created 2012 2012-9-20上午08:40:33
	 */
	public static boolean isBefore(Date firstDate, Date secondDate) {
		return compare(firstDate, secondDate) < 0 ? true : false;
	}

	/**
	 * 
	 * @Description:比较两个时间点 如果secondDate表示的时间等于此 firstDate 表示的时间，则返回 0 值； 如果此
	 *                      firstDate 的时间在参数<secondDate>表示的时间之前，则返回小于 0 的值； 如果此
	 *                      firstDate 的时间在参数<secondDate>表示的时间之后，则返回大于 0 的值
	 * @param firstDate
	 * @param secondDate
	 * @ReturnType int
	 * @author:
	 * @Created 2012 2012-9-20上午08:34:33
	 */
	public static int compare(Date firstDate, Date secondDate) {
		Calendar firstCalendar = null;
		/** 使用给定的 Date 设置此 Calendar 的时间。 **/
		if (firstDate != null) {
			firstCalendar = Calendar.getInstance();
			firstCalendar.setTime(firstDate);
		}

		Calendar secondCalendar = null;
		/** 使用给定的 Date 设置此 Calendar 的时间。 **/
		if (firstDate != null) {
			secondCalendar = Calendar.getInstance();
			secondCalendar.setTime(secondDate);
		}

		/**
		 * 比较两个 Calendar 对象表示的时间值（从历元至现在的毫秒偏移量）。 如果参数表示的时间等于此 Calendar 表示的时间，则返回 0 值；
		 * 如果此 Calendar 的时间在参数表示的时间之前，则返回小于 0 的值； 如果此 Calendar 的时间在参数表示的时间之后，则返回大于 0 的值
		 **/
		return firstCalendar.compareTo(secondCalendar);
	}

	public static int compareTime(Date pushDate) {
		if (pushDate != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar now = Calendar.getInstance();
			Date nowDateTemp = now.getTime();
			String nowDate = df.format(nowDateTemp);
			String lastPushDate = df.format(pushDate);
			if (StringUtils.equals(nowDate, lastPushDate)) {
				return 1;
			} else {
				return 0;
			}
		}
		return 0;
	}

	/**
	 * 日期格式化yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static Date formatDate(String date, String format) {
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 判断指定字符串是否符合日期的格式
	 * 
	 * @param dateStr
	 * @return DateTimeUtil.isDateStr("1990-01-32 00:00:00") = false
	 *         DateTimeUtil.isDateStr("1990-01 00:00") = false
	 *         DateTimeUtil.isDateStr("1990-1-1 00:00:00") = true
	 *         DateTimeUtil.isDateStr("1990-01-3") = true
	 *         DateTimeUtil.isDateStr("1990-21-31 00:00:00") = false
	 *         DateTimeUtil.isDateStr("1990年01月3日 00时00分00秒") = false
	 */
	public static boolean isDateStr(final String dateStr, final String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		format.setLenient(false);
		try {
			format.parse(dateStr);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * 将一个字符串解析为一个日期对象。字符串格式由调用方法指定
	 * 
	 * @param dateStr
	 *            要解析的日期字符串
	 * @param pattern
	 *            日期格式的模式字符串
	 * @return 日期对象
	 * 
	 *         不能被解析的日期格式的模式字符串
	 */
	public static Date parseStringToDate(final String dateStr, final String pattern) {
		if (StringUtil.isEmpty(dateStr)) {
			return null;
		}
		if (StringUtil.isEmpty(pattern)) {
			return parseStringToDate(dateStr);
		}
		Date date = null;
		try {
			date = new SimpleDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将一个字符串解析为一个日期对象。字符串格式为：yyyy-MM-dd
	 * 
	 * @param dateStr
	 *            要解析的日期字符串
	 * @return 日期对象
	 * 
	 */
	public static Date parseStringToDate(final String dateStr) {
		if (StringUtil.isEmpty(dateStr)) {
			return null;
		}
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期格式化yyyyMM
	 * 
	 * @param date
	 * @return
	 */
	public static String simple2(Date date) {
		if (null == date) {
			return "";
		}
		return new SimpleDateFormat("yyyyMM").format(date);
	}

	/**
	 * 日期格式化yyyy-MM
	 * 
	 * @param date
	 * @author fzh
	 * @return
	 */
	public static String simpleYM(Date date) {
		if (null == date) {
			return "";
		}
		return new SimpleDateFormat("yyyy-MM").format(date);
	}

	/**
	 * 得到当前月份（1至12）
	 * 
	 * @author fzh
	 * @return
	 */
	public static int getCurrentADDMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 得到当前月份（1至12）
	 * 
	 * @author fzh
	 * @return
	 */
	public static int getCurrentSUBMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) - 1;
	}

	public static String subMonth(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date dt = sdf.parse(date.trim());
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.MONTH, -1);
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);

		return reStr;
	}

	public static String addMonth(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date dt = sdf.parse(date.trim());
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);

		rightNow.add(Calendar.MONTH, +1);
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);

		return reStr;
	}

	/**
	 * 日期格式化yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateFormat(Date date) {
		return new SimpleDateFormat(DATE_DEFAULT_FORMAT).format(date);
	}

	/**
	 * 日期格式化yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateTimeFormat(Date date) {
		return new SimpleDateFormat(DATETIME_DEFAULT_FORMAT).format(date);
	}

	/**
	 * 时间格式化
	 * 
	 * @param date
	 * @return HH:mm:ss
	 */
	public static String getTimeFormat(Date date) {
		return new SimpleDateFormat(TIME_DEFAULT_FORMAT).format(date);
	}

	/**
	 * 日期格式化
	 * 
	 * @param date
	 * @param 格式类型
	 * @return
	 */
	public static String getDateFormat(Date date, String formatStr) {
		if (StringUtils.isNotBlank(formatStr)) {
			return new SimpleDateFormat(formatStr).format(date);
		}
		return null;
	}

	/**
	 * 日期格式化
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateFormat(String date) {
		try {
			return new SimpleDateFormat(DATE_DEFAULT_FORMAT).parse(date);
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 时间格式化
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateTimeFormat(String date) {
		try {
			return new SimpleDateFormat(DATETIME_DEFAULT_FORMAT).parse(date);
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}

	public static Date getDateTimeFormatHH(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH").parse(date);
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 获取当前日期(yyyy-MM-dd)
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNowDate() {
		return DateTimeUtils.getDateFormat(new SimpleDateFormat(DATE_DEFAULT_FORMAT).format(new Date()));
	}

	/**
	 * 获取当前日期时间(yyyy-MM-dd yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNowDateTime() {
		return DateTimeUtils.getDateFormat(new SimpleDateFormat(DATETIME_DEFAULT_FORMAT).format(new Date()));
	}

	public static Integer getCurrentYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}

	public static Integer getCurrentMon() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH) + 1;
	}

	public static Map<String, Date> getMonthInterval() {
		Calendar c = Calendar.getInstance();
		Integer year = c.get(Calendar.YEAR);
		Integer mon = c.get(Calendar.MONTH) + 1;
		String startTime = year + "-" + mon + "-01 00:00:00";
		int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		String endTime = year + "-" + mon + "-" + maxDay + " 23:59:59";
		Map<String, Date> result = new HashMap<String, Date>();
		result.put("startTime", getDateTimeFormat(startTime));
		result.put("endTime", getDateTimeFormat(endTime));
		return result;
	}

	public static Map<String, Date> getWeekInterval() {
		Map<String, Date> result = new HashMap<String, Date>();
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Saturday
		result.put("lastDay", c.getTime());
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Saturday
		result.put("firstDay", c.getTime());
		return result;
	}

	/**
	 * 获取当年的第一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getCurrYearFirst() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取当年的最后一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getCurrYearLast() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}
	
	/**
	 * 
	  * @Title: getStartTime
	  * @Description: 获取当天时间的开始时间
	  * @return Date
	  * @author liuwei
	  * @date 2017年11月16日 下午3:23:27
	 */
	
	public static Date getStartTime() {
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new Date());
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    Date startTime = calendar.getTime();
		return startTime;
	}
	
	/**
	 * 
	  * @Title: getEndTime
	  * @Description: 获取当天时间的结束时间
	  * @return Date
	  * @author liuwei
	  * @date 2017年11月16日 下午3:23:50
	 */
	
	public static Date getEndTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.SECOND, -1);
		Date endTime = calendar.getTime();
		return endTime;
	}

	public static boolean compareDate(String DATE1, String DATE2) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				return true;
			} else if (dt1.getTime() < dt2.getTime()) {
				return false;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new Exception("日期比较失败");
		}
	}

	/**
	 * 时间戳 10位与13位的转换
	 */
	public static String timestamp2Date(String str_num, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (str_num.length() == 13) {
			String date = sdf.format(new Date(toLong(str_num)));
			return date;
		} else {
			String date = sdf.format(new Date(toLong(str_num) * 1000L));
			return date;
		}
	}

	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}
}
