package com.transilink.framework.core.utils.clazzUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Generics的util类.
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@SuppressWarnings({ "all" })
public class GenericsUtils {
	private static final Log log = LogFactory.getLog(GenericsUtils.class);

	private GenericsUtils() {
	}

	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	public static Class getSuperClassGenricType(Class clazz, int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			log.warn(clazz.getSimpleName()
					+ "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			log.warn("Index: " + index + ", Size of " + clazz.getSimpleName()
					+ "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			log.warn(clazz.getSimpleName()
					+ " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		return (Class) params[index];
	}

	public static Class getGenericClass(Class clazz) {
		return getGenericClass(clazz, 0);
	}

	public static Class getGenericClass(Class clazz, int index)
			throws IndexOutOfBoundsException {
		Type genType = clazz.getGenericSuperclass();

		while (!(genType instanceof ParameterizedType)) {
			if (clazz == Object.class) {
				return Object.class;
			}
			clazz = clazz.getSuperclass();
			genType = clazz.getGenericSuperclass();
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if ((index >= params.length) || (index < 0)) {
			throw new IndexOutOfBoundsException("Index: " + index
					+ ", Size of Parameterized Type: " + params.length);
		}
		return (Class) params[index];
	}
}
