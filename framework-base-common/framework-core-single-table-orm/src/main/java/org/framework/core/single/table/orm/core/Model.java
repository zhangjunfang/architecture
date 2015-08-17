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
import java.util.Map.Entry;
import java.util.Set;

import org.framework.core.single.table.orm.util.DuplexMap;
import org.framework.core.single.table.orm.util.Js;
import org.framework.core.single.table.orm.util.Ref;
import org.framework.core.single.table.orm.util.Str;

/**
 * Model uses @TableMapping as it's first choice to map itself to a table.
 * If @TableMapping is missing or the value is null or empty,Model uses it's hump-to-underline name as a second choice.
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class Model<M extends Model> implements Serializable {
	
	private static final long serialVersionUID = 117960732016226962L;
	
	private String name;
	private Map<String, Object> attr = new HashMap<String, Object>();
	private Object generatedKey;
	
	public Model(){
		TableMapping tableMapping = getClass().getAnnotation(TableMapping.class);
		if(tableMapping==null||Str.isNOB(tableMapping.value()))
			name = Str.humpToUnderline(getClass().getSimpleName());
		else
			name = tableMapping.value();
	}
	
	public Object get(String key) {
		return attr.get(key);
	}
	
	public M set(String key,Object val) {
		attr.put(key, val);
		return (M) this;
	}
	
	public M set(Map<String, Object> attr) {
		if(attr!=null)
			for(Map.Entry<String, Object> e:attr.entrySet())
				set(e.getKey(), e.getValue());
		return (M) this;
	}
	
	public M reset(Map<String, Object> attr) {
		this.attr = attr;
		return (M) this;
	}
	
	public M reuse() {
		attr.clear();
		return (M) this;
	}
	
	public  Map<String, Object> getMap() {
		return attr;
	}
	
	public Set<Entry<String, Object>> getEntrySet() {
		return attr.entrySet();
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
	
	public boolean update(Model<M> model, Session session) {
		List<Object> params = new ArrayList<Object>();
		String sql = session.getDialect().update(name, model.attr, attr, params);
		session.reportSql(sql);
		return DbUtil.update(session.getConnection(), sql, params);
	}
	
	public List<M> select(Session session) {
		List<Object> params = new ArrayList<Object>();
		String sql = session.getDialect().select(name, attr, params);
		session.reportSql(sql);
		List<Map<String, Object>> maps = ORM.toMaps(DbUtil.query(session.getConnection(), sql, params));
		List<M> models = new ArrayList<M>();
		M model = null;
		for(Map<String, Object> map:maps){
			model = Ref.newInstance(getClass());
			model.reset(map);
			for(Map.Entry<String, Object> e:getEntrySet()){
				if(e.getValue()!=null)
					model.set(e.getKey(), e.getValue());
			}
			models.add(model);
		}
		return models.isEmpty()?null:models;
	}
	
	public List<M> paginate(int pageNumber, int pageSize, Session session) {
		List<Object> params = new ArrayList<Object>();
		String sql = session.getDialect().paginate(name, attr, pageNumber, pageSize, params);
		session.reportSql(sql);
		List<Map<String, Object>> maps = ORM.toMaps(DbUtil.query(session.getConnection(), sql, params));
		List<M> models = new ArrayList<M>();
		M model = null;
		for(Map<String, Object> map:maps){
			model = Ref.newInstance(getClass());
			model.reset(map);
			for(Map.Entry<String, Object> e:getEntrySet()){
				if(e.getValue()!=null)
					model.set(e.getKey(), e.getValue());
			}
			models.add(model);
		}
		return models.isEmpty()?null:models;
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
	
	public M selectFirst(Session session) {
		List<M> list = select(session);
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
	
	public <T> T toBean(Class<T> beanClass) {
		return ORM.toBean(beanClass, getMap());
	}
	
	public <T> T toBean(Class<T> beanClass,DuplexMap<String, String> columnFiledDuplexMap) {
		return ORM.toBean(beanClass, getMap(), columnFiledDuplexMap);
	}
	
	public static <T,M extends Model<M>> List<T> toBeans(Class<T> beanClass,List<M> models) {
		if(models==null||models.isEmpty())
			return null;
		List<T> beans = new ArrayList<T>();
		for(M model:models)
			beans.add(model.toBean(beanClass));
		return beans;
	}
	
	public static <T,M extends Model<M>> List<T> toBeans(Class<T> beanClass,List<M> models,DuplexMap<String, String> columnFiledDuplexMap) {
		if(models==null||models.isEmpty())
			return null;
		List<T> beans = new ArrayList<T>();
		for(M model:models)
			beans.add(model.toBean(beanClass,columnFiledDuplexMap));
		return beans;
	}
}