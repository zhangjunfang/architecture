package com.transilink.framework.core.handler;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.EmptyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

import com.transilink.framework.core.utils.fileUtils.SysConfigUtil;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class MySQLInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = -5182264670201560449L;

	static String[] DBTABLES = null;

	static {
		DBTABLES = SysConfigUtil.get("db.tables").split(",");
	}

	@Autowired
	private HttpServletRequest request;

	@Override
	public String onPrepareStatement(String sql) {
		// HttpServletRequest request = ((ServletRequestAttributes)
		// RequestContextHolder.getRequestAttributes()).getRequest();
		sql = transSQL(sql);
		return super.onPrepareStatement(sql);
	}

	protected String getTenantName() {
		return request.getParameter("tenant");
	}

	protected String[] getDbTables() {
		String[] table_names = DBTABLES;
		return table_names;
	}

	protected String transSQL(String sql) {
		String tenant_name = getTenantName();
		if (null != tenant_name) {
			String[] table_names = getDbTables();
			for (String table_name : table_names) {
				sql = sql.replaceAll("(?i) " + table_name + " ", " "
						+ tenant_name + "__" + table_name + " ");
			}
		}
		return sql;
	}
}
