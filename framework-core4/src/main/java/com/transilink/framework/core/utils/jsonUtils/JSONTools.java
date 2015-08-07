package com.transilink.framework.core.utils.jsonUtils;

import java.util.Date;
import java.util.List;

import net.sf.ezmorph.object.DateMorpher;
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

/**
 * 
 * 描述：
 * 
 * @author ocean
 * 2015年4月15日
 *  email：zhangjunfang0505@163.com
 */
public class JSONTools {

	protected static Logger logger = Logger.getLogger(JSONTools.class);

	static {
		String[] dateFormats = new String[] { "yyyy-MM-dd",
				"yyyy-MM-dd HH:mm:ss" };
		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher(dateFormats));// 注册格式化日期的模式
	}

	/**
	 * 从json中取String
	 *
	 * @Title: getString
	 * @data:2013-6-25上午9:37:44
	 * @author:zhanghongliang
	 *
	 * @param json
	 * @param key
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
	 * json对象转bean
	 *
	 * @Title: JSONToBean
	 * @data:2013-7-3上午9:37:35
	 * @author:zhanghongliang
	 *
	 * @param jsonData
	 *            {@link net.sf.json.JSONObject}
	 * @param clazz
	 *            {@link Class}
	 * @param jsonConfig
	 *            {@link net.sf.json.JsonConfig}
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
	 * @Title: JSONToBean
	 * @data:2013-7-3上午9:40:06
	 * @author:zhanghongliang
	 *
	 * @param jsonData
	 *            {@link net.sf.json.JSONObject}json对象
	 * @param clazz
	 *            要转换的成的对象
	 * @param excludes
	 *            不包含的字段名称
	 * @param datePattern
	 *            日期的正则
	 * @param includeNull
	 *            是否包括null，空串字段
	 * @return
	 */
	public static final <T> T JSONToBean(JSONObject jsonData, Class<T> clazz,
			String[] excludes, String datePattern, Boolean includeNull) {
		JsonConfig jsonConfig = getJSConfig(excludes, datePattern, includeNull);
		return JSONToBean(jsonData, clazz, jsonConfig);
	}

	/**
	 * 取jsonConfig
	 *
	 * @Title: getJSConfig
	 * @data:2013-6-28上午11:07:28
	 * @author:zhanghongliang
	 *
	 * @param excludes
	 *            不包含的字段，哪些字段需要过滤掉
	 * @param datePattern
	 *            日期转换格式
	 * @param includeNull
	 *            是否包含值为null的字段，默认包含 true 包含，false不包含
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

	@SuppressWarnings("rawtypes")
	public static JSONArray parseToJSONObject(List list, JsonConfig jsonConfig) {
		JSONArray result = null;
		if (jsonConfig == null) {
			jsonConfig = getJSConfig(null, null, false);
		}
		if (null != list && list.size() > 0) {
			result = JSONArray.fromObject(list, jsonConfig);
		}
		if (result == null) {
			result = new JSONArray();
		}
		return result;
	}
}
