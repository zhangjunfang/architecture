/**
 * Copyright (c) 2011-2015, @author ocean(zhangjufang0505@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.framework.core.single.table.orm.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * A small Reflection tool kit.
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class Ref {

	public static Class<?> cloneClass(Class<?> clazz){
		Class<?> newClazz = null;
		try {
			newClazz = Class.forName(clazz.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return newClazz;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<?> clazz){
		T t = null;
		try {
			t = (T) clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	public static List<Field> getBeanFields(Class<?> clazz){
		Class<?> claz = cloneClass(clazz);
		List<Field> fieldList = new ArrayList<Field>();
		Field[] fields = null;
		while(claz!=null){
			fields = claz.getDeclaredFields();
			if(fields!=null)
				for(Field field:fields)
					if(!Modifier.isStatic(field.getModifiers()))
						fieldList.add(field);
			claz = claz.getSuperclass();
		}
		return fieldList.isEmpty()?null:fieldList;
	}
	
	public static Field getFieldByName(Class<?> clazz,String name){
		Class<?> iClass = cloneClass(clazz);
		Field field = null;
		while(iClass!=null){
			try {
				field = iClass.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				iClass = iClass.getSuperclass();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			if(field!=null)break;
		}
		return field;
	}
	
	public static Object getFieldVal(Object obj,String name){
		Object val = null;
		try {
			Field field = getFieldByName(obj.getClass(),name);
			field.setAccessible(true);
			val = field.get(obj);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return val;
	}
	
	public static void setFieldVal(Object obj,String name,Object val){
		Field field = getFieldByName(obj.getClass(),name);
		if(field==null)
			return;
		field.setAccessible(true);
		try {
			field.set(obj, val);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
