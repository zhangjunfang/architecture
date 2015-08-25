/**
 * 
 */
package com.ocean.base.utils;

/**
 * 描述：字符串操作工具类
 * 
 * @author ocean 2015年8月20日 email：zhangjunfang0505@163.com
 */
public class StringUtil {
	public static String filterInvalidInformation(String temp) {
		if (temp == null || temp.trim().length() == 0 || temp.startsWith("#")) {
			return null;
		}
		if (temp.contains("#")) {
			temp = temp.trim();
			if (temp.length() == 1) {
				return null;
			} else {
				temp.substring(0, temp.indexOf("#"));
			}
		} else {
			return temp;
		}
		return null;
	}
}
