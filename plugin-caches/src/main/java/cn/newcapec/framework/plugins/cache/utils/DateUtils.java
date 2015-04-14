package cn.newcapec.framework.plugins.cache.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
/**
 * 日期工具
 * 
 * author sntey (yanghang)
 * 
 */
public class DateUtils{
	
	public static final String YEAR="年";
	public static final String MONTH="月";
	public static final String DAY="日";
	public static final String SEASON="季";
	
	/**一年分两季,春季和秋季**/
	public static final int SEASON_TYPE_2=2;
	
	private DateUtils(){}
	
	public static enum DateIntervalType {
		YEAR,
		MONTH,
		WEEK,
		DAY,
		HOUR,
		MINUTE,
		SECOND
	}
	
	private static Log log = LogFactory.getLog(DateUtils.class);
	
	/**1秒的毫秒�?*/
	public static final int ONE_SECOND = 1000;
	/**1分钟的毫秒�??*/
	public static final int ONE_MINUTE = 60000;
	/**1小时的毫秒�??*/
	public static final int ONE_HOUR = 3600000;
	/**1天的毫秒�?*/
	public static final long ONE_DAY = 86400000L;
	/**1年的毫秒�?*/
	public static final long ONE_WEEK = 604800000L;
	
	/**  yyyy-MM-dd */
	public static final String DEFALUT_DATE_PATTERN="yyyy-MM-dd";
	/**  yyyy-MM-dd HH:mm:ss */
	public static final String DEFALUT_DATETIME_PATTERN="yyyy-MM-dd HH:mm:ss";
	/**  yyyy-MM-dd-HH-mm-ss */
	public static final String DEFALUT_DATETIME_PATTERN_FORMAT_TWO="yyyy-MM-dd-HH-mm-ss";
	/**YYYYMMDD**/
	public static final String DEFALUT_DATETIME_PATTERN_ONE="yyyyMMdd";
	/**YYYYMMDDHHMMSS**/
	public static final String DEFALUT_DATETIME_PATTERN_THREE="yyyyMMddHHmmss";
	/** 得到当前时间,返回值为java.util.Date类型 */
	public static Date getNow(){
		return new Date();
	}
	
	/**
	 * 按指定格式获得当前日�?
	 * 返回�?个字符串 yyyyMMddHHmmss 
	 * 		比如: 2007�?10�?15�? 晚上9�?30�?20�?
	 * 		就返�?  20071015213020
	 */
	public static String formatNow(String pattern){
		return format(getNow(),pattern);
	}
	/**
	 * 获得当前日期 格式�? yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String formatNow(){
		return  formatNow(DEFALUT_DATETIME_PATTERN);
	}
	
	public static String format(String date,String pattern){
		Date d = parse(date);
		
		return format(d, pattern);
		
	}
	
	/** 获得当前的年�? */
	public static int getNowYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}
	/**
	 * 得到当前年的第一天
	 * **/
	public static String getNowYearFirstDay(){
		return getNowYear()+"-01-01";
	}
	/** 获得当前的月�? */
	public static int getNowMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}
	/****/
	public static String getNowMonthSeason(int type){
		String season = "";
		if(SEASON_TYPE_2 == type){
			int m = DateUtils.getNowMonth();
			if(m>=2 && m<=7){
				season = "1";
			}else{
				season = "2";
			}
		}
		return season;
	}
	/** 以12点为分界线，大于12点视为下午，小于视为上午**/
	public static String getNowTimeNoon(){
		Calendar c = Calendar.getInstance();
		
		int n = c.get(Calendar.HOUR_OF_DAY);
		
		if(n<12){
			return "1";
		}
		return "2";
	}
	/** 获得当前的季�?**/
	public static int getNowSeason(){
		return getNowMonth() / 4 + 1;
	}

	/** 获得当前的日 */
	public static int getNowDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/** 得到当前日期�? 凌晨0�?0�?0�? */
	public static Date getBeginOfToday(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/** 得到当前日期�? 凌晨23�?59�?59�? */
	public static Date getEndOfToday(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}
	
	/** 得到某天的开始时�? 凌晨0�?0�?0�? */
	public static Date getBeginOfDay(Date date){
		if(date==null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date ds = new Date(cal.getTimeInMillis());
		return ds;
	}
	
	/** 得到某天的结束时�? 凌晨23�?59�?59�? */
	public static Date getEndOfDay(Date date) {
		if(date==null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		Date ds = new Date(cal.getTimeInMillis());
		return ds;
	}
	
	/** 得到下一�? */
	public static Date getNextDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
		return cal.getTime();
	}
	
	
	
	/** 得到昨天 */
	public static Date getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginOfToday());
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
		return cal.getTime();
	}
	
	/**
	 * 得到以当前日期为标准的X�?
	 * X 为正，则是当前日期向后某�?
	 * X 为负，则是当前日期向前某�?
	 * **/
	public static Date getXDayByNow(int x){
		Calendar cal = Calendar.getInstance();
		cal.setTime(getEndOfToday());
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + x);
		return cal.getTime();
	}

	/** 得到明天 */
	public static Date getTomorrow() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginOfToday());
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
		return cal.getTime();
	}
	/**
	 * 返回 date 里的�?
	 * @param date
	 * @return
	 */
	public static int getYearOfDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	/**
	 * 返回 date 里的�?
	 * @param date
	 * @return
	 */
	public static int getMonthOfDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}
	
	/** 得到当前月的第一�? */
	public static Date getFirstDayOfCurMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal
				.getActualMinimum(Calendar.DAY_OF_MONTH));
		return getBeginOfDay(cal.getTime());
	}

	/** 得到当前月的�?后一�? */
	public static Date getEndDayOfCurMonth(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		return getBeginOfDay(cal.getTime());
	}
	
	/** 得到两个日期中较大�?? */
	public static Date getMax(Date date1, Date date2){
		if (date1 == null)
			return date2;
		if (date2 == null)
			return date1;
		if (date1.after(date2))
			return date1;
		return date2;
	}

	/** 得到两个日期中较小�??  */
	public static Date getMin(Date date1, Date date2){
		if (date1 == null)
			return date2;
		if (date2 == null)
			return date1;
		if (date1.after(date2))
			return date2;
		return date1;
	}
	/**得到星期几**/
	public static int getWeekOfDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int xq = cal.get(Calendar.DAY_OF_WEEK)-1;
		return xq==0?7:xq;
	}
	
//	public static String getWeekOfDateCn(Date date){
//		int week = getWeekOfDate(date);
//		String r = "";
//		switch(week){
//		case 1 :  r = "一" ; break;
//		case 2 :  r = "二" ; break;
//		case 3 :  r = "三" ; break;
//		case 4 :  r = "四" ; break;
//		case 5 :  r = "五" ; break;
//		case 6 :  r = "六" ; break;
//		case 0 :  r = "日" ; break;
//		default: r= "日" ; 
//		}
//		return r;
//	}
	/**
	 * 判断 first 与second两个日期。
	 * 如果first在second之前，则true
	 * **/
	public static boolean compareFirstMax(String first,String second){
		if(StringUtils.notText(first)){
			return false;
		}
		if(StringUtils.notText(second)){
			return true;
		}
		Date d1 = parse(first);
		Date d2 = parse(second);
		return !d1.after(d2);
	}
	/**
	 * 判断 first 与second两个日期。  first是否在second之后。
	 * 如: 2012-12-12 就在 2012-12-11之后
	 * **/
	public static boolean after(String first,String second){
		Date d1 = parse(first);
		Date d2 = parse(second);
		return d1.after(d2);
	}
	
	/** 得到year年month月的天数 */
	public static int daysOfMonth(int year, int month){
		Assert.isTrue(month>=0 && month <=12,"月份["+ month +"]错误!");
		switch (month) {
		case 1:	case 3:	case 5:	case 7:
		case 8:	case 10:case 12:
			return 31;
		case 4:	case 6:	case 9:	case 11:
			return 30;
		case 2:
			if (isLeap(year))
				return 29;
			return 28;
		default:
			return 0;
		}
	}
	
	/**  判断�?年是否是闰年 */
	public static boolean isLeap(int year) {
		boolean divBy4 = (year % 4 == 0);
		boolean divBy100 = (year % 100 == 0);
		boolean divBy400 = (year % 400 == 0);
		return divBy4 && (!divBy100 || divBy400);
	}
	
	/** 判断�?传的 date 是否能转换成 Date */ 
	public static boolean isDate(String date) {
		try {
			parse(date);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/** 在指定日期基�?上加若干�? */
	public static Date addYear(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, i + year);
		return calendar.getTime();
	}
	
	/** 在指定日期基�?上加若干�? */
	public static Date addMonth(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		month += i;
		int deltaY = month / 12;
		month %= 12;
		calendar.set(Calendar.MONTH, month);
		if (deltaY != 0) {
			int year = calendar.get(Calendar.YEAR);
			calendar.set(Calendar.YEAR, deltaY + year);
		}
		return calendar.getTime();
	}
	
	/** 在指定日期基�?上加若干�? */
	public static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(5, day);
		Date d = calendar.getTime();
		return d;
	}

	/** 在指定日期基�?上加若干小时 */
	public static Date addHour(Date date, long l) {
		long oldD = date.getTime();
		long deltaD = l * 60L * 60L * 1000L;
		long newD = oldD + deltaD;
		Date d = new Date(newD);
		return d;
	}

	/** 在指定日期基�?上加若干分钟 */
	public static Date addMinute(Date date, long l) {
		long oldD = date.getTime();
		long deltaD = l * 60L * 1000L;
		long newD = oldD + deltaD;
		Date d = new Date(newD);
		return d;
	}
	
	/** 在指定日期基�?上加若干�? */
	public static Date addSecond(Date date, long l) {
		long oldD = date.getTime();
		long deltaD = l * 1000L;
		long newD = oldD + deltaD;
		Date d = new Date(newD);
		return d;
	}
	
	/** 在指定日期基�?上加若毫�? */
	public static Date addMilliSecond(Date date, long l) {
		long oldD = date.getTime();
		long deltaD = l;
		long newD = oldD + deltaD;
		Date d = new Date(newD);
		return d;
	}
	
	/**
	 * 在指定日期基�?上加上当前的 时分�?
	 * @param date
	 */
	public static Date addNowHourMinSec(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		Calendar now = Calendar.getInstance();
		
		calendar.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, now.get(Calendar.SECOND));
		
		return new Date(calendar.getTimeInMillis());
	}
	
	
	/**
	 * �? java.util.Date 转换�? String 使用默认的格�? (yyyy-MM-dd HH:mm:ss)
	 * @param aDate �?要转换的日期
	 * @return
	 * @see DEFALUT_DATETIME_PATTERN
	 */
	public static String format(Date aDate){
		return format(aDate,DEFALUT_DATETIME_PATTERN);
	}
	
	/**
	 * �? java.util.Date 转换�? String 使用自定义的格式
	 * @param aDate �?要转换的日期
	 * @param pattern 样式
	 * @return
	 */
	public static String format(Date aDate,String pattern){
		if(aDate == null) return "";
		 SimpleDateFormat df = new SimpleDateFormat(pattern);
		 return df.format(aDate);
	}
	
	/**
	 * �? String 转换�? java.util.Date 使用默认的格�? (yyyy-MM-dd)
	 * @param strDate �?要转换的日期
	 * @return
	 * @throws ParseException 
	 * @see DEFALUT_DATE_PATTERN
	 */
	public static Date parse(String strDate){
		return parse(strDate, new String[]{DEFALUT_DATE_PATTERN, DEFALUT_DATETIME_PATTERN});
	}
	
	/**
	 * �? String 转换�? java.util.Date 使用自定义的格式 
	 * @param strDate �?要转换的日期
	 * @aMask 自定义的格式
	 * @return Date 
	 * @throws ParseException 
	 * @see DEFALUT_DATETIME_PATTERN
	 */
	public static Date parse(String strDate, String aMask){
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		try {
			date = df.parse(strDate);
		} catch (Exception e) {
//			log.error("转换日期错误: "+e.getMessage());
			return null;
		}

		return (date);
	}

	public static Date parseAddBeginOfDay(String date){
		return parse(date+" 00:00:00",DateUtils.DEFALUT_DATETIME_PATTERN);
	}
	public static Date parseAddEndOfDay(String date){
		return parse(date+" 23:59:59",DateUtils.DEFALUT_DATETIME_PATTERN);
	}
	public static Date parse(String strDate, String[] aMasks){
		if(StringUtils.notText(strDate)) return null;
		for(String aMask:aMasks){
			Date date = parse(strDate, aMask);
			if(date!=null){
				return date;
			}
		}
		return null;
	}
	public static String getCurrentZhDate(){
		StringBuffer sb=new StringBuffer();
		sb.append(getNowYear());
		sb.append(YEAR);
		sb.append(getNowMonth());
		sb.append(MONTH);
		sb.append(getNowDayOfMonth());
		sb.append(DAY);
		
		return sb.toString();
	}
	/**
	 * 得到两个日期之间的时间间�?,根据间隔参数
	 * 
	 * @param interval
	 *            间隔参数 计算数量参数( �?,�?,�?,�? ...). 取�??
	 *            DateIntervalTypeEnum.YEAR,DateIntervalTypeEnum.MONTH, ...
	 * @param dDate1
	 * @param dDate2
	 * @return dDate1-dDate2 之间的时间间�?. 如果 dDate1 > dDate2,返回负数
	 */
	public static long dateDiff(DateIntervalType interval, Date dDate1,
			Date dDate2) {
		int desiredField = 0;
		int coef = 1;
		Date date1;
		Date date2;
		if (dDate1.getTime() > dDate2.getTime()) {
			coef = -1;
			date1 = dDate2;
			date2 = dDate1;
		} else {
			date1 = dDate1;
			date2 = dDate2;
		}
		int field;
		if (interval.ordinal() == DateIntervalType.YEAR.ordinal())
			field = Calendar.YEAR;
		else if (interval.ordinal() == (DateIntervalType.MONTH.ordinal()))
			field = Calendar.MONTH;
		else if (interval.ordinal() == (DateIntervalType.DAY.ordinal()))
			field = Calendar.DAY_OF_MONTH;
		else if (interval.ordinal() == (DateIntervalType.WEEK.ordinal()))
			field = Calendar.WEEK_OF_MONTH;
		else if (interval.ordinal() == (DateIntervalType.HOUR.ordinal())) {
			field = Calendar.DATE;
			desiredField = Calendar.HOUR_OF_DAY;
		} else if (interval.ordinal() == (DateIntervalType.MINUTE.ordinal())) {
			field = Calendar.DATE;
			desiredField = Calendar.MINUTE;
		} else if (interval.ordinal() == (DateIntervalType.SECOND.ordinal())) {
			field = Calendar.DATE;
			desiredField = Calendar.SECOND;
		} else {
			throw new IllegalArgumentException("unkown interval!");
		}
		Calendar calTmp = Calendar.getInstance();
		calTmp.setTime(date1);
		long nbOccurence = 0L;
		calTmp.add(field, 1);
		Date dateTemp = calTmp.getTime();
		while (dateTemp.getTime() <= date2.getTime()) {
			calTmp.add(field, 1);
			dateTemp = calTmp.getTime();
			nbOccurence++;
		}
		if (desiredField == Calendar.HOUR_OF_DAY
				|| desiredField == Calendar.MINUTE
				|| desiredField == Calendar.SECOND) {
			calTmp.setTime(date1);
			calTmp.add(field, (int) nbOccurence);
			dateTemp = calTmp.getTime();
			switch (desiredField) {
			case Calendar.HOUR_OF_DAY:
				nbOccurence *= 24L;
				break;
			case Calendar.MINUTE:
				nbOccurence = nbOccurence * 24L * 60L;
				break;
			case Calendar.SECOND:
				nbOccurence = nbOccurence * 24L * 60L * 60L;
				break;
			}
			calTmp.add(desiredField, 1);
			dateTemp = calTmp.getTime();
			while (dateTemp.getTime() <= date2.getTime()) {
				calTmp.add(desiredField, 1);
				dateTemp = calTmp.getTime();
				nbOccurence++;
			}
		}
		return nbOccurence * coef;
	}
	
	
	
	
	
	
	
	
	//===============以下用于测试===============
	public static void main(String[] args) throws Exception {
		
//		System.out.println(getWeekOfDate(DateUtils.parse("2012-10-28")));
		
		
//		System.out.println(after("2012-10-12", "2012-10-11"));
		
//		long l = dateDiff(DateIntervalType.DAY, DateUtils.parse("2012-10-09"), DateUtils.parse("2012-10-09"));
//		
//		System.out.println(l);
//		testMain();
		
//		System.out.println(getXDayByNow(-7));
//		String ps = "2010-10-10";
//		
//		System.out.println(DateUtils.parse(ps, DateUtils.DEFALUT_DATETIME_PATTERN));
	}

	private static void testMain() throws Exception{
		//System.out.println(getNow());
		//System.out.println(dateDiff(DateIntervalTypeEnum.HOUR, getNow(),getBeginOfToday()));
		//System.out.println(convertStringToDate("2007-10-9"));
//		System.out.println(format(getNow(),"yyyyMMddHHmmss"));
		System.out.println(getNowMonth());
		
	}

	
	
	
}
