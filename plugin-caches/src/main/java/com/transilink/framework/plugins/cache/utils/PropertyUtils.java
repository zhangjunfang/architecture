package com.transilink.framework.plugins.cache.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.type.ClobType;


/****
 * 
 * @author sntey
 *
 */

public abstract class PropertyUtils {

	private final static Log LOG = LogFactory.getLog(PropertyUtils.class);
	
	public static void main(String[] args) throws Exception {
		LOG.debug(getPropertyType(String.class, "bytes"));
	}
	
	/** 从某个对�?(targer)中根据名称获得字段类�? */
	public static Class<?> getPropertyType(Object target, String name){
		try {
			return org.apache.commons.beanutils.PropertyUtils.getPropertyType(target, name);
		} catch (Exception e) {
			LOG.error("Object:[" + target.getClass() + "] property:[" + name + "] is error!");
			throw new RuntimeException(e);
		}
	}
	
	/** 从某个对�?(targer)中根据名称获得字段类�? */
	public static Class<?> getPropertyType(Class<?> targetClass, String name){
		for(Field field:targetClass.getDeclaredFields()){
			if(name.contains(".")){
				if(name.startsWith(field.getName())){
					return getPropertyType(field.getType(), name.substring(name.indexOf(".") + 1, name.length()));
				}
			}
			else{
				if(name.equals(field.getName())){
					return field.getType();
				}
			}
		}
		throw new IllegalArgumentException("property name is error!");
	}
	
	/** 从某个对�?(targer)中根据名称获得字�? */
	public static Object getProperty(Object target, String name){
		try {
			return org.apache.commons.beanutils.PropertyUtils.getProperty(target, name);
		} catch (Exception e) {
			LOG.error("Object:[" + target.getClass() + "] property:[" + name + "] is error!");
			throw new RuntimeException(e);
		}
	}
	
	/** 从某个对�?(targer)中根据名称获得字�? */
	public static void setProperty(Object target, String name, Object value){
		try {
			org.apache.commons.beanutils.PropertyUtils.setProperty(target, name, value);
		} catch (Exception e) {
			LOG.error("Object:[" + target.getClass() + "] property:[" + name + "] is error!");
			throw new RuntimeException(e);
		}
	}
	
	public static void setPropertyType(Object t,Map<String,Object> map){
		Field[] pds = t.getClass().getDeclaredFields();
		if(pds==null || pds.length<=0){
			return;
		}
		for(Field pd : pds){
			String pd_name = pd.getName();
			if("class".equals(pd_name) || !PropertyUtils.isWriteable(t, pd_name)){
				continue;
			}
			Object value = map.get(pd_name.toUpperCase());
			setPropertyType(t,pd_name,value);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void setPropertyType(Object t, String key, Object value){
		Class type = PropertyUtils.getPropertyType(t, key);
		if(type == null){
			return;
		}
		if(null == value){
			PropertyUtils.setProperty(t,key, value);
			
		} else if(ClobType.class.equals(value.getClass())){//此功能由于要集成到 hibernate新版中，先这样设置，有空再测试
			PropertyUtils.setProperty(t, key, ((ClobType)value).toString());
		}else{
			if(String.class.equals(type)){
				PropertyUtils.setProperty(t,key, String.valueOf(value));
			}else if(Integer.class.equals(type)){
				PropertyUtils.setProperty(t,key, NumberUtils.toInteger(value));
			}else if(Double.class.equals(type)){
				PropertyUtils.setProperty(t,key, NumberUtils.uble(value));
			}else if(Date.class.equals(type) || java.sql.Date.class.equals(type)){
				String v = ObjectUtils.nullSafeToEmptyString(value);
				if(v.length()>11){
					PropertyUtils.setProperty(t,key, DateUtils.parse(String.valueOf(value),DateUtils.DEFALUT_DATETIME_PATTERN));
				}else{
					PropertyUtils.setProperty(t,key, DateUtils.parse(String.valueOf(value),DateUtils.DEFALUT_DATE_PATTERN));
				}
			}else if(Float.class.equals(type)){
				PropertyUtils.setProperty(t,key, NumberUtils.toFloat(value));
			} else if(Boolean.class.equals(type)){
				PropertyUtils.setProperty(t, key, value);
			} else{
				PropertyUtils.setProperty(t,key, String.valueOf(value));
			}
		}
	}
	
	public static PropertyDescriptor[] getPropertyDescriptors(Object target){
		return org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors(target);
	}
	
	@SuppressWarnings("rawtypes")
	public static PropertyDescriptor[] getPropertyDescriptors(Class targetClass){
		return org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors(targetClass);
		
	}
	
	public static boolean isReadable(Object target, String name){
		return org.apache.commons.beanutils.PropertyUtils.isReadable(target, name);
	}

	public static boolean isWriteable(Object target, String name) {
		return org.apache.commons.beanutils.PropertyUtils.isWriteable(target, name);
	}
	/**交如果To中对应的数据发生变化，就将from中的数据复制到To中.
	 * 不会复制ID
	 * without字段的数据也不会复制。
	 * 2012.09.17 Sntey创建,
	 * **/
	public static void copyTo(Object from,Object to,String...without){
		PropertyDescriptor[] propDescs = PropertyUtils.getPropertyDescriptors(from);
		String name;
		Object value;
		List<String> excepts = new ArrayList<String>();
		if(without!=null && without.length>0){
			for(String w : without){
				excepts.add(w);
			}
		}
		for(PropertyDescriptor pd:propDescs){
			name = pd.getName();
			if(!PropertyUtils.isWriteable(from, name)){
				continue;
			}
			if(excepts.size()>0 && excepts.contains(name)){
				continue;
			}
			
			if(name.equals("class") || name.equals("id")){	continue;}
			
			value = getProperty(from, name);
//			if(value == null || "".equals(value)){
//				continue;
//			}
			setProperty(to, name, value);
		}
	}
	/****
	 * 
	 * @Title: empty
	 * @Description: (除了ID之年，清空实体中所有变量)
	 * @param <T>
	 * @param entity    设定文件
	 * @return void 返回类型 返回格式
	 * @throws
	 * @author 杨航(Sntey)
	 * @date 创建日期：2012-10-22
	 * 修改日期：
	 * 修改人：
	 * 复审人：
	 */
	public static void empty(Object entity){
		PropertyDescriptor[] propDescs = PropertyUtils.getPropertyDescriptors(entity);
		String name;
		for(PropertyDescriptor pd:propDescs){
			name = pd.getName();
			if(!PropertyUtils.isWriteable(entity, name)){
				continue;
			}
			if(name.equals("class") || name.equals("id")){	continue;}
			if(pd.getPropertyType().isPrimitive()){
				continue;
			}
			setProperty(entity, name, null);
		}
	}
	
}
