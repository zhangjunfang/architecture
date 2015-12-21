package org.springframework.ocean.remoting.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * @author： ocean
 * @创建时间：2015年12月21日
 * @mail：zhangjunfang0505@163.com
 * @描述：
 */
public abstract class BeanUtil {

	private static final Class<?>[] class0 = {};
	private static final Object[] object0 = {};

	private BeanUtil() {
	}

	/**
	 * 关闭多个对象,实现close方法,屏蔽异常
	 * 
	 * 采用可变参数取代数组
	 * 
	 * @param resources
	 *            Object[]
	 */
	public static void closeResource(Object... resources) {
		closeResource2(resources, "close");
	}

	public static void closeResource2(Object[] resources, String method) {
		NEXT: for (int i = 0; i < resources.length; i++) {
			if (resources[i] != null) {
				try {
					Method m = resources[i].getClass()
							.getMethod(method, class0);
					try {
						m.invoke(resources[i], object0);
						continue NEXT;
					} catch (IllegalAccessException e) {
						Class<?>[] supers = getInterfaces(resources[i]
								.getClass());
						for (int j = 0; j < supers.length; j++) {
							try {
								m = supers[j].getMethod(method, class0);
								m.invoke(resources[i], object0);
								continue NEXT;
							} catch (NoSuchMethodException ex) {
								continue;
							} catch (IllegalAccessException ex) {
								continue;
							}
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public static Class<?>[] getInterfaces(Class<?> clazz) {
		ArrayList<Class<?>> list = new ArrayList<Class<?>>();
		while (clazz != null) {
			list.addAll(Arrays.asList(clazz.getInterfaces()));
			clazz = clazz.getSuperclass();
		}
		return list.toArray(new Class[list.size()]);
	}
}
