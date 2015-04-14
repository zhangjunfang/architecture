package com.transilink.framework.core.rest.velocity;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class PathUtil {
	private static String rootPath;

	public static String getRootPath() {
		return rootPath;
	}

	static {
		String classPath = PathUtil.class.getResource("/").getPath();

		if (classPath.indexOf("/./") != -1) {
			classPath = classPath.replaceAll("/./", "/");
		}
		rootPath = classPath.substring(0, classPath.indexOf("WEB-INF"));
	}
}