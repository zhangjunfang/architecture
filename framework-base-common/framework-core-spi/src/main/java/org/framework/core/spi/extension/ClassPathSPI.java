/**
 * 
 */
package org.framework.core.spi.extension;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.framework.core.spi.common.Adaptive;
import org.framework.core.spi.common.SPI;

import com.ocean.base.utils.StringUtil;

/**
 * 描述：管理SPI对应的资源
 * 
 * @author ocean 2015年8月20日 email：zhangjunfang0505@163.com
 */
public class ClassPathSPI {
	//存储spi接口对应的实现类集合
	public static final Map<Class<?>, Set<Class<?>>> JAR_MAP = new ConcurrentHashMap<Class<?>, Set<Class<?>>>(
			32);
	//存储spi实现类对应的名称
	public static final Map<String, Class<?>> NAME_SPI_MAP = new ConcurrentHashMap<String, Class<?>>(
			64);
	public static final String SPIPATH_STRING = "META-INF/services";
	//public static final String SPIPATH_STRING_PERSION = "META-INF/spi";
	// 静态加载： 默认扫描
	static {
		try {
			URI uri = Thread.currentThread().getContextClassLoader()
					.getResource("").toURI();

			final Path path = Paths.get(uri);
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) throws IOException {
					try {
						Path temPath = getPath(file, path.getNameCount(),
								file.getNameCount());
						String classNameString = getLocalPath(temPath);
						if (!temPath.startsWith("META-INF")) {
							// 6: 代表 .class字符串的长度
							classNameString = classNameString.substring(0,
									classNameString.length() - 6);
							Class<?> type = Class.forName(classNameString);
							// 说明是spi
							if (type.isAnnotationPresent(SPI.class)) {
								JAR_MAP.putIfAbsent(type,
										new HashSet<Class<?>>(8));
							}
						} else if (temPath.startsWith(SPIPATH_STRING)) {// 查找spi对应的实现类
							temPath = getPath(file,
									Paths.get(path.toString(), SPIPATH_STRING)
											.getNameCount(), file
											.getNameCount());
							Class<?> type = Class.forName(temPath.toString());
							List<String> strings = Files.readAllLines(file);
							Set<Class<?>> set = null;
							for (String temp : strings) {
								temp = StringUtil
										.filterInvalidInformation(temp);
								if (temp == null) {
									continue;
								}
								Class<?> nameType=Class.forName(temp.trim());
								if (JAR_MAP.containsKey(nameType)) {
									set = JAR_MAP.get(nameType);
									set.add(type);
								} else {
									set = new HashSet<Class<?>>(8);
									set.add(nameType);
									JAR_MAP.putIfAbsent(nameType, set);
								}
								String key="";
								if (nameType.isAssignableFrom(Adaptive.class)) {
									key=nameType.getAnnotation(Adaptive.class).value();
								} else {
									key=nameType.getName();
								}
								NAME_SPI_MAP.put(key, nameType);
							}

						}
					} catch (ClassNotFoundException e) {

						e.printStackTrace();
					}

					return FileVisitResult.CONTINUE;
				}

				private Path getPath(Path file, int nameCount, int nameCount2) {
					return file.subpath(nameCount, nameCount2);
				}

				private String getLocalPath(Path path) {
					String classNameString = "";
					if (System.getProperty("os.name").toLowerCase()
							.contains("window")) {
						classNameString = path.toString().replaceAll(
								File.separator + File.separator, "\\.");
					} else {
						classNameString = path.toString().replaceAll(
								File.separator, "\\.");
					}
					return classNameString;
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
}
