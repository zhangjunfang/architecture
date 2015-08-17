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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.framework.core.single.table.orm.util.DuplexMap;
import org.framework.core.single.table.orm.util.Js;

/**
 * Record.
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class Record implements Serializable {
	
	private static final long serialVersionUID = -8231986888120518765L;
	
	private String name;
	private Map<String, Object> attr = new HashMap<String, Object>();
	private Object generatedKey;
	
	public Record(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object get(String key) {
		return attr.get(key);
	}
	
	public Record set(String key,Object val) {
		attr.put(key, val);
		return this;
	}
	
	public Record set(Map<String, Object> attr) {
		if(attr!=null)
			for(Map.Entry<String, Object> e:attr.entrySet())
				set(e.getKey(), e.getValue());
		return this;
	}
	
	public Record reSet(Map<String, Object> attr) {
		this.attr = attr;
		return this;
	}
	
	public Record reuse() {
		attr.clear();
		return this;
	}
	
	public  Map<String, Object> getMap() {
		return attr;
	}
	
	public  Set<Map.Entry<String, Object>> getEntrySet() {
		return getMap().entrySet();
	}
	
	public boolean insert(Session session) {
		List<Object> params = new ArrayList<Object>();
		String sql = session.getDialect().insert(name, attr, params);
		session.reportSql(sql);
		return DbUtil.updateAndRetrieveKey(generatedKey,session.getConnection(), sql, params);
	}
	
	public Object getGeneratedKey() {
		return generatedKey;
	}
	
	public boolean delete(Session session) {
		List<Object> params = new ArrayList<Object>();
		String sql = session.getDialect().delete(name, attr, params);
		session.reportSql(sql);
		return DbUtil.update(session.getConnection(), sql, params);
	}
	
	public boolean update(Record record, Session session) {
		List<Object> params = new ArrayList<Object>();
		String sql = session.getDialect().update(name, record.attr, attr, params);
		session.reportSql(sql);
		return DbUtil.update(session.getConnection(), sql, params);
	}
	
	public List<Record> select(Session session) {
		List<Object> params = new ArrayList<Object>();
		String sql = session.getDialect().select(name, attr, params);
		session.reportSql(sql);
		List<Map<String, Object>> maps = ORM.toMaps(DbUtil.query(session.getConnection(), sql, params));
		List<Record> records = new ArrayList<Record>();
		for(Map<String, Object> map:maps){
			Record record = new Record(name).reSet(map);
			for(Map.Entry<String, Object> e:getEntrySet()){
				if(e.getValue()!=null)
					record.set(e.getKey(), e.getValue());
			}
			records.add(record);
		}
		return records.isEmpty()?null:records;
	}
	
	public List<Record> paginate(int pageNumber, int pageSize, Session session) {
		List<Object> params = new ArrayList<Object>();
		String sql = session.getDialect().paginate(name, attr, pageNumber, pageSize, params);
		session.reportSql(sql);
		List<Map<String, Object>> maps = ORM.toMaps(DbUtil.query(session.getConnection(), sql, params));
		List<Record> records = new ArrayList<Record>();
		for(Map<String, Object> map:maps){
			Record record = new Record(name).reSet(map);
			for(Map.Entry<String, Object> e:getEntrySet()){
				if(e.getValue()!=null)
					record.set(e.getKey(), e.getValue());
			}
			records.add(record);
		}
		return records.isEmpty()?null:records;
	}
	
	public boolean validate(Session session) {
		List<Object> params = new ArrayList<Object>();
		String sql = session.getDialect().validate(name, attr, params);
		session.reportSql(sql);
		return DbUtil.validate(session.getConnection(), sql, params);
	}
	
	public long count(Session session) {
		List<Object> params = new ArrayList<Object>();
		String sql = session.getDialect().count(name, attr, params);
		session.reportSql(sql);
		return DbUtil.count(session.getConnection(), sql, params);
	}
	
	public Record selectFirst(Session session) {
		List<Record> list = select(session);
		if(list==null)
			return null;
		return list.get(0);
	}
	
	@Override
	public String toString() {
		return attr.toString();
	}
	
	public String toJson() {
		return Js.toJson(attr);
	}
	
	public Record fromBean(Object bean) {
		return set(ORM.toMap(bean));
	}
	
	public Record fromBean(Object bean,DuplexMap<String, String> fieldColumnDuplexMap) {
		return set(ORM.toMap(bean,fieldColumnDuplexMap));
	}
	
	public <T> T toBean(Class<T> beanClass) {
		return ORM.toBean(beanClass, getMap());
	}
	
	public <T> T toBean(Class<T> beanClass,DuplexMap<String, String> columnFiledDuplexMap) {
		return ORM.toBean(beanClass, getMap(), columnFiledDuplexMap);
	}
	
	public static <T> List<T> toBeans(Class<T> beanClass,List<Record> records) {
		if(records==null||records.isEmpty())
			return null;
		List<T> beans = new ArrayList<T>();
		for(Record record:records)
			beans.add(record.toBean(beanClass));
		return beans;
	}
	
	public static <T> List<T> toBeans(Class<T> beanClass,List<Record> records,DuplexMap<String, String> columnFiledDuplexMap) {
		if(records==null||records.isEmpty())
			return null;
		List<T> beans = new ArrayList<T>();
		for(Record record:records)
			beans.add(record.toBean(beanClass,columnFiledDuplexMap));
		return beans;
	}
}