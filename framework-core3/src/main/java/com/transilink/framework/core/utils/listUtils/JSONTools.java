package com.transilink.framework.core.utils.listUtils;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.transilink.framework.core.utils.dataUtils.DateMorpherEx;
import com.transilink.framework.core.utils.stringUtils.JsonDateValueProcessor;

/**
 * @JSONUtil.java
 * @author ocean(zhangjufang0505@163.com)
 */
public class JSONTools {

	protected static Logger logger = Logger.getLogger(JSONTools.class);

	static {
		String[] dateFormats = new String[] { "yyyy-MM-dd HH:mm:ss" };
		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpherEx(dateFormats));// 注册格式化日期的模式
	}

	/**
	 * @return
	 */
	public static final String getString(JSONObject json, String key) {
		String result = "";
		Object obj = getObject(json, key);
		if (obj != null) {
			result = obj.toString();
		}

		return result;
	}

	public static final int getInt(JSONObject json, String key) {
		int result = 0;
		Object obj = getObject(json, key);
		if (obj != null) {
			result = NumberUtils.toInt(obj.toString(), result);
		}

		return result;
	}

	public static final boolean getBoolean(JSONObject json, String key) {
		boolean result = false;
		Object obj = getObject(json, key);
		if (obj != null) {
			result = BooleanUtils.toBoolean(obj.toString());
		}

		return result;
	}

	public static final Double getDouble(JSONObject json, String key) {
		double result = 0;
		Object obj = getObject(json, key);
		if (obj != null) {
			result = NumberUtils.toDouble(obj.toString(), result);
		}

		return result;
	}

	public static final JSONObject getJSONObject(JSONObject json, String key) {
		JSONObject result = null;
		Object obj = getObject(json, key);
		if (obj != null && obj instanceof JSONObject) {
			result = (JSONObject) obj;
		}

		return result;
	}

	public static final JSONArray getJSONArray(JSONObject json, String key) {
		JSONArray result = null;
		Object obj = getObject(json, key);
		if (obj != null && obj instanceof JSONArray) {
			result = (JSONArray) obj;
		}

		return result;
	}

	public static final Object getObject(JSONObject json, String key) {
		Object result = null;
		if (json != null && StringUtils.isNotEmpty(key)
				&& json.containsKey(key)) {
			result = json.get(key);
		}
		return result;
	}

	public static final <T> T JSONToBean(JSONObject jsonData, Class<T> clazz) {
		return JSONToBean(jsonData, clazz, null);
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T JSONToBean(JSONObject jsonData, Class<T> clazz,
			JsonConfig jsonConfig) {
		T result = null;
		if (jsonData == null || jsonData.size() == 0 || clazz == null) {
			return result;
		}
		if (jsonConfig == null) {
			jsonConfig = getJSConfig(null, null, false);
		}
		try {
			result = clazz.newInstance();
			result = (T) JSONObject.toBean(jsonData, result, jsonConfig);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return result;
	}

	/**
	 *
	 * @return
	 */
	public static final <T> T JSONToBean(JSONObject jsonData, Class<T> clazz,
			String[] excludes, String datePattern, Boolean includeNull) {
		JsonConfig jsonConfig = getJSConfig(excludes, datePattern, includeNull);
		return JSONToBean(jsonData, clazz, jsonConfig);
	}

	/**
	 * @return
	 */
	public static JsonConfig getJSConfig(String[] excludes, String datePattern,
			Boolean includeNull) {
		JsonConfig result = new JsonConfig();
		if (null != excludes)
			result.setExcludes(excludes);

		result.setIgnoreDefaultExcludes(false);
		result.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		result.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor(datePattern));
		if (includeNull != null && includeNull == false) {
			// 忽略属性值为null的字段
			result.setJsonPropertyFilter(new PropertyFilter() {
				public boolean apply(Object source, String name, Object value) {
					// 忽略birthday属性
					if (value == null) {
						return true;
					}
					return false;
				}
			});

			result.setJavaPropertyFilter(new PropertyFilter() {

				@Override
				public boolean apply(Object source, String name, Object value) {
					if (value == null || StringUtils.isBlank(value.toString())) {
						return true;
					}
					return false;
				}
			});
		}
		return result;
	}

	public JSONObject strToJSONObject(String str, JSONObject jsonObject) {
		JSONObject result = null;
		return result;
	}

	public static JSONObject parseToJSONObject(String str) {
		JSONObject result = null;
		if (StringUtils.isNotBlank(str) && str.startsWith("{")
				&& str.endsWith("}")) {
			result = JSONObject.fromObject(str);
		}
		if (result == null) {
			result = new JSONObject();
		}
		return result;
	}
}
