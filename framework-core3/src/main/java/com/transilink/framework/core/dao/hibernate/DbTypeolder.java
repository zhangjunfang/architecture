package com.transilink.framework.core.dao.hibernate;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class DbTypeolder {
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setDBType(String dbType) {
		contextHolder.set(dbType);
	}

	public static String getDBType() {
		return (String) contextHolder.get();
	}

	public static void clearDBType() {
		contextHolder.remove();
	}
}