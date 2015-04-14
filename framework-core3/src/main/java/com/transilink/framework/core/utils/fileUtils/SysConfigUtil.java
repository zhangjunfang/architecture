package com.transilink.framework.core.utils.fileUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.transilink.framework.core.logs.LogEnabled;

/**
 * 读取属性文件
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class SysConfigUtil implements LogEnabled {

	/**
	 * 保存装载的 /config.properties 里的内容
	 */
	static ResourceBundle CONFIG = null;

	/**
	 * 日志句柄
	 */
	static Logger log = Logger.getLogger(SysConfigUtil.class);

	private static final String FILE_NAME = "config/config";

	/**
	 * 初始化
	 */
	static {
		try {
			CONFIG = ResourceBundle.getBundle(FILE_NAME);
		} catch (Exception ignored) {
		}
	}

	/**
	 * 获取 classpath 下 /config.properties 里的配置信息
	 *
	 * @param key
	 *            根据关键字获取值
	 * @return 返回对应关键字的值
	 */
	public synchronized static String get(String key) {
		try {
			String _key = StringUtils.defaultIfEmpty(key, "");
			if (!CONFIG.containsKey(_key)) {
				return null;
			}
			String str = CONFIG.getString(_key);
			str = StringUtils.defaultIfEmpty(str, "");
			str = str.trim();
			return str;
		} catch (Exception ex) {
			log.error(ExceptionUtils.getFullStackTrace(ex));
		}
		return null;
	}

	public static Map<String, String> getAllConfig() {
		// 加载配置文件，以后需要移到配置表的service中
		Map<String, String> map = new HashMap<String, String>();
		for (String key : CONFIG.keySet()) {
			map.put(StringUtils.trim(key),
					StringUtils.trim(CONFIG.getString(key)));
		}
		return map;
	}
}
