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
package org.framework.core.single.table.orm.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import org.framework.core.single.table.orm.util.DuplexMap;
import org.framework.core.single.table.orm.util.Ref;

/**
 * Object relationship mapping.
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class ORM {

	public static <T> T toBean(Class<T> beanClass,Map<String, Object> map) {
		T t = null;
		try {
			t = beanClass.newInstance();
			for(Map.Entry<String, Object> e2:map.entrySet())
				if(e2.getValue()!=null)
					Ref.setFieldVal(t, e2.getKey(), e2.getValue());
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		return t;
	}
	
	public static <T> List<T> toBeans(Class<T> beanClass,List<Map<String, Object>> maps) {
		List<T> list = new ArrayList<T>();
		for(Map<String, Object> map:maps)
			list.add(toBean(beanClass, map));
		return list.isEmpty()?null:list;
	}
	
	public static <T> T toBean(Class<T> beanClass,Map<String, Object> map,DuplexMap<String, String> columnFiledDuplexMap) {
		T t = null;
		try {
			t = beanClass.newInstance();
			for(Map.Entry<String, Object> e:map.entrySet())
				if(e.getValue()!=null)
					Ref.setFieldVal(t, columnFiledDuplexMap.getByK(e.getKey()), e.getValue());
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		return t;
	}
	
	public static <T> List<T> toBeans(Class<T> beanClass,List<Map<String, Object>> maps,DuplexMap<String, String> columnFiledDuplexMap) {
		List<T> list = new ArrayList<T>();
		for(Map<String, Object> map:maps)
			list.add(toBean(beanClass, map,columnFiledDuplexMap));
		return list.isEmpty()?null:list;
	}
	
	public static <T> List<T> toBeans(Class<T> beanClass,CachedRowSet crs,DuplexMap<String, String> columnFiledDuplexMap) {
		return toBeans(beanClass, toMaps(crs), columnFiledDuplexMap);
	}
	
	public static Map<String, Object> toMap(Object bean) {
		if(bean==null)
			return null;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Field> fields = Ref.getBeanFields(bean.getClass());
		if(fields!=null){
			try {
				for(Field field:fields){
					if(!Modifier.isStatic(field.getModifiers())){
						field.setAccessible(true);
						map.put(field.getName(), field.get(bean));
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public static Map<String, Object> toMap(Object bean,DuplexMap<String, String> filedColumnDuplexMap) {
		if(bean==null)
			return null;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Field> fields = Ref.getBeanFields(bean.getClass());
		if(fields!=null){
			try {
				for(Field field:fields){
					if(!Modifier.isStatic(field.getModifiers())){
						field.setAccessible(true);
						map.put(filedColumnDuplexMap.getByK(field.getName()), field.get(bean));
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public static List<Map<String, Object>> toMaps(CachedRowSet crs) {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		try {
			ResultSetMetaData rsmd = crs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			String[] labelNames = new String[columnCount];
			int[] types = new int[columnCount];
			for (int i=0; i<columnCount; i++) {
				labelNames[i] = rsmd.getColumnLabel(i+1);
				types[i] = rsmd.getColumnType(i+1);
			}
			while(crs.next()){
				Map<String, Object> map = new HashMap<String, Object>();
				Object value = null;
				for(int i=0;i<columnCount;i++){
					if (types[i] < Types.BLOB)
						value = crs.getObject(i+1);
					else if (types[i] == Types.CLOB)
						value = handleClob(crs.getClob(i+1));
					else if (types[i] == Types.NCLOB)
						value = handleClob(crs.getNClob(i+1));
					else if (types[i] == Types.BLOB)
						value = handleBlob(crs.getBlob(i+1));
					else
						value = crs.getObject(i+1);
					map.put(labelNames[i], value);
				}
				maps.add(map);
			}
			crs.close();
			crs = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maps;
	}
	
	public static byte[] handleBlob(Blob blob) {
		byte[] data = null;
		InputStream is = null;
		try {
			is = blob.getBinaryStream();
			data = new byte[(int)blob.length()];
			is.read(data);
			is.close();
			return data;
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SQLException e2) {
			e2.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	public static String handleClob(Clob clob) {
		String data = null;
		Reader reader = null;
		try {
			reader = clob.getCharacterStream();
			char[] buffer = new char[(int)clob.length()];
			reader.read(buffer);
			data = new String(buffer);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SQLException e2) {
			e2.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
}
