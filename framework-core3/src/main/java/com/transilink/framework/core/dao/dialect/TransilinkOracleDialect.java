package com.transilink.framework.core.dao.dialect;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.type.StandardBasicTypes;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class TransilinkOracleDialect extends Oracle10gDialect {
	public TransilinkOracleDialect() {
		registerHibernateType(-1, StandardBasicTypes.STRING.getName());
		registerHibernateType(-1, StandardBasicTypes.TEXT.getName());
		registerHibernateType(3, StandardBasicTypes.BIG_INTEGER.getName());
		registerHibernateType(-1, 65535, "text");
	}
}