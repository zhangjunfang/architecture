package cn.newcapec.framework.plugins.cache.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * 数字的格式的操作
 */
public abstract class NumberUtils 
{
	private final static Log log = LogFactory.getLog(NumberUtils.class);
	
//	public static void main(String[] args){
////		System.out.println(formatIntByLengthIfNeed(1,4));
////		System.out.println("1000000.2555=="+NumberUtils.format(1000000.2555,2,3));
////		System.out.println("1000000.25=="+NumberUtils.defaultFormat("1000000.25558"));
////		System.out.println("1000000.25=="+NumberUtils.format("1000000.55558",NumberUtils.INT_FORMAT));
////		System.out.println("1000000.25=="+NumberUtils.format("1000000.25558",NumberUtils.NOCOMMA_INT_FORMAT));
////		System.out.println("1000000.2555=="+NumberUtils.format("1000000.2555", "#0.00"));
////		System.out.println("1000000.2555=="+NumberUtils.format("1000000.2555", "#"));
//	}
	
	/**
	 * <br>格式是	 1000000.00
	 * <br>整数部分无分隔, 小数部分位数4位
	 */
	public static final String S0C4_FORMAT="#0.0000";
	
	/**
	 * <br>格式是 10,000,000.0000
	 * <br>整数部分3位分隔, 小数部分位数4位
	 */
	public static final String S3C4_FORMAT="#,##0.0000";

	/**
	 * <br>格式是 1000000
	 * <br>整数部分无分隔, 无小数部分
	 */
	public static final String S0C0_FORMAT="#0";
	
	/**
	 * <br>格式是 10,000,000
	 * <br>整数部分3位分隔, 无小数部分
	 */
	public static final String S3C0_FORMAT="#,##0";
	
	/**
	 * 如果数据有小数，保留两位小数。如无小数就没有小数
	 * **/
	public static final String SXCX4_FORMAT="0.####";
	
	
	public static final int N_1000=0x3E8;
	public static final int N_60=0x3C;
	public static final int N_24=0x18;
	public static final int N_N1=0xFFFFFFFF;

	//===========format begin==========================

	/** 返回按指定格式 格式化的字符串 */
	public static String format(Object obj,String pattern){
		if(obj==null || !NumberUtils.canParseDouble(obj)){	return "";}
		DecimalFormat df=new DecimalFormat(pattern);
		return df.format(NumberUtils.toDouble(obj));
	}
	
	
	/** 整数部分3位分隔, 小数部分位数4位 */
	public static String defaultFormat(double dbl){
		return format(dbl, S3C4_FORMAT);
	}
	
	/** 整数部分3位分隔, 无小数部分 */
	public static String defaultFormat(int dbl){
		return format(dbl, S3C0_FORMAT);
	}
	
	/** 整数部分无分隔, 小数部分位数4位  ZhouBo 2011-08-01*/
	public static double defaultFormatSOC4(double dbl){
		return toDouble(format(dbl, S0C4_FORMAT));
	}

	public static String nullSafeFormat(Object obj,String pattern){
		if(obj==null){	return "";}
		return format(obj, pattern);
	}
	
	/**
	 * @param obj
	 * @param comma			几位分割
	 * @param decimalNum	小数位数
	 * */
	public static String format(Object obj,int comma,int decimalNum){
		if(obj == null){	return "";}
		
		StringBuilder pattern = new StringBuilder();
		if(decimalNum >= 0){
			pattern.append('0');
			for(int i=0;i<decimalNum;i++){
				if(pattern.length()==1){
					pattern.append('.');
				}
				pattern.append('0');
			}
		}
		if(comma > 0){
			pattern.insert(0, "#,");
			for(int i=0;i<comma-1;i++){	pattern.insert(2, '#');}
		}
		
		return format(obj.toString(),pattern.toString());
	}

	/**
	 * 将int转成指定位数(如果需要转)的String
	 * 如果int本身位数大于所需, 则返回int
	 * */
	public static String formatIntByLengthIfNeed(int int4format, int needLength) {
		Assert.isTrue(needLength>0);
		
		int realLength = Integer.toString(int4format).length();
		
		if(realLength>needLength){	return Integer.toString(int4format);}
		
		StringBuilder ret = new StringBuilder(30);
		for(int i=0;i<needLength-realLength;i++){
			ret.append('0');
		}
		ret.append(int4format);
		return ret.toString();
	}
	
	//===========format end==========================

	public static Double toDouble(Object obj) {
		return NumberUtils.toDouble(obj, false);
	}
	
	public static Double toDoubleNull2Zero(Object obj) {
		Double d = NumberUtils.toDouble(obj, false);
		return d==null?0D:d;
	}
	public static Float toFloat(Object obj){
		Float f = NumberUtils.toFloat(obj, false);
		return f == null ?0f:f;
	}
	
	public static Float toFloat(Object obj, boolean isThrowException) {
		Float d = null;
		try {
			if(null == obj){
				return 0f;
			}
			String str = obj.toString();
			str = str.replaceAll(",|%|#", "");
			d = new Float(str);
		} catch (Exception e) {
			if (isThrowException){
				throw new IllegalArgumentException("["
						+ ObjectUtils.nullSafeToString(obj)+"]转换Float错误!",e);
			}
		}
		return d;
	}
	public static Double toDouble(Object obj, boolean isThrowException) {
		Double d = null;
		try {
			if(null == obj){
				return 0D;
			}
			String str = obj.toString();
			str = str.replaceAll(",|%|#", "");
			d = new Double(str);
		} catch (Exception e) {
			if (isThrowException){
				throw new IllegalArgumentException("["
						+ ObjectUtils.nullSafeToString(obj)+"]转换Double错误!",e);
			}
		}
		return d;
	}

	public static Double toDouble(Object obj, Double defaultValue) {
		Double d = null;
		try {
			String str = obj.toString();
			str = StringUtils.replace(str, ",", "");
			d = new Double(str);
		} catch (Exception e) {
			d = defaultValue;
		}
		return d;
	}

	public static Integer toInteger(Object obj) {
		return NumberUtils.toInteger(obj, false);
	}

	public static Integer toInteger(Object obj, boolean isThrowException) {
		Integer i;
		try {
			if(obj == null) return 0;
			String str = obj.toString();
			str = StringUtils.replace(str, ",", "");
			i = new Integer(str);		
		} catch (Exception e) {
			if (isThrowException){
				throw new IllegalArgumentException("["
						+ ObjectUtils.nullSafeToString(obj)+"]转换Integer错误!",e);
			}else{
				i = null;
			}
		}
		return i;
	}

	public static Integer toInteger(Object obj, Integer defaultValue) {
		Integer re;
		try {
			String str = obj.toString();
			str = StringUtils.replace(str, ",", "");
			re = new Integer(str);
		} catch (Exception e) {
			re = defaultValue;
		}
		return re;
	}
	
	/**
	 * 收款选择客户后出错，所以这里改成 false
	 * @param obj
	 * @return
	 */
	public static Long toLong(Object obj) {
		return NumberUtils.toLong(obj, false);
	}

	/**	将 String 转化为 Long 是否抛异常 */
	public static Long toLong(Object obj, boolean isThrowException) {
		Long l = null;
		try {
			String str= obj.toString();
			str = StringUtils.replace(str, ",", "");
			str = StringUtils.replace(str, "'", "");
			l = new Long(str);	
		} catch (Exception e) {
			if (isThrowException)
				throw new IllegalArgumentException("["+ ObjectUtils.nullSafeToString(obj)+"]转换Long错误!",e);
		}
		return l;
	}

	/**	将 String 转化为 Long 是否抛异常 */
	public static Long toLong(Object obj, Long defaultValue) {
		Long l;
		try {
			String str= obj.toString();
			str = StringUtils.replace(str, ",", "");
			l = new Long(str);	
		} catch (Exception e) {
			l = defaultValue;
		}
		return l;
	}
	
	
	public static Short toShort(Object obj){
		
		return toShort(obj,false);
	}
	
	public static Short toShort(Object obj,boolean isThrowException){
		Short s = null;
		try {
			String str= obj.toString();
			str = StringUtils.replace(str, ",", "");
			s = new Short(str);
		} catch (Exception e) {
			if(isThrowException)
				throw new IllegalArgumentException("["+ ObjectUtils.nullSafeToString(obj)+"]转换Short错误!",e);
		}
		return s;
	}
	/**
	 * 将 String 转化成 int 
	 * */
	public static int intValue(Object obj){
		return toInteger(obj,true);
	}

	/**
	 * 将 String 转化成 int ,转换过程中出现错误则返回自定义值
	 * */
	public static int intValue(Object obj,int defaultValue){
		return toInteger(obj,false) == null? defaultValue:toInteger(obj, false).intValue();
	}

	/**
	 * 将 String 转化成 long 
	 * */
	public static long longValue(String str){
		return Long.parseLong(str);
	}

	/**
	 * 将 String 转化成 long ,转换过程中出现错误则返回自定义值
	 * */
	public static long longValue(String str,long defaultValue){
		return toLong(str,false) == null? defaultValue:toLong(str).longValue();
	}
	
	/**
	 *  转化成 double 
	 * */
	public static double doubleValue(Object obj){
		return toDouble(obj, true);
	}

	/**
	 * 转化成 double ,转换过程中出现错误则返回自定义值
	 * */
	public static double doubleValue(Object obj,double defaultValue){
		Double d = toDouble(obj, false);
		return d==null?defaultValue:d;
	}

	

	public static Long[] toLongArray(Object[] objs){
		if(objs == null || objs.length == 0){
			return new Long[0];
		}
		List<Long> result = new ArrayList<Long>();
		for(Object o:objs){
			result.add(toLong(o.toString(),true));
		}
		return result.toArray(new Long[0]);
	}

	public static int[] intArray(Integer[] array) {
		int[] result = new int[array.length];
		int i=0;
		for(Integer value : array){
			result[i++] = value;
		}
		return result;
	}

	public static int[] intArray(List<Integer> list) {
		int[] result = new int[list.size()];
		int i=0;
		for(Integer value : list){
			result[i++] = value;
		}
		return result;
	}

	public static long[] longArray(Object[] array){
		Long[] ll = toLongArray(array);
		return NumberUtils.longArray(ll);
	}

	public static long[] longArray(Long[] array) {
		long[] result = new long[array.length];
		int i=0;
		for(Long value : array){
			result[i++] = value;
		}
		return result;
	}

	public static long[] longArray(List<Long> list) {
		return longArray(list.toArray(new Long[0]));
	}

	public static boolean canParseInt(Object value) {
		try {
			Integer.parseInt(value.toString());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean canParseDouble(Object value) {
		try {
			Double.parseDouble(value.toString());
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**四舍五入返回X位保留小数**/
	public static String round(Double d,int x){
		DecimalFormat df = null;
		String formatString="#";
		Double td = 1d;
		if(x>0){
			td = Math.pow(10, x);
			formatString += ".";
			for(int f=1;f<=x;f++){
				formatString+="#";
			}
		}
		df = new DecimalFormat(formatString);
		Double xD = Math.floor(d * td + 0.5);
		return df.format(xD/td);
	}
}
