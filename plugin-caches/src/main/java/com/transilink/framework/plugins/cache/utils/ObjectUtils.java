package com.transilink.framework.plugins.cache.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

public class ObjectUtils {
	private final static Log log = LogFactory.getLog(ObjectUtils.class);
	
	public static final String EMPTY_STRING = StringUtils.Symbol.EMPTY;
	
	public static final int DEFAULT_INT_VALUE = Integer.MIN_VALUE;
	public static final long DEFAULT_LONG_VALUE = Long.MIN_VALUE;

	public static void main(String[] args) {
//		log.debug(toLong("1231,233"));
//		log.debug(toInteger("1111,161"));
//		log.debug(arg0);
//		log.debug(arg0);
//		log.debug(arg0);
//		log.debug(arg0);
//		log.debug(arg0);
		
	}
	
	
	public static <T> T newInstance(Class<T> type){
		Assert.notNull(type);
		try {
			return type.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("实例�? [" + type +" 错误] !");
	}
	
	/**
	 * 转换 Bean �? Field 编码
	 * Field 的类型为 String �? String[]
	 * */
	public static void convertStringFieldCoding(Object theObject){
		PropertyDescriptor[] propDescs = PropertyUtils.getPropertyDescriptors(theObject);
		for (int i = 0, n = propDescs.length; i < n; i++) {
			String name = propDescs[i].getName();
			
			if (!PropertyUtils.isReadable(theObject, name)) {continue;}
			
			Object value = null;
			try {
				value = PropertyUtils.getProperty(theObject, name);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			//log.debug("1111 name ==" + name +", value == " + ObjectUtils.nullSafeToString(value));
			
			if(!PropertyUtils.isWriteable(theObject, name)){continue;}
			
			if(value instanceof String){ 
				value = StringUtils.convertStrByCoding((String)value, "ISO-8859-1");
			}
			else if(value != null && value.getClass().equals(String[].class)){
				String[] strs = (String[])(value);
				
				if(strs.length <= 0){continue;}
				
				String[] values = new String[strs.length];
				for(int index=0;index<strs.length;index++){
//					log.debug(objs[index]);
					values[index] = StringUtils.convertStrByCoding((String)strs[index], "ISO-8859-1");
				}
				value = values;
			}
			else{
				continue;
			}
			
			//log.debug("2222 name ==" + name +", value == " + ObjectUtils.nullSafeToString(value));
			
			try {
				PropertyUtils.setProperty(theObject, name, value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

//	public static <T> T newInstance(Class<T> clazz){
//		if(clazz == null){	return null;}
//		try {
//			return clazz.newInstance();
//		} catch (Exception e) {
//			throw new RuntimeException("["+clazz+"] 实例化错�?,请检查是否有默认构�?�方�?!");
//		}
//			
//	}

	/**
	 * @see #toString()
	 * �?个�?�用的toString方法
	 * 将一个POJO类的 getXXX 的�?�全部输�?
	 * */
	public static String toStringMethod(Object theObject){
		try {
			StringBuffer sb = new StringBuffer(500);
			PropertyDescriptor[] propDescs = PropertyUtils.getPropertyDescriptors(theObject);
			for (int i = 0, n = propDescs.length; i < n; i++) {
				String name = propDescs[i].getName();
				if (!PropertyUtils.isReadable(
						theObject, name)) {
					continue;
				}
				Object obj;
				try {
					obj = PropertyUtils.getProperty(theObject, name);
				} catch (UnsupportedOperationException e) {
					obj = "Unsupported!";
				}

				if (obj instanceof Collection || obj instanceof Map) {
					sb.append(name).append("=[more...]");
				}else {
					sb.append(name).append("=[").append(obj).append("]");
				}
			}
			return sb.toString();
		} catch (Exception e) {
			log.error(theObject.getClass().getCanonicalName()+" toString() Error!",e);
			return "";
		}
	}
	
	public static boolean nullSafeEquals(Object o1, Object o2) {
		return org.springframework.util.ObjectUtils.nullSafeEquals(o1, o2);
	}

	public static int nullSafeHashCode(Object obj) {
		return org.springframework.util.ObjectUtils.nullSafeHashCode((Object)obj);
	}

	public static String nullSafeClassName(Object obj) {
		return org.springframework.util.ObjectUtils.nullSafeClassName(obj);
	}

	public static String nullSafeToString(Object obj) {
		return org.springframework.util.ObjectUtils.nullSafeToString(obj);
	}
	
	/**
	 * 如果�? NULL 就返回空�?
	 * */
	public static String nullSafeToEmptyString(Object obj){
		if (obj == null) {
			return EMPTY_STRING;
		}else{
			return nullSafeToString(obj);
		}
	}
		
	public static boolean isFieldInClass(Class<?> clazz, String fieldName) {
		if(clazz == null || fieldName == null)
			throw new NullPointerException();
		
		Field[] fields = clazz.getDeclaredFields();
		//log.debug("fields.length   " + fields.length);
		for(Field field:fields){
			//log.debug(field.getName());
			if(field.getName().equals(fieldName)){
				return true;
			}
		}
		return false;
	}
	
	public static String getFieldName(Class<?> clazz, Class<?> fieldType){
		if(clazz == null || fieldType == null)
			throw new NullPointerException();
		
		Field[] fields = clazz.getDeclaredFields();
		for(Field field:fields){
			if(field.getType().equals(fieldType)){
				return field.getName();
			}
		}
		throw new IllegalArgumentException("The Field [" + fieldType.getCanonicalName() + "] is not in Object [" + clazz.getCanonicalName() + "] ");
	}
	public static String format(Object obj){
		String result;
	
		if(obj instanceof Collection || obj instanceof Map){
			throw new IllegalArgumentException();
		}
		
		if(obj == null){
			result = null;
		}
		else if(obj instanceof Date){
			result = DateUtils.format((Date)obj,"yyyy-MM-dd");
		}
		else if(obj instanceof Double){
			result = NumberUtils.defaultFormat((Double)obj);
		}
		else if(obj instanceof Integer){
			result = NumberUtils.defaultFormat((Integer)obj);
		}
		else{
			result = obj.toString();
		}
		
		return result;
	}
}
