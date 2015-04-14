package com.transilink.framework.core.dao.dialect;

import org.hibernate.Hibernate;
import org.hibernate.dialect.Oracle10gDialect;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class TransilinkOracleDialect extends Oracle10gDialect {
	public TransilinkOracleDialect() {
		registerHibernateType(-1, Hibernate.STRING.getName());
		registerHibernateType(-1, Hibernate.TEXT.getName());
		registerHibernateType(3, Hibernate.BIG_INTEGER.getName());
		registerHibernateType(-1, 65535, "text");
	}
}