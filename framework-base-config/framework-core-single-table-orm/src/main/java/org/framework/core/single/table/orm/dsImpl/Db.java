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

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.framework.core.single.table.orm.core.Ds;
import org.framework.core.single.table.orm.dialect.DefaultDialect;
import org.framework.core.single.table.orm.dialect.Dialect;
import org.framework.core.single.table.orm.dialect.MysqlDialect;
import org.framework.core.single.table.orm.dialect.OracleDialect;
import org.framework.core.single.table.orm.dialect.PostgreSqlDialect;
import org.framework.core.single.table.orm.dialect.SqlServerDialect;
import org.framework.core.single.table.orm.dialect.SqliteDialect;
import org.framework.core.single.table.orm.util.Prop;
import org.framework.core.single.table.orm.util.Str;

/**
 * Db(none-pooled data source) implements Ds.
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class Db implements Ds {

	private String jdbcUrl;
	private String user;
	private String password;
	private String driverClass = "com.mysql.jdbc.Driver";
	private Dialect dialect;
	
	public Db(String jdbcUrl,String user,String password,String driverClass) {
		this.jdbcUrl = jdbcUrl;
		this.user = user;
		this.password = password;
		this.driverClass = Str.isNOB(driverClass)?this.driverClass:driverClass;
	}
	
	public Db(String jdbcUrl,String user,String password) {
		this(jdbcUrl, user, password, null);
	}
	
	public Db(String file) {
		this(new Prop(file).getProperties());
	}
	
	public Db(File file) {
		this(new Prop(file).getProperties());
	}
	
	public Db(Properties properties) {
		this(properties.getProperty("jdbcUrl"), properties.getProperty("user"), properties.getProperty("password"), properties.getProperty("driverClass"));
	}
	
	private void detectDialect() {
		String driver = driverClass.toLowerCase();
		if(driver.contains("mysql")){
			dialect = new MysqlDialect();
		}else if(driver.contains("oracle")) {
			dialect = new OracleDialect();
		}else if(driver.contains("sqlserver")) {
			dialect = new SqlServerDialect();
		}else if(driver.contains("sqlite")) {
			dialect = new SqliteDialect();
		}else if(driver.contains("postgresql")) {
			dialect = new PostgreSqlDialect();
		}else{
			dialect = new DefaultDialect();
			System.out.println("Warning:unrecognized driverClass '"+driverClass+"'. Dialect detector assign a DefaultDialect,which may not function correctly.");
		}
	}
	
	@Override
	public Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(driverClass).newInstance();
			con = DriverManager.getConnection(jdbcUrl, user, password);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return con;
	}

	@Override
	public Dialect getDialect() {
		if(dialect==null)
			detectDialect();
		return dialect;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

}
