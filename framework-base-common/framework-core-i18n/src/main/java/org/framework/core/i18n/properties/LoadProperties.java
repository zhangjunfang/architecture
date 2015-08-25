/**
 * 
 */
package org.framework.core.i18n.properties;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述： 1.默认加载classpath下的baseName为message的国际化资源文件
 * 
 * 2.资源文件命名格式：message_语言代码_国家或者地区代码.properties
 * 
 * 3.约定大于规范的原则 注意： 1.自动扫描classpath下符合资源文件命名格式，任意深度的文件 2.国际化资源文件合并问题.
 * 例如xxx.bb包下和xxx.cc包下包含相同名称的文件， 但是property文件中的key是不重复的。 按照 相同资源文件命名格式 文件 合并为一个
 * 3. 资源文件中注释问题： 1》凡是以#开通的都是视为注释 ，不予与处理 2>注释必须另起一行 4.空白行或者没有不符合属性文件的格式的行 1》
 * 不予与处理，视为不存在
 * 
 * @author ocean 2015年8月14日 email：zhangjunfang0505@163.com
 */
public class LoadProperties implements Serializable {
	private static final long serialVersionUID = -5545133727184399946L;
	public static final ConcurrentHashMap<Locale, Map<String, String>> CONCURRENT_HASH_MAP = new ConcurrentHashMap<Locale, Map<String, String>>(
			8);
	static {
		try {
			URI uri = Thread.currentThread().getContextClassLoader()
					.getResource("").toURI();
			Files.walkFileTree(Paths.get(uri), new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) throws IOException {
					String fileName = file.getFileName().toString();
					// message_zh_cn.properties
					if (fileName.endsWith("properties")
							&& fileName.startsWith("message")) {
						List<String> list = Files.readAllLines(file);
						fileName = fileName.substring("message".length() + 1,
								fileName.length() - 1 - "properties".length());
						String[] temp = fileName.split("_");
						Locale locale = new Locale(temp[0], temp[1]);
						putIfExist(locale, list);
					}
					return FileVisitResult.CONTINUE;
				}

				private void putIfExist(Locale locale, List<String> list) {
					Map<String, String> map = new HashMap<String, String>(32);
					for (String line : list) {
						int i = line.indexOf('=');
						String key = null;
						if (i > 0 && !line.trim().startsWith("#")) {
							key = line.substring(0, i).trim();
							line = line.substring(i + 1).trim();
							//判断
							if (line.contains("#")) {
								line = line.substring(0, line.indexOf("#"))
										.trim();
							}
							line = parseMessage(line);
							//判断是否有已经存在同名文件
							if (line.length() > 0) {
								if (CONCURRENT_HASH_MAP.containsKey(locale)) {
									map = CONCURRENT_HASH_MAP.get(locale);
									map.put(key, line);
								} else {
									map.put(key, line);
									CONCURRENT_HASH_MAP.put(locale, map);
								}
							}
						}
					}

				}
				//转换编码 ascii 到utf-8 编码
				private String parseMessage(String line) {
					try {
						return new String(line.getBytes("utf-8"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return line;
				}
				@Override
				public FileVisitResult postVisitDirectory(Path dir,
						IOException e) throws IOException {
					if (e == null) {
						return FileVisitResult.CONTINUE;
					} else {
						throw e;
					}
				}
			});

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private static String findByLocale(String key, Locale locale) {
		Map<String, String> map = CONCURRENT_HASH_MAP.get(locale);
		return map.get(key);
	}
	public static String getLocaleMessage(String key, Locale locale,
			String[] message) {
		return MessageFormat.format(findByLocale(key, locale), message);
	}

}
