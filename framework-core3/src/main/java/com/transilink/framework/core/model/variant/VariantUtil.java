package com.transilink.framework.core.model.variant;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import oracle.sql.TIMESTAMP;

import com.transilink.framework.core.logs.LogEnabled;
import com.transilink.framework.core.utils.dataUtils.DateUtil;
import com.transilink.framework.core.utils.stringUtils.StringUtil;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public final class VariantUtil implements LogEnabled {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static SimpleDateFormat timeFormat = new SimpleDateFormat(
			"HH:mm:ss");

	private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static final Object toObject(byte value) {
		return new Byte(value);
	}

	public static final Object toObject(short value) {
		return new Short(value);
	}

	public static final Object toObject(int value) {
		return new Integer(value);
	}

	public static final Object toObject(long value) {
		return new Long(value);
	}

	public static final Object toObject(float value) {
		return new Float(value);
	}

	public static final Object toObject(double value) {
		return new Double(value);
	}

	public static final Object toObject(boolean value) {
		return new Boolean(value);
	}

	public static final Object toObject(Date value) {
		return value;
	}

	public static final Object toObject(BigDecimal value) {
		return value;
	}

	public static final Object toObject(String value) {
		return value;
	}

	public static final String parseString(Object value) {
		if (value == null)
			return null;
		if ((value instanceof Date))
			return DateUtil.toDateString((Date) value);
		return value.toString();
	}

	public static final byte parseByte(Object value) {
		if (value == null)
			return 0;
		if ((value instanceof Number))
			return ((Number) value).byteValue();
		if ((value instanceof Boolean))
			return (byte) (((Boolean) value).booleanValue() ? 1 : 0);
		String str;
		if ((str = value.toString()).equals(""))
			return 0;
		return Byte.parseByte(str);
	}

	public static final short parseShort(Object value) {
		if (value == null)
			return 0;
		if ((value instanceof Number))
			return ((Number) value).shortValue();
		if ((value instanceof Boolean))
			return (short) (((Boolean) value).booleanValue() ? 1 : 0);
		String str;
		if ((str = value.toString()).equals(""))
			return 0;
		return Short.parseShort(str);
	}

	public static final int parseInt(Object value) {
		if (value == null)
			return 0;
		if ((value instanceof Number))
			return ((Number) value).intValue();
		if ((value instanceof Boolean)) {
			if (((Boolean) value).booleanValue())
				return 1;
			return 0;
		}
		String str;
		if ((str = value.toString()).equals(""))
			return 0;
		return Integer.parseInt(str);
	}

	public static final long parseLong(Object value) {
		if (value == null)
			return 0L;
		if ((value instanceof Number))
			return ((Number) value).longValue();
		if ((value instanceof Boolean)) {
			if (((Boolean) value).booleanValue())
				return 1L;
			return 0L;
		}
		if ((value instanceof Date))
			return ((Date) value).getTime();
		String str;
		if ((str = value.toString()).equals(""))
			return 0L;
		return Long.parseLong(str);
	}

	public static final float parseFloat(Object value) {
		if (value == null)
			return 0.0F;
		if ((value instanceof Number))
			return ((Number) value).floatValue();
		if ((value instanceof Boolean)) {
			if (((Boolean) value).booleanValue())
				return 1.0F;
			return 0.0F;
		}
		String str;
		if ((str = value.toString()).equals(""))
			return 0.0F;
		return Float.parseFloat(str);
	}

	public static final double parseDouble(Object value) {
		if (value == null)
			return 0.0D;
		if ((value instanceof Number))
			return ((Number) value).doubleValue();
		if ((value instanceof Boolean)) {
			if (((Boolean) value).booleanValue())
				return 1.0D;
			return 0.0D;
		}
		String str;
		if ((str = value.toString()).equals(""))
			return 0.0D;
		return Double.parseDouble(str);
	}

	public static final BigDecimal parseBigDecimal(Object value) {
		if (value == null)
			return BigDecimal.valueOf(0L);
		if ((value instanceof BigDecimal))
			return (BigDecimal) value;
		if ((value instanceof Number))
			return BigDecimal.valueOf(((Number) value).longValue());
		if ((value instanceof Boolean))
			return BigDecimal.valueOf(((Boolean) value).booleanValue() ? 1L
					: 0L);
		String str;
		if ((str = value.toString()).equals(""))
			return BigDecimal.valueOf(0L);
		return new BigDecimal(str);
	}

	public static final boolean parseBoolean(String value) {
		if (value == null)
			return false;
		return (value.equalsIgnoreCase("true")) || (value.equals("1"))
				|| (value.equals("-1"));
	}

	public static final boolean parseBoolean(Object value) {
		if (value == null)
			return false;
		if ((value instanceof Boolean))
			return ((Boolean) value).booleanValue();
		return parseBoolean(value.toString());
	}

	private static boolean isNumber(String str) {
		int i = str.length();
		for (int j = 0; j < i; j++) {
			int k;
			if ((((k = str.charAt(j)) < '0') || (k > 57)) && (k != 46)
					&& ((j != 0) || (k != 45))) {
				return false;
			}
		}
		return true;
	}

	public static final Date parseDate(Object value) {
		if (value == null)
			return null;
		if ((value instanceof Date))
			return (Date) value;
		if ((value instanceof Number))
			return new Date(((Number) value).longValue());
		if ((value instanceof TIMESTAMP))
			try {
				return ((TIMESTAMP) value).timestampValue();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		String str;
		if (StringUtil.isValid(str = String.valueOf(value))) {
			if (isNumber(str)) {
				long l = Long.parseLong(str);
				return new Date(l);
			}
			int i = str.length();
			try {
				if (i < 19) {
					if (str.indexOf(":") > 0)
						return timeFormat.parse(str);
					return dateFormat.parse(str);
				}
				log.info("str:" + str);
				return dateTimeFormat.parse(str);
			} catch (ParseException ex) {
				ex.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public static final Object translate(int dataType, Object obj) {
		if ((obj == null)
				|| (((obj instanceof String)) && (((String) obj).length() == 0))) {
			if (dataType == 1)
				return obj;
			return null;
		}
		switch (dataType) {
		case 1:
			return parseString(obj);
		case 9:
			return toObject(parseBoolean(obj));
		case 10:
		case 11:
		case 12:
			return parseDate(obj);
		case 4:
			return toObject(parseInt(obj));
		case 7:
			return toObject(parseDouble(obj));
		case 5:
			return toObject(parseLong(obj));
		case 6:
			return toObject(parseFloat(obj));
		case 8:
			return parseBigDecimal(obj);
		case 2:
			return toObject(parseByte(obj));
		case 3:
			return toObject(parseShort(obj));
		}
		return obj;
	}
}