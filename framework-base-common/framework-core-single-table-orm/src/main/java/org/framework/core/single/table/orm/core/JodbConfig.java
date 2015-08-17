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

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.framework.core.single.table.orm.util.DuplexMap;
import org.framework.core.single.table.orm.util.Ref;
import org.framework.core.single.table.orm.util.Str;

/**
 * JodbConfig.
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class JodbConfig {

	private String table;
	private DuplexMap<String, String> columnFieldDuplexMap;
	
	private static final Map<Class<?>, JodbConfig> jodbConfigMap = new ConcurrentHashMap<Class<?>, JodbConfig>();
	
	protected JodbConfig() {}
	
	public JodbConfig(String table,DuplexMap<String, String> columnFieldDuplexMap) {
		this.table = table;
		this.columnFieldDuplexMap = columnFieldDuplexMap;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public DuplexMap<String, String> getColumnFieldDuplexMap() {
		return columnFieldDuplexMap;
	}

	public void setColumnFieldDuplexMap(DuplexMap<String, String> columnFieldDuplexMap) {
		this.columnFieldDuplexMap = columnFieldDuplexMap;
	}

	public static Map<Class<?>, JodbConfig> getJodbconfigmap() {
		return jodbConfigMap;
	}

	public static JodbConfig getJodbConfig(Class<?> clazz){
		JodbConfig jodbConfig = jodbConfigMap.get(clazz);
		if(jodbConfig==null){
			DuplexMap<String, String> columnFieldDuplexMap = new DuplexMap<String, String>();
			List<Field> fields = Ref.getBeanFields(clazz);
			if(fields!=null){
				for(Field field:fields){
					ColumnMapping columnMapping = field.getAnnotation(ColumnMapping.class);
					if(columnMapping!=null){
						String column = Str.isNOB(columnMapping.value())?Str.humpToUnderline(field.getName()):columnMapping.value();
						columnFieldDuplexMap.put(column, field.getName());
					}
				}
			}
			if(columnFieldDuplexMap.isEmpty())
				throw new JodbException("There is no ColumnMapping in your bean class '"+clazz.getName()+"'.You must use @ColumnMapping annotation(s) to map your bean field(s) to the table field(s).");
			TableMapping tableMapping = clazz.getAnnotation(TableMapping.class);
			String table = tableMapping==null||Str.isNOB(tableMapping.value())?Str.humpToUnderline(clazz.getSimpleName()):tableMapping.value();
			jodbConfig = new JodbConfig(table, columnFieldDuplexMap);
			jodbConfigMap.put(clazz, jodbConfig);
		}
		return jodbConfig;
	}

	public static String getTable(Class<?> clazz){
		return getJodbConfig(clazz).getTable();
	}
	
	public static DuplexMap<String, String> getColumnFieldDuplexMap(Class<?> clazz){
		return getJodbConfig(clazz).getColumnFieldDuplexMap();
	}
}
