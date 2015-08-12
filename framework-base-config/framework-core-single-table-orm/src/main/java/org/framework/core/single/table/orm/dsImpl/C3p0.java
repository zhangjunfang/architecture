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
package org.framework.core.single.table.orm.dsImpl;

import java.beans.PropertyVetoException;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.framework.core.single.table.orm.util.Prop;
import org.framework.core.single.table.orm.util.Str;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * C3p0 data source.
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class C3p0 extends Db {

	private int maxPoolSize = 100;
	private int minPoolSize = 10;
	private int initialPoolSize = 10;
	private int maxIdleTime = 20;
	private int acquireIncrement = 2;
	
	private ComboPooledDataSource dataSource;
	
	public C3p0(String jdbcUrl, String user, String password, String driverClass, Integer maxPoolSize, Integer minPoolSize, Integer initialPoolSize, Integer maxIdleTime, Integer acquireIncrement) {
		super(jdbcUrl, user, password, driverClass);
		this.maxPoolSize = maxPoolSize != null ? maxPoolSize : this.maxPoolSize;
		this.minPoolSize = minPoolSize != null ? minPoolSize : this.minPoolSize;
		this.initialPoolSize = initialPoolSize != null ? initialPoolSize : this.initialPoolSize;
		this.maxIdleTime = maxIdleTime != null ? maxIdleTime : this.maxIdleTime;
		this.acquireIncrement = acquireIncrement != null ? acquireIncrement : this.acquireIncrement;
	}
	
	public C3p0(String jdbcUrl, String user, String password) {
		this(jdbcUrl, user, password, null);
	}
	
	public C3p0(String jdbcUrl, String user, String password, String driverClass) {
		this(jdbcUrl, user, password, driverClass, null, null, null, null, null);
	}
	
	public C3p0(Properties properties) {
		super(properties);
		this.maxPoolSize = Str.isNOB(properties.getProperty("maxPoolSize"))?this.maxPoolSize:toInt(properties.getProperty("maxPoolSize"));
		this.minPoolSize = Str.isNOB(properties.getProperty("minPoolSize"))?this.minPoolSize:toInt(properties.getProperty("minPoolSize"));
		this.initialPoolSize = Str.isNOB(properties.getProperty("initialPoolSize"))?this.initialPoolSize:toInt(properties.getProperty("initialPoolSize"));
		this.maxIdleTime = Str.isNOB(properties.getProperty("maxIdleTime"))?this.maxIdleTime:toInt(properties.getProperty("maxIdleTime"));
		this.acquireIncrement = Str.isNOB(properties.getProperty("acquireIncrement"))?this.acquireIncrement:toInt(properties.getProperty("acquireIncrement"));
	}
	
	public C3p0(String file) {
		this(new Prop(file).getProperties());
	}
	
	public C3p0(File file) {
		this(new Prop(file).getProperties());
	}
	
	private Integer toInt(String str) {
		return Integer.parseInt(str);
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	@Override
	public Connection getConnection() {
		if(dataSource==null){
			dataSource = new ComboPooledDataSource();
			dataSource.setJdbcUrl(getJdbcUrl());
			dataSource.setUser(getUser());
			dataSource.setPassword(getPassword());
			try {dataSource.setDriverClass(getDriverClass());}
			catch (PropertyVetoException e) {dataSource = null;e.printStackTrace(); throw new RuntimeException(e);} 
			dataSource.setMaxPoolSize(maxPoolSize);
			dataSource.setMinPoolSize(minPoolSize);
			dataSource.setInitialPoolSize(initialPoolSize);
			dataSource.setMaxIdleTime(maxIdleTime);
			dataSource.setAcquireIncrement(acquireIncrement);
		}
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return con;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public int getInitialPoolSize() {
		return initialPoolSize;
	}

	public void setInitialPoolSize(int initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}

	public int getMaxIdleTime() {
		return maxIdleTime;
	}

	public void setMaxIdleTime(int maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public int getAcquireIncrement() {
		return acquireIncrement;
	}

	public void setAcquireIncrement(int acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}

}
