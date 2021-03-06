package com.transilink.framework.core.dao.dialect;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.type.StandardBasicTypes;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class TransilinkMySQLDialect extends MySQL5Dialect {
	public TransilinkMySQLDialect() {
		registerHibernateType(-1, StandardBasicTypes.STRING.getName());
		registerHibernateType(-1, StandardBasicTypes.TEXT.getName());
		registerHibernateType(3, StandardBasicTypes.BIG_INTEGER.getName());
		registerHibernateType(-1, 65535, "text");
	}
}