/**
 * 
 */
package org.framework.core.spi.extension;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.ocean.base.utils.ClassLoaderUtil;
import com.ocean.base.utils.StringUtil;

/**
 * 描述：jar包中查找SPI资源
 * 
 * @author ocean 2015年8月20日 email：zhangjunfang0505@163.com
 */
public class JarSPI {

	public static final Map<Class<?>, Set<Class<?>>> JAR_MAP = new ConcurrentHashMap<Class<?>, Set<Class<?>>>(
			512);
	// 加载jar中的资源
	public static void loadJarFile(Class<?> type, String dir) {
		Enumeration<URL> urls = ClassLoaderUtil.getURL(dir + "/" + type.getName());
		while (urls.hasMoreElements()) {
			URL config = urls.nextElement();
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(config.openStream(), "utf-8"))) {
				String line = null;
				Set<Class<?>> set = null;
				while ((line = reader.readLine()) != null) {
					line = StringUtil.filterInvalidInformation(line);
					if (line != null) {
						if (JAR_MAP.containsKey(type)) {
							set = JAR_MAP.get(type);
							set.add(Class.forName(line));
						} else {
							set = new HashSet<Class<?>>(8);
							set.add(Class.forName(line));
							JAR_MAP.putIfAbsent(type, set);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
