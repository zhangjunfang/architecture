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
package org.framework.core.single.table.orm.dialect;

import java.util.List;
import java.util.Map;

import org.framework.core.single.table.orm.syntax.Conditions;
import org.framework.core.single.table.orm.syntax.KeyValuePairs;
import org.framework.core.single.table.orm.syntax.Values;
import org.framework.core.single.table.orm.util.Str;

/**
 * DefaultDialect.Paginate is not supported due to the diversity of data base manufacturers.
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class DefaultDialect extends Dialect {

	protected String quote;
	
	public DefaultDialect() {
		quote = "";
	}
	
	@Override
	public String insert(String table, Map<String, Object> map,List<Object> params) {
		Values keys = new Values().setQuote(quote);
		Values vals = new Values();
		for(Map.Entry<String, Object> e:map.entrySet()){
			if(e.getValue()!=null){
				keys.add(e.getKey());
				if((e.getValue() instanceof String)&&e.getValue().toString().endsWith(".nextval")){
					vals.add(e.getValue());
				}else {
					vals.add("?");
					params.add(e.getValue());
				}
			}
		}
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ")
		   .append(quote+table+quote)
		   .append("(")
		   .append(keys)
		   .append(")")
		   .append(" values(")
		   .append(vals)
		   .append(")");
		return sql.toString();
	}

	@Override
	public String delete(String table, Map<String, Object> map,List<Object> params) {
		Conditions conditions = new Conditions().setKeyQuote(quote);
		for(Map.Entry<String, Object> e:map.entrySet()){
			if(e.getValue()!=null){
				conditions.add(e.getKey(),"?","=");
				params.add(e.getValue());
			}
		}
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ")
		   .append(quote+table+quote)
		   .append(" where ")
		   .append(conditions.isEmpty()?"1":conditions);
		return sql.toString();
	}

	@Override
	public String update(String table, Map<String, Object> target,
			Map<String, Object> replacement,List<Object> params) {
		
		KeyValuePairs keyValuePairs = new KeyValuePairs().setKeyQuote(quote);
		for(Map.Entry<String, Object> e:replacement.entrySet()){
			if(e.getValue()!=null){
				keyValuePairs.add(e.getKey(),"?");
				if((e.getValue() instanceof String)&&e.getValue().toString().endsWith(".nextval"))
					continue;
				params.add(e.getValue());
			}
		}
		
		Conditions conditions = new Conditions().setKeyQuote(quote);
		for(Map.Entry<String, Object> e:target.entrySet()){
			if(e.getValue()!=null){
				conditions.add(e.getKey(),"?","=");
				params.add(e.getValue());
			}
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("update ")
		   .append(quote+table+quote)
		   .append(" set ")
		   .append(keyValuePairs)
		   .append(" where ")
		   .append(conditions.isEmpty()?"1":conditions);
		return sql.toString();
	}

	@Override
	public String select(String table, Map<String, Object> map,List<Object> params) {
		Values values = new Values().setQuote(quote);
		Conditions conditions = new Conditions().setKeyQuote(quote);
		for(Map.Entry<String, Object> e:map.entrySet()){
			if(e.getValue()==null) {
				values.add(e.getKey());
			}else {
				conditions.add(e.getKey(), "?", "=");
				params.add(e.getValue());
			}
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select ")
		   .append(values.isEmpty()?"*":values)
		   .append(" from ")
		   .append(quote+table+quote)
		   .append(" where ")
		   .append(Str.isNOB(conditions.toString())?"1":conditions);
		return sql.toString();
	}

	@Override
	public String paginate(String table, Map<String, Object> map,
			int pageNumber, int pageSize,List<Object> params) {
		int offset = pageSize * (pageNumber - 1);
		String limit = " limit "+offset+","+pageSize;
		return select(table, map, params)+limit;
	}

	@Override
	public String count(String table, Map<String, Object> map,
			List<Object> params) {
		Conditions conditions = new Conditions().setKeyQuote(quote);
		for(Map.Entry<String, Object> e:map.entrySet()){
			if(e.getValue()!=null) {
				conditions.add(e.getKey(), "?", "=");
				params.add(e.getValue());
			}
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) from")
		   .append(quote+table+quote)
		   .append(" where ")
		   .append(conditions.isEmpty()?"1":conditions);
		return sql.toString();
	}

	@Override
	public String validate(String table, Map<String, Object> map,
			List<Object> params) {
		Conditions conditions = new Conditions().setKeyQuote(quote);
		for(Map.Entry<String, Object> e:map.entrySet()){
			if(e.getValue()!=null) {
				conditions.add(e.getKey(), "?", "=");
				params.add(e.getValue());
			}
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select 1 from")
		   .append(quote+table+quote)
		   .append(" where ")
		   .append(conditions.isEmpty()?"1":conditions);
		return sql.toString();
	}


}
