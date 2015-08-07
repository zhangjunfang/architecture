package com.transilink.framework.core.rest.velocity;

import java.io.File;
import java.util.ResourceBundle;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class ConfigUtil {
	private static final String SYSTEM_CONFIG_PROPERTIY = "config"
			+ File.separator + "velocity" + File.separator + "system_config";

	public static String getItem(String key) {
		String result = "";
		try {
			ResourceBundle resourceBundle = ResourceBundle
					.getBundle(SYSTEM_CONFIG_PROPERTIY);
			result = resourceBundle.getString(key);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
}