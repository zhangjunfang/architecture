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

import java.sql.Connection;
import java.util.List;

/**
 * Java Object database.
 * 
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class Jodb {
	
	private Ds ds;
	private Session session;
	private boolean autoCommit = true;
	private int sessionLevel = Connection.TRANSACTION_READ_COMMITTED;
	
	public Jodb(Ds ds) {
		this.setDs(ds);
	}
	
	public Ds getDs() {
		return ds;
	}

	public void setDs(Ds ds) {
		this.ds = ds;
	}

	public Jodb open() {
		if(isClosed()){
			session = new Session(ds);
			setSessionLevel(sessionLevel);
			setAutoCommit(autoCommit);
		}
		if(isClosed())
			throw new JodbException("Errors occurs when Jodb try to open datasource.Please check your Ds implementation or configuration and make sure Jodb can get connections from the datasource.");
		return this;
	}
	
	public Session getSession() {
		return session;
	}

	public void close() {
		if(session!=null){
			session.close();
			session = null;
		}
	}
	
	public boolean isClosed() {
		if(session==null)
			return true;
		else
			return session.isClosed();
	}
	
	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
		if(!isClosed())
			session.setAutoCommit(autoCommit);
	}
	
	public boolean isAutoCommit() {
		if(!isClosed())
			autoCommit = session.isAutoCommit();
		return autoCommit;
	}
	
	public void setSessionLevel(int sessionLevel) {
		this.sessionLevel = sessionLevel;
		if(!isClosed())
			session.setLevel(sessionLevel);
	}
	
	public int getSessionLevel() {
		return sessionLevel;
	}
	
	public void commit() {
		if((!isClosed())&&(!isAutoCommit()))
				session.commit();
	}
	
	public void rollBack() {
		if((!isClosed())&&(!isAutoCommit()))
			session.rollBack();
	}
	
	public boolean insert(Record record) {
		return record.insert(session);
	}
	
	public <M extends Model<M>> boolean insert(M model) {
		return model.insert(session);
	}
	
	public <M> boolean insert(M bean) {
		return insert(beanToRecord(bean));
	}
	
	public boolean delete(Record record) {
		return record.delete(session);
	}
	
	public <M extends Model<M>> boolean delete(M model) {
		return model.delete(session);
	}
	
	public <M> boolean delete(M bean) {
		return delete(beanToRecord(bean));
	}
	
	public boolean update(Record target,Record replacement) {
		return replacement.update(target, session);
	}
	
	public <M extends Model<M>> boolean update(M target,M replacement) {
		return replacement.update(target,session);
	}
	
	public <M> boolean update(M target,M replacement) {
		return update(beanToRecord(target), beanToRecord(replacement));
	}
	
	public List<Record> select(Record record) {
		return record.select(session);
	}
	
	public Record selectFirst(Record record) {
		List<Record> records = select(record);
		if(records==null)
			return null;
		return records.get(0);
	}
	
	public <M extends Model<M>> List<M> select(M model) {
		return model.select(session);
	}
	
	public <M extends Model<M>> M selectFirst(M model) {
		List<M> models = select(model);
		if(models==null)
			return null;
		return models.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public <M> List<M> select(M bean) {
		List<Record> records = select(beanToRecord(bean));
		return recordsToBeans(records, (Class<M>)bean.getClass());
	}
	
	public <M> M selectFirst(M bean) {
		List<M> beans = select(bean);
		if(beans==null)
			return null;
		return beans.get(0);
	}
	
	public List<Record> paginate(Record record,int pageNumber,int pageSize) {
		return record.paginate(pageNumber, pageSize, session);
	}
	
	public <M extends Model<M>> List<M> paginate(M model,int pageNumber,int pageSize) {
		return model.paginate(pageNumber,pageSize,session);
	}
	
	@SuppressWarnings("unchecked")
	public <M> List<M> paginate(M bean,int pageNumber,int pageSize) {
		List<Record> records = paginate(beanToRecord(bean),pageNumber,pageSize);
		return recordsToBeans(records, (Class<M>)bean.getClass());
	}
	
	public boolean validate(Record record) {
		return record.validate(session);
	}
	
	public <M extends Model<M>> boolean validate(M model) {
		return model.validate(session);
	}
	
	public <M> boolean validate(M bean){
		return validate(beanToRecord(bean));
	}
	
	public long count(Record record) {
		return record.count(session);
	}
	
	public <M extends Model<M>> long count(M model) {
		return model.count(session);
	}
	
	public <M> long count(M bean){
		return count(beanToRecord(bean));
	}
	
	public static <M> Record beanToRecord(M bean) {
		return new Record(JodbConfig.getTable(bean.getClass())).fromBean(bean, JodbConfig.getColumnFieldDuplexMap(bean.getClass()).invert());
	}
	
	public static <M> M recordToBean(Record record,Class<M> beanClass) {
		return record.toBean(beanClass, JodbConfig.getColumnFieldDuplexMap(beanClass));
	}
	
	public static <M> List<M> recordsToBeans(List<Record> records,Class<M> beanClass) {
		return Record.toBeans(beanClass, records, JodbConfig.getColumnFieldDuplexMap(beanClass));
	}
}
