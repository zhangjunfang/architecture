package com.transilink.framework.plugins.cache.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ArrayUtils{

	protected final static Log log = LogFactory.getLog(ArrayUtils.class);
	

	
	/** 集合元素替换 */
	public static void replace(Object[] array,Object oldObj,Object newObj){
		if(oldObj == null){
			oldObj = Null.getInstance();
		}
	
		for(int i=0;i<array.length;i++){
			if(oldObj.equals(array[i])){
				array[i] = newObj;
			}
		}
	}
	
	public static String[] conver2String(Object[] array){
		String[] rs = new String[array.length];
		for(int i=0; i<array.length; i++){
			
			rs[i] = String.valueOf(array[i] == null?"":array[i]);
			
		}
		return rs;
	}
	
	

	/** 去除 toString 方法为空串的�? */
	@SuppressWarnings("unchecked")
	public static <T> T[] delectToStringEmptyValues(T[] array){
		if(array == null){	return array;}
		
		List<T> list = new ArrayList<T>();
		for(T o:array){
			if(o == null){
				continue;
			}
			if(StringUtils.hasText(o.toString())){
				list.add(o);
			}
		}
		T[] result =(T[]) Array.newInstance(array.getClass().getComponentType(), list.size());
		return list.toArray(result);
	}
	
	/** 去除 null �? */
	@SuppressWarnings("unchecked")
	public static <T> T[] delectNullValues(T[] array){
		if(array == null){	return array;}
		
		List<T> list = new ArrayList<T>();
		for(T o:array){
			if(o == null){
				continue;
			}
			list.add(o);
		}
		T[] result =(T[]) Array.newInstance(array.getClass().getComponentType(), list.size());
		return list.toArray(result);
	}

	/** 删除相同的�?? */
	@SuppressWarnings("unchecked")
	public static <T> T[] delectEqualValues(T[] array) {
		if(array == null){	return array;}
		
		Set<T> set = new HashSet<T>(array.length);
		
		for(T t:array){
			set.add(t);
		}
		
		T[] result = (T[]) Array.newInstance(array.getClass().getComponentType(), set.size());
		return set.toArray(result);
	}

	public static boolean isEmpty(int[] array) {
		return (array == null || array.length==0);
	}

	public static boolean isEmpty(Object[] array) {
		return (array == null || array.length==0);
	}

	public static int indexOf(int[] array, int oo) {
		if(array == null){	return -1;}
		for(int i=0;i<array.length;i++){
			if (array[i] == oo)	return i;
		}
		return -1;
	}

	public static int indexOf(Object[] array, Object o) {
		if(array == null){	return -1;}
		for(int i=0;i<array.length;i++){
			if (array[i].equals(o))	return i;
		}
		return -1;
	}

	public static boolean contains(Object[] array, Object o){
		return indexOf(array, o) > -1;
	}

	/**
	 * 数组克隆
	 * */
	@SuppressWarnings("unchecked")
	public static <T> T[] copyArray(T[] src) {
		if(src == null){
			return null;
		}
		int length = src.length;
		T[] result =(T[]) Array.newInstance(src.getClass().getComponentType(), length);
		System.arraycopy(src, 0, result, 0, length);
		return result;
	}

	/**
	 * 将不定个对象添加到一个对象数组尾�?
	 * */
	@SuppressWarnings("unchecked")
	public static <T> T[] addObjectsToArray(T[] array, T...otherObjs){
		if(otherObjs == null || array == null){		throw new NullPointerException();}
		return ArrayUtils.addObjectsToArray(array, array.length, otherObjs);
	}

	/**
	 * 将不定个对象添加到一个数�?
	 * */
	@SuppressWarnings("unchecked")
	public static <T> T[] addObjectsToArray(T[] array, int index, T...otherObjs){
		if(otherObjs == null || array == null){		throw new NullPointerException();}
		
		if(index<0 || index>array.length){	
			throw new IndexOutOfBoundsException(
				"索引:["+index+"]小于0 或�?? 大于数组长度: ["+array.length+"]"); 
		}
		
		T[] result = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length + otherObjs.length);
		
		System.arraycopy(array, 		0, 	result,						   0,                index);
		System.arraycopy(otherObjs, 	0,	result,					   index,  	  otherObjs.length);
		System.arraycopy(array, 	index, 	result, index + otherObjs.length, array.length - index);
		
		return result;
	}

	/**
	 * 合并对象到一个对象数组Object[] �?
	 * 如果对象�? null, String, Number, Date 类型则直接加�?
	 * 如果对象�? getClass().isArray() 则将每项加入
	 * */
	public static Object[] mergeObjectsToArray(Object...objs) {
		if(objs == null){	return objs;}
		
		List<Object> list = new ArrayList<Object>();
		
		for(Object obj:objs){
			if(ArrayUtils.addIntoList(list,obj)){	continue;}
			
			if(obj.getClass().isArray()){
				
				Object temp;
				for(int i=0;i<Array.getLength(obj); i++){
					temp = Array.get(obj, i);
					if(		ArrayUtils.addIntoList(list,temp)){	continue;}
					throw new UnsupportedOperationException("mergeObjectsToArray 不支持类型[" + ObjectUtils.nullSafeClassName(obj)+ "]");
				}
			}
			else{
				throw new UnsupportedOperationException("mergeObjectsToArray 不支持类型[" + ObjectUtils.nullSafeClassName(obj)+ "]");
			}
			
		}
		return list.toArray();
	}

	private static boolean addIntoList(List<Object> list,Object obj){
		if(obj == null){
			list.add(null);
			return true;
		}
		else if(obj instanceof String || obj instanceof Number || obj instanceof Date){
			list.add(obj);
			return true;
		}
		return false;
	}

	/**
	 * 避免�? ArrayIndexOutOfBoundsException �? NullPointerException<p>
	 * safeGet(null, 0) = null<p>
	 * safeGet(int[]{1,2,3}, -1) = null<p>
	 * safeGet(int[]{1,2,3}, 3) = null<p>
	 * safeGet(int[]{1,2,3}, 2) = 3<p>
	 * */
	public static <T> T safeGet(T[] array, int index) {
		if(array==null || index<0 || index>array.length-1){	
			return null;
		}
		return array[index];
	}

	/**
	 * 返回�?个新的数组实例并且填充默认�??
	 * newInstanceWithFill(String.class, 2, "s") = new String[]{"s", "s"}<p>
	 * newInstanceWithFill(Integer.class, 3, 1) = new Integer[]{new Integer(1), new Integer(1), new Integer(1)}<p>
	 * newInstanceWithFill(Integer.TYPE, 3, 1) = new int[]{1, 1, 1}<p>
	 * */
	public static Object newInstanceWithFill(Class<?> componentType, int length, Object filledValue) {
		Object array = Array.newInstance(componentType, length);
		for(int i=0;i<Array.getLength(array);i++){
			Array.set(array, i, filledValue);
		}
		return array;
	}
	
	
	public static String[] removeNullValue(String[] strings){
		List<String> list = new ArrayList<String>(strings.length);
		for(String s : strings){
			if(StringUtils.notText(s)){
				continue;
			}
			list.add(s);
		}
		return list.toArray(new String[list.size()]);
	}

	public static void main(String[] args) {
//		log.debug(newInstanceWithFill(Integer.TYPE, 2 , 3) instanceof int[]);
//		log.debug(ObjectUtils.nullSafeToString(newInstanceWithFill(Integer.TYPE, 2 , 3)));
//		log.debug(newInstanceWithFill(Integer.class, 2 , 3) instanceof int[]);
//		log.debug(ObjectUtils.nullSafeToString(newInstanceWithFill(Integer.class, 2 , 3)));
	}
}
