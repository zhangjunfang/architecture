/**
 * 
 */
package com.ocean.base.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * 描述：
 * 
 * @author ocean
 * 2015年8月20日
 *  email：zhangjunfang0505@163.com
 */
public class ClassLoaderUtil {
	public static Enumeration<URL> getURL(String dir) {
		try {
			Enumeration<URL> urls;
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			if (classLoader != null) {
				urls = classLoader.getResources(dir);
			} else {
				urls = ClassLoader.getSystemResources(dir);
			}
			return urls;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
