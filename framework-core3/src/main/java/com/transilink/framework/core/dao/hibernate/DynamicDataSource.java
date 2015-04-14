package com.transilink.framework.core.dao.hibernate;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class DynamicDataSource extends AbstractRoutingDataSource implements
		ApplicationContextAware {
	private Logger log = Logger.getLogger(getClass());
	public static Map<Object, Object> _targetDataSources;
	private ApplicationContext ctx;

	protected Object determineCurrentLookupKey() {
		String dataSourceName = DBContextHolder.getDBType();
		this.log.info("dataSourceName:" + dataSourceName);
		if (dataSourceName == null) {
			dataSourceName = "dataSource";
		} else {
			try {
				selectDataSource(dataSourceName);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
			if (dataSourceName.equals("0"))
				dataSourceName = "dataSource";
		}
		this.log.debug("-------->切换至数据源" + dataSourceName);
		return dataSourceName;
	}

	public void setTargetDs(Map<Object, Object> targetDataSources) {
		_targetDataSources = targetDataSources;
		super.setTargetDataSources(_targetDataSources);
		super.afterPropertiesSet();
	}

	public void addTargetDataSource(String key, ComboPooledDataSource dataSource) {
		_targetDataSources.put(key, dataSource);
		setTargetDs(_targetDataSources);
	}

	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.ctx = ctx;
	}

	public ComboPooledDataSource createDataSource(String driverClassName,
			String url, String username, String password)
			throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		ComboPooledDataSource parent = (ComboPooledDataSource) this.ctx
				.getBean("parentDataSource");

		dataSource.setJdbcUrl(url);
		dataSource.setUser(username);
		dataSource.setPassword(password);
		dataSource.setDriverClass(driverClassName);
		dataSource.setMaxPoolSize(parent.getMaxPoolSize());
		dataSource.setMinPoolSize(parent.getMinPoolSize());
		dataSource.setAcquireIncrement(parent.getAcquireIncrement());
		dataSource.setInitialPoolSize(parent.getInitialPoolSize());
		return dataSource;
	}


	public void selectDataSource(String serverId) throws PropertyVetoException {
		Object sid = DBContextHolder.getDBType();
		if ("0".equals(serverId)) {
			DBContextHolder.setDBType("0");
			return;
		}
		if (null == _targetDataSources) {
			_targetDataSources = new HashMap();
		}
		Object obj = _targetDataSources.get(serverId);
		if ((obj != null) && (sid.equals(serverId))) {
			return;
		}
		ComboPooledDataSource dataSource = getDataSource(serverId);
		if (null != dataSource)
			setDataSource(serverId, dataSource);
	}

	public ComboPooledDataSource getDataSource(String serverId)
			throws PropertyVetoException {
		selectDataSource("0");
		determineCurrentLookupKey();
		Connection conn = null;
		HashMap map = null;
		try {
			conn = getConnection();
			PreparedStatement ps = conn
					.prepareStatement("select * from t_data_source  where tenant_id=?");

			ps.setString(1, serverId);

			ResultSet rs = ps.executeQuery();
			map = new HashMap();
			while (rs.next()) {
				map.put("DBS_DriverClassName",
						rs.getString("DRIVER_CLASS_NAME"));

				map.put("DBS_URL", rs.getString("URL"));
				map.put("DBS_UserName", rs.getString("USER_NAME"));
				map.put("DBS_Password", rs.getString("PASSWORD"));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			this.log.error(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				this.log.error(e);
			}
		}
		if (null != map) {
			DbTypeolder.setDBType("Oracle");
			String driverClassName = map.get("DBS_DriverClassName").toString();
			String url = map.get("DBS_URL").toString();
			String userName = map.get("DBS_UserName").toString();
			String password = map.get("DBS_Password").toString();
			ComboPooledDataSource dataSource = createDataSource(
					driverClassName, url, userName, password);

			return dataSource;
		}
		return null;
	}

	public void setDataSource(String serverId, ComboPooledDataSource dataSource) {
		addTargetDataSource(serverId, dataSource);
		DBContextHolder.setDBType(serverId);
	}
}