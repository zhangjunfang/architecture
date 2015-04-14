package com.transilink.framework.core.model.variant;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public final class DataType {
	public static final String UNKNOWN_NAME = "";
	public static final int UNKNOWN = 0;
	public static final String STRING_NAME = "string";
	public static final int STRING = 1;
	public static final String BYTE_NAME = "byte";
	public static final int BYTE = 2;
	public static final String SHORT_NAME = "short";
	public static final int SHORT = 3;
	public static final String INT_NAME = "int";
	public static final int INT = 4;
	public static final String LONG_NAME = "long";
	public static final int LONG = 5;
	public static final String FLOAT_NAME = "float";
	public static final int FLOAT = 6;
	public static final String DOUBLE_NAME = "double";
	public static final int DOUBLE = 7;
	public static final String BIGDECIMAL_NAME = "bigdecimal";
	public static final int BIGDECIMAL = 8;
	public static final String BOOLEAN_NAME = "boolean";
	public static final int BOOLEAN = 9;
	public static final String DATE_NAME = "date";
	public static final int DATE = 10;
	public static final String TIME_NAME = "time";
	public static final int TIME = 11;
	public static final String DATETIME_NAME = "datetime";
	public static final int DATETIME = 12;
	public static final String BINARY_NAME = "binary";
	public static final int BINARY = 20;
	static Class booleanClazz = getClazz("java.lang.Boolean");

	static Class byteClazz = getClazz("java.lang.Byte");

	static Class doubleClazz = getClazz("java.lang.Double");

	static Class floatClazz = getClazz("java.lang.Float");

	static Class integerClazz = getClazz("java.lang.Integer");

	static Class longClazz = getClazz("java.lang.Long");

	static Class numberClazz = getClazz("java.lang.Number");

	static Class shortClazz = getClazz("java.lang.Short");

	static Class stringClazz = getClazz("java.lang.String");

	static Class bigDecimalClazz = getClazz("java.math.BigDecimal");

	static Class sqlDateClazz = getClazz("java.sql.Date");

	static Class timeClazz = getClazz("java.sql.Time");

	static Class timeStampClazz = getClazz("java.sql.Timestamp");

	static Class dateClazz = getClazz("java.util.Date");

	public static final boolean isNumberType(int dataType) {
		return (2 <= dataType) && (dataType <= 8);
	}

	public static final boolean isDateType(int dataType) {
		return (10 <= dataType) && (dataType <= 12);
	}

	public static final boolean isBaseDataType(Class clazz) {
		if (clazz.isPrimitive()) {
			return true;
		}
		if (clazz.equals(stringClazz)) {
			return true;
		}
		if (numberClazz.isAssignableFrom(clazz)) {
			return true;
		}
		if (booleanClazz.isAssignableFrom(clazz)) {
			return true;
		}
		return dateClazz.isAssignableFrom(clazz);
	}

	public static final int parse(Class clazz) {
		if (clazz.equals(stringClazz))
			return 1;
		if (clazz.equals(Integer.TYPE))
			return 4;
		if (clazz.equals(Boolean.TYPE))
			return 9;
		if (clazz.equals(Float.TYPE))
			return 6;
		if (clazz.equals(dateClazz))
			return 10;
		if (clazz.equals(sqlDateClazz))
			return 10;
		if (clazz.equals(timeClazz))
			return 11;
		if (clazz.equals(timeStampClazz))
			return 12;
		if (clazz.equals(Long.TYPE))
			return 5;
		if (clazz.equals(Double.TYPE))
			return 7;
		if (clazz.equals(Byte.TYPE))
			return 2;
		if (clazz.equals(Short.TYPE))
			return 3;
		if (clazz.equals(bigDecimalClazz))
			return 8;
		if (clazz.equals(integerClazz))
			return 4;
		if (clazz.equals(booleanClazz))
			return 9;
		if (clazz.equals(floatClazz))
			return 6;
		if (clazz.equals(longClazz))
			return 5;
		if (clazz.equals(doubleClazz))
			return 7;
		if (clazz.equals(byteClazz))
			return 2;
		if (clazz.equals(shortClazz))
			return 3;
		return 1;
	}

	public static final int nameToType(String dataTypeName) {
		if ("string".equals(dataTypeName))
			return 1;
		if ("boolean".equals(dataTypeName))
			return 9;
		if ("int".equals(dataTypeName))
			return 4;
		if ("float".equals(dataTypeName))
			return 6;
		if ("date".equals(dataTypeName))
			return 10;
		if ("time".equals(dataTypeName))
			return 11;
		if ("datetime".equals(dataTypeName))
			return 12;
		if ("double".equals(dataTypeName))
			return 7;
		if ("long".equals(dataTypeName))
			return 5;
		if ("byte".equals(dataTypeName))
			return 2;
		if ("short".equals(dataTypeName))
			return 3;
		if ("bigdecimal".equals(dataTypeName))
			return 8;
		if ("binary".equals(dataTypeName))
			return 20;
		return 0;
	}

	public static final String typeToName(int type) {
		switch (type) {
		case 1:
			return "string";
		case 9:
			return "boolean";
		case 4:
			return "int";
		case 6:
			return "float";
		case 10:
			return "date";
		case 11:
			return "time";
		case 12:
			return "datetime";
		case 7:
			return "double";
		case 5:
			return "long";
		case 2:
			return "byte";
		case 3:
			return "short";
		case 8:
			return "bigdecimal";
		case 20:
			return "binary";
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
		}
		return "";
	}

	public static final Class typeToClass(int dataType) {
		switch (dataType) {
		case 1:
			return stringClazz;
		case 9:
			return booleanClazz;
		case 4:
			return integerClazz;
		case 6:
			return floatClazz;
		case 10:
		case 11:
		case 12:
			return dateClazz;
		case 7:
			return doubleClazz;
		case 5:
			return longClazz;
		case 2:
			return byteClazz;
		case 3:
			return shortClazz;
		case 8:
			return bigDecimalClazz;
		case 20:
			return null;
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
		}
		return null;
	}

	static final Class getClazz(String clazzName) {
		try {
			return Class.forName(clazzName);
		} catch (ClassNotFoundException ex) {
			throw new NoClassDefFoundError(ex.getMessage());
		}
	}
}