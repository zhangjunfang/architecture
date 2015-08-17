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
import java.sql.SQLException;

import org.framework.core.single.table.orm.dialect.Dialect;

/**
 * A session is created every time you use Model,Model or Jodb to interact with your data base.
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class Session {

	private Connection connection;
	private Dialect dialect;
	private boolean autoCommit = false;
	private int level = Connection.TRANSACTION_READ_COMMITTED;
	private boolean reportSql = false;
	private SqlReporter sqlReporter = new SystemSqlReporter();
	
	public Session(Ds ds) {
		this(ds.getConnection(), ds.getDialect());
	}
	
	public Session(Connection con,Dialect dialect) {
		this.connection = con;
		this.dialect = dialect;
	}

	public Connection getConnection() {
		return connection;
	}

	public Dialect getDialect() {
		return dialect;
	}

	public void setConnection(Connection con) {
		this.connection = con;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	public SqlReporter getSqlReporter() {
		return sqlReporter;
	}

	public void setSqlReporter(SqlReporter sqlReporter) {
		this.sqlReporter = sqlReporter;
	}

	public boolean reportSql(String sql) {
		if(reportSql&&sqlReporter!=null){
			sqlReporter.report(sql);
			return true;
		}
		return false;
	}
	
	public void close() {
		DbUtil.close(connection);
	}
	
	public boolean isClosed() {
		if(connection!=null){
			try {
				return connection.isClosed();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}else {
			return true;
		}
	}
	
	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
		if(!isClosed())
			try {
				connection.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public boolean isAutoCommit() {
		if(connection!=null)
			try {
				autoCommit = connection.getAutoCommit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return autoCommit;
	}
	
	public void setLevel(int level) {
		this.level = level;
		if(connection!=null)
			try {
				connection.setTransactionIsolation(level);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public int getLevel() {
		return level;
	}
	
	public boolean reportSql() {
		return reportSql;
	}

	public void setReportSql(boolean reportSql) {
		this.reportSql = reportSql;
	}

	public void commit() {
		if(autoCommit==false){
			if(connection!=null)
				try {
					connection.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void rollBack() {
		if(connection!=null)
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
}
