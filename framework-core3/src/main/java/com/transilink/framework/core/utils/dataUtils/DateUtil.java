package com.transilink.framework.core.utils.dataUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

import com.transilink.framework.core.logs.LogEnabled;

/**
 * 提供对日期时间操作的几个日常方法.
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class DateUtil implements LogEnabled {
	public static String DATE_FORMAT = "yyyy-MM-dd";
	public static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final long ONE_DAY = 24 * 3600000;

	private static final long ONE_MINUTE = 60000;

	private static String datePattern = "yyyy-MM-dd";

	private static String timePattern = "HH:mm:ss";

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			datePattern);

	private static SimpleDateFormat datetimeFormat = new SimpleDateFormat(
			datePattern + " " + timePattern);

	/**
	 * 将日期对象转换为字符串，格式为yyyy-MM-dd.
	 *
	 * @param date
	 *            日期.
	 * @return 日期对应的日期字符串.
	 */
	public static String toDateString(Date date) {
		if (date == null) {
			return "";
		}
		return dateFormat.format(date);
	}

	/**
	 * 将日期对象转换为字符串
	 *
	 * @param date
	 *            日期.
	 * @return 日期对应的日期字符串.
	 */
	public static String toDateTimeString(Date date, String dataformat) {
		SimpleDateFormat datetimeFormat = new SimpleDateFormat(dataformat);
		if (date == null) {
			return "";
		}
		return datetimeFormat.format(date);
	}

	/**
	 * 将字符串转换为日期对象，字符串必须符合yyyy-MM-dd的格式.
	 *
	 * @param s
	 *            要转化的字符串.
	 * @return 字符串转换成的日期.如字符串为NULL或空串,返回NULL.
	 */
	public static Date toDate(String s) {
		s = StringUtils.trim(s);
		if (s.length() < 1) {
			return null;
		}
		try {
			if (s.length() <= 10) {
				return dateFormat.parse(s);
			}
			return toDate(Timestamp.valueOf(s));
		} catch (Exception e) {
			log.warn("解析字符串成日期对象时出错", e);
			return null;
		}
	}

	/**
	 * 将日期对象转换为字符串，转换后的格式为yyyy-MM-dd HH:mm:ss.
	 *
	 * @param date
	 *            要转换的日期对象.
	 * @return 字符串,格式为yyyy-MM-dd HH:mm:ss.
	 */
	public static String toDatetimeString(Date date) {
		if (date == null) {
			return null;
		}
		return datetimeFormat.format(date);
	}

	/**
	 * 将日期对象转换为字符串，转换后的格式为yyyy-MM-dd HH:mm:ss.
	 *
	 * @param date
	 *            要转换的日期对象.
	 * @return 字符串,格式为yyyy-MM-dd HH:mm:ss.
	 */
	public static Date toDatetime(String date) {
		if (date == null) {
			return null;
		}
		try {
			return datetimeFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 计算两个日期间相隔的周数
	 *
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public static int computeWeek(Date startDate, Date endDate) {

		int weeks = 0;

		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(startDate);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);

		while (beginCalendar.before(endCalendar)) {

			// 如果开始日期和结束日期在同年、同月且当前月的同一周时结束循环
			if (beginCalendar.get(Calendar.YEAR) == endCalendar
					.get(Calendar.YEAR)
					&& beginCalendar.get(Calendar.MONTH) == endCalendar
							.get(Calendar.MONTH)
					&& beginCalendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) == endCalendar
							.get(Calendar.DAY_OF_WEEK_IN_MONTH)) {
				break;

			} else {

				beginCalendar.add(Calendar.DAY_OF_YEAR, 7);
				weeks += 1;
			}
		}

		return weeks;
	}

	/**
	 * 返回当前系统时间
	 *
	 * @return
	 */
	public static String getCurrDateTime() {
		return toDatetimeString(new Date());

	}

	/**
	 * 获取系统当前时间，待后期可扩展到取数据库时间
	 *
	 * @return 系统当前时间
	 */
	public static String getCurrDate() {
		return toDateString(new Date());

	}

	/**
	 * 将Timestamp转换为日期.
	 *
	 * @param timestamp
	 *            时间戳.
	 * @return 日期对象.如时间戳为NULL,返回NULL.
	 */
	public static Date toDate(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		return new Date(timestamp.getTime());
	}

	/**
	 * 将日期转换为Timestamp.
	 *
	 * @param date
	 *            日期.
	 * @return 时间戳.如日期为NULL,返回NULL.
	 */
	public static Timestamp toTimestamp(Date date) {
		if (date == null) {
			return null;
		}

		return new Timestamp(date.getTime());
	}

	/**
	 * 将时间戳对象转化成字符串.
	 *
	 * @param t
	 *            时间戳对象.
	 * @return 时间戳对应的字符串.如时间戳对象为NULL,返回NULL.
	 */
	public static String toDateString(Timestamp t) {
		if (t == null) {
			return null;
		}
		return toDateString(toDate(t));
	}

	/**
	 * 将Timestamp转换为日期时间字符串.
	 *
	 * @param t
	 *            时间戳对象.
	 * @return Timestamp对应的日期时间字符串.如时间戳对象为NULL,返回NULL.
	 */
	public static String toDatetimeString(Timestamp t) {
		if (t == null) {
			return null;
		}
		return toDatetimeString(toDate(t));
	}

	/**
	 * 将日期字符串转换为Timestamp对象.
	 *
	 * @param s
	 *            日期字符串.
	 * @return 日期时间字符串对应的Timestamp.如字符串对象为NULL,返回NULL.
	 */

	public static Timestamp toTimestamp(String s) {
		return toTimestamp(toDate(s));
	}

	/**
	 * 返回年份，如2004.
	 * */
	public static int getYear(Date d) {

		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.YEAR);
	}

	public static int getYear() {
		return getYear(new Date());
	}

	/**
	 * 返回月份，为1－－ － 12内.
	 * */
	public static int getMonth(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.MONTH) + 1;
	}

	public static int getMonth() {
		return getMonth(new Date());
	}

	/**
	 * 取得季度
	 *
	 * @param d
	 *            日期类型
	 * @return
	 */
	public static final int getQuarter(Date d) {
		return getQuarter(getMonth(d));
	}

	/**
	 * 取得当前的季度
	 *
	 * @return
	 */
	public static final int getQuarter() {
		return getQuarter(getMonth());
	}

	/**
	 * 传递月份,取得季度
	 *
	 * @param num
	 * @return
	 */
	public static final int getQuarter(int num) {
		num = num % 3 == 0 ? num / 3 : (num / 3 + 1);
		return num % 4 == 0 ? 4 : num % 4;

	}

	/**
	 * 返回日期，为1－－ － 31内.
	 * */
	public static int getDay(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得将来的日期.如果timeDiffInMillis > 0,返回将来的时间;否则，返回过去的时间
	 *
	 * @param currDate
	 *            现在日期.
	 * @param timeDiffInMillis
	 *            毫秒级的时间差.
	 * @return 经过 timeDiffInMillis 毫秒后的日期.
	 * */
	public static Date getFutureDate(Date currDate, long timeDiffInMillis) {
		long l = currDate.getTime();

		l += timeDiffInMillis;
		return new Date(l);
	}

	/**
	 * 获得将来的日期.如果timeDiffInMillis > 0,返回将来的时间;否则，返回过去的时间.
	 *
	 * @param currDate
	 *            现在日期.
	 * @param timeDiffInMillis
	 *            毫秒级的时间差.
	 * @return 经过 timeDiffInMillis 毫秒后的日期.
	 * */
	public static Date getFutureDate(String currDate, long timeDiffInMillis) {
		return getFutureDate(toDate(currDate), timeDiffInMillis);
	}

	/**
	 * 获得将来的日期.如果 days > 0,返回将来的时间;否则，返回过去的时间.
	 *
	 * @param currDate
	 *            现在日期.
	 * @param days
	 *            经过的天数.
	 * @return 经过days天后的日期.
	 * */
	public static Date getFutureDate(Date currDate, int days) {
		long l = currDate.getTime();
		long l1 = (long) days * ONE_DAY;

		l += l1;
		return new Date(l);
	}

	/**
	 * 获得将来的日期.如果 days > 0,返回将来的时间;否则，返回过去的时间.
	 *
	 * @param currDate
	 *            现在日期,字符型如2005-05-05 [14:32:10].
	 * @param days
	 *            经过的天数.
	 * @return 经过days天后的日期.
	 * */
	public static Date getFutureDate(String currDate, int days) {
		return getFutureDate(toDate(currDate), days);
	}

	/**
	 * 检查是否在核算期内.
	 *
	 * @param currDate
	 *            当前时间.
	 * @param dateRange
	 *            核算期日期范围.
	 * @return 是否在核算期内.
	 * */
	public static boolean isDateInRange(String currDate, String[] dateRange) {
		if (currDate == null || dateRange == null || dateRange.length < 2) {
			throw new IllegalArgumentException("传入参数非法");
		}

		currDate = getDatePart(currDate);
		return (currDate.compareTo(dateRange[0]) >= 0 && currDate
				.compareTo(dateRange[1]) <= 0);
	}

	/**
	 * 只获取日期部分.获取日期时间型的日期部分.
	 *
	 * @param currDate
	 *            日期[时间]型的字串.
	 * @return 日期部分的字串.
	 * */
	public static String getDatePart(String currDate) {
		if (currDate != null && currDate.length() > 10) {
			return currDate.substring(0, 10);
		}

		return currDate;
	}

	/**
	 * 计算两天的相差天数,不足一天按一天算.
	 *
	 * @param stopDate
	 *            结束日期.
	 * @param startDate
	 *            开始日期.
	 * @return 相差天数 = 结束日期 - 开始日期.
	 * */
	public static int getDateDiff(String stopDate, String startDate) {
		long t2 = toDate(stopDate).getTime();
		long t1 = toDate(startDate).getTime();

		int diff = (int) ((t2 - t1) / ONE_DAY); // 相差天数
		// 如有剩余时间，不足一天算一天
		diff += (t2 > (t1 + diff * ONE_DAY) ? 1 : 0);
		return diff;
	}

	/**
	 * 计算两天的相差分钟,不足一分钟按一分钟算.
	 *
	 * @param stopDate
	 *            结束日期.
	 * @param startDate
	 *            开始日期.
	 * @return 相差分钟数 = 结束日期 - 开始日期.
	 * */
	public static int getMinutesDiff(String stopDate, String startDate) {
		long t2 = toDate(stopDate).getTime();
		long t1 = toDate(startDate).getTime();

		int diff = (int) ((t2 - t1) / ONE_MINUTE); // 相差分钟数
		// 如有剩余时间，不足一天算一天
		diff += (t2 > (t1 + diff * ONE_MINUTE) ? 1 : 0);
		return diff;
	}

	/**
	 * 判断两个日期是否在同一周
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setFirstDayOfWeek(Calendar.MONDAY);
		cal2.setFirstDayOfWeek(Calendar.MONDAY);
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	/**
	 *
	 * 按年获取周序号
	 *
	 * @param currDate
	 * @return
	 */
	public static int getSeqWeekByYear(Date currDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(currDate);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		int weekNo = c.get(Calendar.WEEK_OF_YEAR);

		Calendar lastDate = Calendar.getInstance();

		if (weekNo == 1) {
			// 获取周五时间
			lastDate.setTime(DateUtil.toDate(getFriday(c.getTime())));
			if (c.get(Calendar.YEAR) != lastDate.get(Calendar.YEAR)) {
				lastDate.setTime(DateUtil.toDate(getMonday(c.getTime())));
				lastDate.add(Calendar.DATE, -1);
				lastDate.setFirstDayOfWeek(Calendar.MONDAY);
				weekNo = lastDate.get(Calendar.WEEK_OF_YEAR) + 1;
			}
		}
		return weekNo;
	}

	/**
	 *
	 * 按月获取周序号
	 *
	 * @param currDate
	 * @return
	 */
	public static int getSeqWeekByMonth(Date currDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(currDate);
		c.setFirstDayOfWeek(Calendar.MONDAY);

		return c.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 *
	 * 获取周一的日期
	 *
	 * @param date
	 * @return
	 */
	public static String getMonday(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	/**
	 *
	 * 获取周一的日期
	 *
	 * @param date
	 * @return
	 */
	public static String getMonday(String date) {
		Calendar c = Calendar.getInstance();
		c.setTime(toDate(date));
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	/**
	 * 获得周五的日期
	 */
	public static String getFriday(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	// 当前日期前几天或者后几天的日期
	public static String afterNDay(int n) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		c.setTime(new Date());
		c.add(Calendar.DATE, n);
		Date d2 = c.getTime();
		String s = df.format(d2);
		return s;
	}

	/**
	 * 判断某年是否为闰年
	 *
	 * @return boolean
	 *
	 */
	public static boolean isLeapYear(int yearNum) {
		boolean isLeep = false;
		/** 判断是否为闰年，赋值给一标识符flag */
		if ((yearNum % 4 == 0) && (yearNum % 100 != 0)) {
			isLeep = true;
		} else if (yearNum % 400 == 0) {
			isLeep = true;
		} else {
			isLeep = false;
		}
		return isLeep;
	}

	/**
	 * 计算某年某周的开始日期
	 *
	 * @return interger
	 *
	 */
	public static String getYearWeekFirstDay(int yearNum, int weekNum) {

		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.YEAR, yearNum);
		cal.set(Calendar.WEEK_OF_YEAR, weekNum);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// 分别取得当前日期的年、月、日
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String tempDay = Integer.toString(cal.get(Calendar.DATE));
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return SetDateFormat(tempDate, "yyyy-MM-dd");
	}

	/**
	 * @return String
	 *
	 */
	public static String SetDateFormat(String myDate, String strFormat) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
			String sDate = sdf.format(sdf.parse(myDate));
			return sDate;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 计算某年某周的结束日期
	 *
	 * @return interger
	 *
	 */
	public String getYearWeekEndDay(int yearNum, int weekNum) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, yearNum);
		cal.set(Calendar.WEEK_OF_YEAR, weekNum + 1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		// 分别取得当前日期的年、月、日
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String tempDay = Integer.toString(cal.get(Calendar.DATE));
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return SetDateFormat(tempDate, "yyyy-MM-dd");
	}

	/**
	 * 计算某年某月的开始日期
	 *
	 * @return interger
	 *
	 */
	public String getYearMonthFirstDay(int yearNum, int monthNum) {

		// 分别取得当前日期的年、月、日
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(monthNum);
		String tempDay = "1";
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return SetDateFormat(tempDate, "yyyy-MM-dd");
	}

	/**
	 * 计算某年某月的结束日期
	 *
	 * @return interger
	 *
	 */
	public static String getYearMonthEndDay(int yearNum, int monthNum) {

		// 分别取得当前日期的年、月、日
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(monthNum);
		String tempDay = "31";
		if (tempMonth.equals("1") || tempMonth.equals("3")
				|| tempMonth.equals("5") || tempMonth.equals("7")
				|| tempMonth.equals("8") || tempMonth.equals("10")
				|| tempMonth.equals("12")) {
			tempDay = "31";
		}
		if (tempMonth.equals("4") || tempMonth.equals("6")
				|| tempMonth.equals("9") || tempMonth.equals("11")) {
			tempDay = "30";
		}
		if (tempMonth.equals("2")) {
			if (isLeapYear(yearNum)) {
				tempDay = "29";
			} else {
				tempDay = "28";
			}
		}

		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return tempDate;

	}

	/**
	 * 根据参数，获取相对日期
	 *
	 * @param date
	 * @param flag
	 * @param intervals
	 * @return
	 */
	public static Date getRelativeDate(Date date, char flag, int intervals) {
		Date currDate = null;
		if (date != null) {
			Calendar newDate;
			(newDate = Calendar.getInstance()).setTime(date);
			switch (flag) {
			case 'y':
				newDate.add(Calendar.YEAR, intervals);
				break;
			case 'M':
				newDate.add(Calendar.MONTH, intervals);
				break;
			case 'd':
				newDate.add(Calendar.DATE, intervals);
				break;
			case 'w':
				newDate.add(Calendar.WEEK_OF_YEAR, intervals);
				break;
			case 'h':
				newDate.add(Calendar.HOUR, intervals);
				break;
			case 'm':
				newDate.add(Calendar.MINUTE, intervals);
				break;
			case 's':
				newDate.add(Calendar.SECOND, intervals);
				break;
			case 'S':
				newDate.add(Calendar.MILLISECOND, intervals);
			}
			currDate = newDate.getTime();
		}
		return currDate;
	}

	/**
	 * 获取当前日期
	 *
	 * @return
	 */
	public static Date getCurrDay() {
		// TODO Auto-generated method stub
		return new Date();
	}

	/**
	 * 获取下一周的第一天
	 *
	 * @return
	 */
	public static String getAfterWeekFirst() {
		return getMonday(afterNDay(7));
	}

	/**
	 * 获取下一月的第一天
	 *
	 * @param date
	 * @param afterNum
	 * @return
	 */
	public static String getAfterMonthFirst(String date, int afterNum) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(toDate(date));
		cal.add(Calendar.MONTH, afterNum);
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return dateFormat.format(cal.getTime());
	}

	/**
	 * 获取周日
	 *
	 * @param date
	 * @param afterNum
	 * @return
	 */
	public static String getSundayOfWeek(String date) {
		Calendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(toDate(date));
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6);
		return dateFormat.format(cal.getTime());
	}

	/**
	 * 获取下一季度的第一天
	 *
	 * @param date
	 * @param afterNum
	 * @return
	 */
	public static String getAfterQuarterFirst(String date, int afterNum) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(toDate(date));
		int currentMonth = cal.get(Calendar.MONTH) + 1;
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		if (currentMonth >= 1 && currentMonth <= 3)
			cal.set(Calendar.MONTH, 0);
		else if (currentMonth >= 4 && currentMonth <= 6)
			cal.set(Calendar.MONTH, 3);
		else if (currentMonth >= 7 && currentMonth <= 9)
			cal.set(Calendar.MONTH, 6);
		else if (currentMonth >= 10 && currentMonth <= 12)
			cal.set(Calendar.MONTH, 9);
		cal.add(Calendar.MONTH, afterNum * 3);
		return dateFormat.format(cal.getTime());
	}

	public static void main(String[] args) {
		System.out.println(getAfterWeekFirst());
	}

}
