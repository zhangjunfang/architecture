package com.transilink.framework.core.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class UrlMapping {
	public static Map<String, String> loadUrlMap = new ConcurrentHashMap<String, String>();

	public static Map<String, String> loadFrameworkMap = new ConcurrentHashMap<String, String>();
}