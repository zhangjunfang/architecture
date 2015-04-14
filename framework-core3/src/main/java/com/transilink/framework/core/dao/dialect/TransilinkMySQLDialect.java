package com.transilink.framework.core.dao.dialect;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class TransilinkMySQLDialect extends MySQL5Dialect {
	public TransilinkMySQLDialect() {
		registerHibernateType(-1, Hibernate.STRING.getName());
		registerHibernateType(-1, Hibernate.TEXT.getName());
		registerHibernateType(3, Hibernate.BIG_INTEGER.getName());
		registerHibernateType(-1, 65535, "text");
	}
}