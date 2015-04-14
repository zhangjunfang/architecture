package com.transilink.framework.core.model.dbmeta;

import java.util.Enumeration;
import java.util.Properties;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class TypeResolver {
	private static final Properties typeMap = new Properties();

	public static String resolveType(String type, boolean useDefault) {
		if (null == type)
			return null;
		String s = typeMap.getProperty(type.toLowerCase());
		if (null == s) {
			if (useDefault)
				return "string";

			for (Enumeration e = typeMap.keys(); e.hasMoreElements();) {
				String key = e.nextElement().toString();
				if (type.toLowerCase().startsWith(key.toLowerCase()))
					return typeMap.getProperty(key);
			}
			return null;
		}

		return s;
	}

	static {
		typeMap.setProperty("float", "float");
		typeMap.setProperty("float unsigned zerofill", "float");
		typeMap.setProperty("double", "double");
		typeMap.setProperty("decimal", "bigdecimal");
		typeMap.setProperty("money", "bigdecimal");
		typeMap.setProperty("currency", "bigdecimal");
		typeMap.setProperty("int", "int");
		typeMap.setProperty("uniqueidentifier", "int");
		typeMap.setProperty("nvarchar", "string");
		typeMap.setProperty("tinyint", "byte");
		typeMap.setProperty("smallint", "short");
		typeMap.setProperty("mediumint", "int");
		typeMap.setProperty("int2", "int");
		typeMap.setProperty("int4", "long");
		typeMap.setProperty("int8", "long");
		typeMap.setProperty("int", "int");
		typeMap.setProperty("int identity", "int");
		typeMap.setProperty("bigint", "long");
		typeMap.setProperty("numeric", "int");
		typeMap.setProperty("number", "int");
		typeMap.setProperty("serial", "int");
		typeMap.setProperty("int unsigned", "int");
		typeMap.setProperty("smallint", "short");
		typeMap.setProperty("mediumint", "int");
		typeMap.setProperty("datetime", "datetime");
		typeMap.setProperty("datetime", "datetime");
		typeMap.setProperty("datetimetz", "datetime");
		typeMap.setProperty("typesysdatetime", "datetime");
		typeMap.setProperty("smalldatetime", "date");
		typeMap.setProperty("interval", "datetime");
		typeMap.setProperty("time", "time");
		typeMap.setProperty("date", "date");
		typeMap.setProperty("smalldate", "date");
		typeMap.setProperty("ntext", "string");
		typeMap.setProperty("text", "string");
		typeMap.setProperty("mediumtext", "string");
		typeMap.setProperty("mediumblob", "binary");
		typeMap.setProperty("longtext", "string");
		typeMap.setProperty("varchar", "string");
		typeMap.setProperty("varchar2", "string");
		typeMap.setProperty("char", "string");
		typeMap.setProperty("bpchar", "string");
		typeMap.setProperty("clob", "string");
		typeMap.setProperty("blob", "binary");
		typeMap.setProperty("bit", "boolean");
		typeMap.setProperty("bool", "boolean");
		typeMap.setProperty("varbinary", "binary");
		typeMap.setProperty("image", "binary");
		typeMap.setProperty("byte", "binary");
	}
}