package com.transilink.framework.core.utils.stringUtils;

import java.util.Hashtable;
import java.util.Map;

/**
 * <p>
 * 注册全局变量的类
 * </p>
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class GlobalVariant {

	private static Map<String, String> variantMap = new Hashtable<String, String>();

	private GlobalVariant() {
	}

	/**
	 * <p>
	 * Title: registerVariant
	 * </p>
	 * <p>
	 * Description: 注册变量
	 * </p>
	 *
	 * @param name
	 *            变量名
	 * @param value
	 *            变量值
	 */
	public static void registerVariant(String name, String value) {
		variantMap.put(name, value);
	}

	/**
	 * <p>
	 * Title: getVariant
	 * </p>
	 * <p>
	 * Description: 获取变量值
	 * </p>
	 *
	 * @param name
	 *            变量名
	 * @return String 变量值
	 */
	public static String getVariant(String name) {
		return (String) variantMap.get(name);
	}

	/**
	 * <p>
	 * Title: getVariant
	 * </p>
	 * <p>
	 * Description: 获取变量值
	 * </p>
	 *
	 * @param name
	 *            变量名
	 * @param defaultValue
	 *            设定默认值
	 * @return String 变量值
	 */
	public static String getVariant(String name, String defaultValue) {
		String value = (String) variantMap.get(name);
		if (StringUtil.isEmpty(value)) {
			return defaultValue;
		} else {
			return value;
		}
	}

	/**
	 * <p>
	 * Title: removeVariant
	 * </p>
	 * <p>
	 * Description: 删除key值为name的项
	 * </p>
	 *
	 * @param name
	 *            变量名
	 * @return String 变量值
	 */
	public static String removeVariant(String name) {
		return (String) variantMap.remove(name);
	}

	/**
	 * <p>
	 * Title: addVariantValue
	 * </p>
	 * <p>
	 * Description: 在hash中添加一个新值，如果原先存在指定key的项，则将新值添加到原先项的字符串的后面，否则添加一个
	 * 指定key和value的新项
	 * </p>
	 *
	 * @param name
	 *            变量名
	 * @param newValue
	 *            追加的新值
	 */
	public static void addVariantValue(String name, String newValue) {
		if (variantMap.containsKey(name)) {
			String message = (String) variantMap.get(name);
			message = message + newValue;
			variantMap.remove(name);
			variantMap.put(name, message);
		} else {
			variantMap.put(name, newValue);
		}
	}

	/**
	 * <p>
	 * Title: haveVariant
	 * </p>
	 * <p>
	 * Description: 判断hash中是否存在指定key的项
	 * </p>
	 *
	 * @param name
	 *            变量名
	 * @return boolean true/false
	 */
	public static boolean haveVariant(String name) {
		return variantMap.containsKey(name);
	}

	/**
	 * <p>
	 * Title: size
	 * </p>
	 * <p>
	 * Description: 返回注册全局变量的个数
	 * </p>
	 *
	 * @return int 注册变量的个数
	 */
	public static int size() {
		return variantMap.size();
	}
}
