package com.transilink.framework.core.utils.fileUtils.fileloader;

import com.transilink.framework.core.utils.clazzUtils.ClassLoaderUtils;

/**
 * 文件加载工厂类
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@SuppressWarnings("all")
public final class FileLoaderFactory {
	/**
	 * 默认的文件资源载入类
	 */
	private static String DEFAULT_FILE_LOADER = "framework.utils.fileloader.DefaultFileLoader";

	/**
	 * 创建FileLoader
	 *
	 * @param fileLoaderClazzName
	 * @param rootPath
	 * @return
	 */
	public static FileLoader creatFileLoader(String fileLoaderClazzName,
			String rootPath) {
		try {
			return (FileLoader) ClassLoaderUtils.newInstance(
					fileLoaderClazzName, new Class[] { String.class },
					new Object[] { rootPath });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建FileLoader
	 *
	 * @param fileLoaderClazzName
	 * @param rootPath
	 * @return
	 */
	public static FileLoader creatFileLoader(Class fileLoaderClazzName,
			String rootPath) {
		try {
			return (FileLoader) ClassLoaderUtils.newInstance(
					fileLoaderClazzName, new Class[] { String.class },
					new Object[] { rootPath });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建默认的文件加载器
	 *
	 * @return
	 */
	public static final FileLoader createDefaultFileLoader() {
		return creatFileLoader(DEFAULT_FILE_LOADER, null);
	}

}