package com.transilink.framework.core.utils.clazzUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

/**
 *
 * <p>
 * Title:类加载工具类
 * </p>
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@SuppressWarnings("all")
public class ClassLoaderUtils {
	// ~ Methods
	// ////////////////////////////////////////////////////////////////

	/**
	 * Load a given resource.
	 *
	 * This method will try to load the resource using the following methods (in
	 * order):
	 * <ul>
	 * <li>From Thread.currentThread().getContextClassLoader()
	 * <li>From ClassLoaderUtil.class.getClassLoader()
	 * <li>callingClass.getClassLoader()
	 * </ul>
	 *
	 * @param resourceName
	 *            The name of the resource to load
	 * @param callingClass
	 *            The Class object of the calling object
	 */
	public static URL getResource(String resourceName, Class callingClass) {
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource(resourceName);

		if (url == null) {
			url = ClassLoaderUtils.class.getClassLoader().getResource(
					resourceName);
		}

		if (url == null) {
			ClassLoader cl = callingClass.getClassLoader();

			if (cl != null) {
				url = cl.getResource(resourceName);
			}
		}
		if (url == null) {
			url = callingClass.getResource(resourceName);
		}
		if (url == null) {
			url = callingClass.getClass().getResource(resourceName);
		}

		if ((url == null) && (resourceName != null)
				&& (resourceName.charAt(0) != '/')) {
			return getResource('/' + resourceName, callingClass);
		}

		return url;
	}

	/**
	 * This is a convenience method to load a resource as a stream.
	 *
	 * The algorithm used to find the resource is given in getResource()
	 *
	 * @param resourceName
	 *            The name of the resource to load
	 * @param callingClass
	 *            The Class object of the calling object
	 */
	public static InputStream getResourceAsStream(String resourceName,
			Class callingClass) {
		URL url = getResource(resourceName, callingClass);

		try {
			return (url != null) ? url.openStream() : null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Load a class with a given name.
	 *
	 * It will try to load the class in the following order:
	 * <ul>
	 * <li>From Thread.currentThread().getContextClassLoader()
	 * <li>Using the basic Class.forName()
	 * <li>From ClassLoaderUtil.class.getClassLoader()
	 * <li>From the callingClass.getClassLoader()
	 * </ul>
	 *
	 * @param className
	 *            The name of the class to load
	 * @param callingClass
	 *            The Class object of the calling object
	 * @throws ClassNotFoundException
	 *             If the class cannot be found anywhere.
	 */
	public static Class loadClass(String className, Class callingClass)
			throws ClassNotFoundException {
		try {
			return Thread.currentThread().getContextClassLoader()
					.loadClass(className);
		} catch (ClassNotFoundException e) {
			try {
				return Class.forName(className);
			} catch (ClassNotFoundException ex) {
				try {
					return ClassLoaderUtils.class.getClassLoader().loadClass(
							className);
				} catch (ClassNotFoundException exc) {
					return callingClass.getClassLoader().loadClass(className);
				}
			}
		}
	}

	/**
	 * 解析类型
	 *
	 * @param typeName
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static final Class parseType(String typeName)
			throws ClassNotFoundException {
		if (typeName.equals("boolean"))
			return Boolean.TYPE;
		if (typeName.equals("int"))
			return Byte.TYPE;
		if (typeName.equals("long"))
			return Byte.TYPE;
		if (typeName.equals("byte"))
			return Byte.TYPE;
		if (typeName.equals("short"))
			return Short.TYPE;
		if (typeName.equals("float"))
			return Byte.TYPE;
		if (typeName.equals("double"))
			return Byte.TYPE;
		return Class.forName(typeName);
	}

	/**
	 * 创建对象
	 *
	 * @param clazzName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static final Object newInstance(String clazzName)
			throws ClassNotFoundException, IllegalAccessException,
			InstantiationException {
		return Class.forName(clazzName).newInstance();
	}

	/**
	 * 创建对象
	 *
	 * @param clazzName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static final Object newInstance(Class clazzName)
			throws ClassNotFoundException, IllegalAccessException,
			InstantiationException {
		return clazzName.newInstance();
	}

	/**
	 * 创建对象
	 *
	 * @param clazzName
	 * @param paramsType
	 * @param constrArgs
	 * @return
	 * @throws java.lang.reflect.InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 */
	public static final Object newInstance(String clazzName,
			Class[] paramsType, Object[] constrArgs)
			throws InvocationTargetException, IllegalArgumentException,
			IllegalAccessException, InstantiationException, SecurityException,
			NoSuchMethodException, ClassNotFoundException {
		return newInstance(Class.forName(clazzName), paramsType, constrArgs);
	}

	/**
	 * 创建对象
	 *
	 * @param clazzName
	 * @param paramTypes
	 * @param constrArgs
	 * @return
	 * @throws java.lang.reflect.InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static final Object newInstance(Class clazzName, Class[] paramTypes,
			Object[] constrArgs) throws InvocationTargetException,
			IllegalArgumentException, IllegalAccessException,
			InstantiationException, SecurityException, NoSuchMethodException {
		return clazzName.getConstructor(paramTypes).newInstance(constrArgs);
	}
}
