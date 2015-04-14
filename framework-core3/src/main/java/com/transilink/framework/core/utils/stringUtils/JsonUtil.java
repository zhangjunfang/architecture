package com.transilink.framework.core.utils.stringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;

import com.transilink.framework.core.logs.LogEnabled;
import com.transilink.framework.core.model.variant.VariantSet;
import com.transilink.framework.core.utils.clazzUtils.BeanUtils;

/**
 * json 工具类
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@SuppressWarnings("all")
public class JsonUtil implements LogEnabled {

	/**
	 * 将List转换为JSONArray对象,如果为空，则转为空对象[]
	 *
	 * @param list
	 *            对象集合
	 * @param names
	 *            转换为json对象的属性列表
	 * @return
	 */
	public static JSONArray toJSONArray(List list, String names[]) {

		JSONArray jsonArray = new JSONArray();
		if (!list.isEmpty()) {
			for (int i = 0, len = list.size(); i < len; i++) {
				jsonArray.add(toJSONObject(list.get(i), names));
			}
		}

		return jsonArray;

	}

	/**
	 * 将List转换为JSONArray对象,如果为空，则转为空对象[]
	 *
	 * @param list
	 *            对象集合
	 * @return
	 */
	public static JSONArray toJSONArray(List list) {

		JSONArray jsonArray = new JSONArray();
		if (!list.isEmpty()) {
			for (int i = 0, len = list.size(); i < len; i++) {
				jsonArray.add(toJSONObject(list.get(i), null));
			}
		}

		return jsonArray;

	}

	/**
	 * 将jsonArray转换为JSONObject,格式:{total:xx,data:[{name:value,name1:value1,...},
	 * ..,{}]}
	 *
	 * @param jsonArray
	 *            转换为json对象的属性列表
	 * @param possibleTotalCount
	 *            记录总数量
	 * @return
	 */
	public static JSONObject toJSONObject(JSONArray jsonArray,
			int possibleTotalCount) {
		JSONObject jsonObject = new JSONObject();
		JsonUtil.put(jsonObject, "total", possibleTotalCount);
		JsonUtil.put(jsonObject, "data", jsonArray);
		return jsonObject;

	}

	/**
	 * 将list转换为JSONObject,格式:{total:xx,data:[{name:value,name1:value1,...},..,{}
	 * ]}
	 *
	 * @param list
	 *            javabena对象集合
	 * @param names
	 *            转换为json对象的属性列表
	 * @param possibleTotalCount
	 *            记录总数
	 * @return
	 */
	public static JSONObject toJSONObject(List list, String names[],
			int possibleTotalCount) {

		JSONArray jsArray = toJSONArray(list, names);
		return toJSONObject(jsArray, possibleTotalCount);

	}

	/**
	 * 将list转换为JSONObject,格式:{total:xx,data:[{name:value,name1:value1,...},..,{}
	 * ]}
	 *
	 * @param list
	 *            javabena对象集合
	 * @param names
	 *            转换为json对象的属性列表
	 * @return
	 */
	public static JSONObject toJSONObject(List list, String names[]) {
		return toJSONObject(list, names, list.size());

	}

	/**
	 * 将list转换为JSONObject,格式:{total:xx,data:[{name:value,name1:value1,...},..,{}
	 * ]}
	 *
	 * @param list
	 *            javabena对象集合
	 * @param possibleTotalCount
	 *            记录数量
	 * @return
	 */
	public static JSONObject toJSONObject(List list, int possibleTotalCount) {

		JSONArray jsArray = toJSONArray(list, null);
		return toJSONObject(jsArray, possibleTotalCount);

	}

	/**
	 * 将list转换为JSONObject,格式:{total:xx,data:[{name:value,name1:value1,...},..,{}
	 * ]}
	 *
	 * @param list
	 *            javabena对象集合
	 * @return
	 */
	public static JSONObject toJSONObject(List list) {
		return toJSONObject(list, list.size());

	}

	/**
	 * 将javabean转换为JsonObject对象,如果为空，则转为空对象{}
	 *
	 * @param obj
	 *            javabean对象
	 * @param names
	 *            转换为json对象的属性列表
	 * @return
	 */
	public static JSONObject toJSONObject(Object obj, String names[]) {

		if (obj instanceof Map) {

			return mapToJsonObject((Map) obj, names);

		} else if (obj instanceof VariantSet) {

			return variantSetToJsonObject((VariantSet) obj, names);

		} else {
			return objectToJsonObject(obj, names);
		}

	}

	/**
	 * 将javabean转换为JsonObject对象,如果为空，则转为空对象{}
	 *
	 * @param obj
	 * @return
	 */
	public static JSONObject toJSONObject(Object obj) {

		return toJSONObject(obj, null);

	}

	/**
	 * 将object bean对象转换为JsonObject
	 *
	 * @param obj
	 * @param names
	 *            转换为json对象的属性列表
	 * @return
	 */
	private static JSONObject objectToJsonObject(Object obj, String[] names) {
		JSONObject jsonObject = new JSONObject();
		if (names == null || names.length == 0) {
			// TODO xuya
			// jsonObject = new JSONObject("");
		} else {
			for (int i = 0, len = names.length; i < len; i++) {
				try {
					Class propertyType = PropertyUtils.getPropertyType(obj,
							names[i]);
					if (propertyType == null) {
						continue;
					}
					put(jsonObject, names[i],
							BeanUtils.getProperty(obj, names[i]));
				} catch (Exception ex) {
					log.error(ex);
				}

			}

		}

		return jsonObject;

	}

	/**
	 * 将Variant对象转换为JsonObject
	 *
	 * @param variantSet
	 * @param names
	 *            转换为json对象的属性列表
	 * @return
	 */
	public static JSONObject variantSetToJsonObject(VariantSet variantSet,
			String[] names) {

		JSONObject jObject = new JSONObject();

		if (names == null || names.length == 0) {
			for (int i = 0, len = variantSet.count(); i < len; i++) {
				put(jObject, variantSet.indexToName(i), variantSet.getValue(i));
			}

		} else {
			for (int i = 0, len = variantSet.count(); i < len; i++) {
				String key = variantSet.indexToName(i);
				if (isExists(names, key)) {
					put(jObject, key, variantSet.getValue(i));
				}
			}
		}

		return jObject;

	}

	/**
	 * 将map对象转换为JsonObject
	 *
	 * @param map
	 * @param names
	 *            转换为json对象的属性列表
	 * @return
	 */
	public static JSONObject mapToJsonObject(Map map, String[] names) {

		JSONObject jObject = new JSONObject();

		if (names == null || names.length == 0) {
			for (Iterator<Map.Entry> iterator = map.entrySet().iterator(); iterator
					.hasNext();) {
				Map.Entry entry = iterator.next();
				try {
					jObject.put(entry.getKey().toString(), entry.getValue());
				} catch (JSONException ex) {
					log.error(ex);
				}
			}

		} else {
			for (Iterator<Map.Entry> iterator = map.entrySet().iterator(); iterator
					.hasNext();) {
				Map.Entry entry = iterator.next();
				if (isExists(names, entry.getKey().toString())) {
					try {
						jObject.put(entry.getKey().toString(), entry.getValue());
					} catch (JSONException ex) {
						log.error(ex);
					}
				}
			}
		}
		return jObject;

	}

	/**
	 * 判断是否存在
	 *
	 * @param names
	 * @param name
	 * @return
	 */
	private static boolean isExists(String[] names, String name) {
		boolean flag = false;
		for (int i = 0, len = names.length; i < len; i++) {
			if (name.equals(names[i])) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 设置json对象的属性值
	 *
	 * @param jsonObject
	 * @param key
	 * @param value
	 */
	public static void put(JSONObject jsonObject, String key, Object value) {

		try {
			if (value == null) {
				jsonObject.put(key, "");
			} else {
				jsonObject.put(key, value);
			}

		} catch (JSONException ex) {
			log.error(ex);
		}

	}

}
