package com.transilink.framework.core.utils.clazzUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.transilink.framework.core.dao.db.Record;
import com.transilink.framework.core.exception.BaseException;
import com.transilink.framework.core.logs.LogEnabled;
import com.transilink.framework.core.model.variant.DataType;
import com.transilink.framework.core.model.variant.VariantSet;
import com.transilink.framework.core.model.variant.VariantUtil;

/**
 * 提供对对象操作的几个日常方法.如COPY,获取对象某属性的值等.
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@SuppressWarnings("all")
public class BeanUtils implements LogEnabled {
	/**
	 * 拷贝对象属性，拷贝特定的属性值,如目标属性类型与源属性类型不一致，尝试进行转换.
	 *
	 * @param dest
	 *            要copy到的对象
	 * @param orginal
	 *            原始对象
	 */
	public static void copyProperties(Object dest, Object orginal) {
		try {
			org.apache.commons.beanutils.BeanUtils
					.copyProperties(dest, orginal);
		} catch (Exception e) {
			log.warn("复制对象所有属性时出错", e);
		}
	}

	/**
	 * 判断是否存在
	 *
	 * @param names
	 * @param name
	 * @return
	 */
	private static boolean isExists(String[] names, String name) {
		boolean flag = false;
		for (int i = 0, len = names.length; i < len; i++) {
			if (name.equals(names[i])) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 拷贝bean的属性，拷贝特定的属性值,如目标属性类型与源属性类型不一致，尝试进行转换.
	 *
	 * @param dest
	 *            源对象
	 * @param orginal
	 *            目标对象
	 * @param excepts
	 *            忽略拷贝的属性名
	 */
	public static void copyProperties(Object dest, Object orginal,
			String[] excepts) {
		try {

			PropertyDescriptor[] origDescriptors = PropertyUtils
					.getPropertyDescriptors(orginal);

			for (int i = 0; i < origDescriptors.length; ++i) {
				String name = origDescriptors[i].getName();

				if ("class".equals(name)) {
					continue;
				}
				if (excepts != null && isExists(excepts, name)) {
					continue;
				}
				if ((PropertyUtils.isReadable(orginal, name))
						&& (PropertyUtils.isWriteable(dest, name))) {
					try {
						Object value = PropertyUtils.getProperty(orginal, name);

						copyProperty(dest, name, value);
					} catch (NoSuchMethodException e) {
						log.warn("复制对象属性时出错", e);
					}

				}
			}

		} catch (Exception e) {
			log.warn("复制对象属性时出错", e);
		}
	}

	/**
	 * 拷贝特定的属性值,如目标属性类型与源属性类型不一致，尝试进行转换.
	 *
	 * @param dest
	 *            源对象
	 * @param orginal
	 *            目标对象
	 * @param properties
	 *            复制的属性范围
	 */
	public static void copySpecialProperties(Object dest, Object orginal,
			String[] properties) {
		if (properties == null) {
			throw new BaseException("复制对象属性时出错:传入的属性集合参数不能为空！");
		}
		for (int i = 0; i < properties.length; ++i) {
			String name = properties[i];
			try {
				if ((PropertyUtils.isReadable(orginal, name))
						&& (PropertyUtils.isWriteable(dest, name))) {
					Object value = BeanUtils.getProperty(orginal, name);
					copyProperty(dest, name, value);

				}
			} catch (Exception ex) {
				log.warn("复制对象属性时出错", ex);
			}
		}

	}

	/**
	 * 设置对象属性值,如目标属性类型与当前值类型不一致，尝试进行转换.
	 *
	 * @param Object
	 *            bean 目的bean.
	 * @param String
	 *            name 属性名.
	 * @param Object
	 *            value 属性值.
	 * @see #copyProperties.
	 */
	public static void copyProperty(Object bean, String name, Object value) {
		try {

			Class clazz = PropertyUtils.getPropertyType(bean, name);
			if (clazz == null) {
				return;
			}
			if (value == null || clazz.equals(value.getClass())) {
				PropertyUtils.setSimpleProperty(bean, name, value);
				return;
			}

			PropertyUtils.setSimpleProperty(bean, name,
					VariantUtil.translate(DataType.parse(clazz), value));

		} catch (Exception e) {
			log.warn("复制对象单个属性时出错", e);
		}
	}

	/**
	 * 获得对象某个属性的值.
	 *
	 * @param o
	 *            要取值的对象.
	 * @param name
	 *            要取的属性名.
	 * @return 属性值,如果出错返回NULL.
	 * */
	public static Object getProperty(Object o, String name) {
		Object val = null;
		try {
			val = PropertyUtils.getProperty(o, name);
		} catch (Exception e) {
			log.warn("使用BeanUtils.getPropertyValue获取对象某个属性值时出错", e);
		}

		return val;
	}

	/**
	 *
	 * 对象描述
	 *
	 * @param obj
	 * @return
	 */
	public static Map describe(Object obj) {
		try {
			return org.apache.commons.beanutils.BeanUtils.describe(obj);
		} catch (Exception e) {
			log.warn("使用BeanUtils.Map获取对象描述时出错", e);
			return null;
		}
	}

	/**
	 * 调用某个对象的方法
	 *
	 * @param obj
	 *            被调用的对象
	 * @param methodName
	 *            方法名
	 * @param paramName
	 *            参数名数组
	 * @param paramValue
	 *            参数值数组
	 * @return
	 */
	public static Object callMethod(Object obj, String methodName,
			Class[] paramTypes, Object[] paramValues) {

		try {

			Method method = obj.getClass().getDeclaredMethod(methodName,
					paramTypes);
			if (method != null) {
				return method.invoke(obj, paramValues);
			}

		} catch (Exception e) {
			log.warn("使用BeanUtils.callMethod调用方式对象时出错", e);
		}

		return null;
	}

	public static Object cloneBean(Object obj) {
		Object newObj = null;
		try {
			newObj = org.apache.commons.beanutils.BeanUtils.cloneBean(obj);
		} catch (Exception e) {
			log.warn("使用BeanUtils.cloneBean复制对象时出错", e);
		}
		return newObj;
	}

	/**
	 *
	 * 根据数据库columnname获取对用的PO属性名
	 *
	 * @param fldName
	 *            列名称
	 * @return
	 */
	public static String getPOFieldName(String fldName) {
		String s = fldName.toLowerCase();
		StringBuffer strBuff = new StringBuffer();
		String findStr = "_";
		int i = 0;
		int len = s.length();
		while (i < len) {
			int j = s.indexOf(findStr, i);
			if (j >= 0) {
				strBuff = strBuff.append(s.substring(i, j));
				strBuff = strBuff
						.append(Character.toUpperCase(s.charAt(j + 1)));
				i = j + 2;
				continue;
			}
			strBuff = strBuff.append(s.substring(i));
			break;
		}
		return strBuff.toString();
	}

	/**
	 * 转换resultset 到 object
	 *
	 * @param resultSet
	 * @param retuenModelClass
	 * @param flag
	 * @return
	 */
	static void resultSetToBean(ResultSet resultSet, Object obj, boolean flag) {
		try {

			ResultSetMetaData resultMetaData = resultSet.getMetaData();
			for (int i = 1, len = resultMetaData.getColumnCount(); i <= len; i++) {
				try {
					Object value = resultSet.getObject(i);
					if (value instanceof Clob) {
						Clob clob = (Clob) value;
						value = (clob != null ? clob.getSubString(1,
								(int) clob.length()) : null);

					} else if (value instanceof Timestamp) {
						value = ((Timestamp) value).getNanos();

					}
					String propertyName = resultMetaData.getColumnLabel(i);
					if (flag) {
						propertyName = getPOFieldName(propertyName);
					}
					Class propertyType = PropertyUtils.getPropertyType(obj,
							propertyName);
					if (propertyType == null) {
						continue;
					}
					if (propertyName.equals("CREATE_DATETIME")) {

						// 
						// value = ((oracle.sql.TIMESTAMP)
						// value).timestampValue();
					}
					copyProperty(obj, propertyName, value);
				} catch (Exception ex) {
					log.error(ex);
				}
			} // end for i
		} catch (Exception ex) {
			if (ex instanceof BaseException) {
				throw (BaseException) ex;
			}
			throw new BaseException("转换result对象到类[" + obj.getClass().getName()
					+ "]出错！", ex);
		}

	}

	/**
	 * 转换resultset 到 Map
	 *
	 * @param resultSet
	 * @param retuenModelClass
	 * @param flag
	 *            是否获取原始字段名
	 * @return
	 */
	private static void resultSetToMap(ResultSet resultSet, Map obj,
			boolean flag) {
		try {

			ResultSetMetaData resultMetaData = resultSet.getMetaData();
			for (int i = 1, len = resultMetaData.getColumnCount(); i <= len; i++) {
				try {
					Object value = resultSet.getObject(i);
					if (value instanceof Clob) {
						Clob clob = (Clob) value;
						value = (clob != null ? clob.getSubString(1,
								(int) clob.length()) : null);

					} else if (value instanceof Timestamp) {
						value = ((Timestamp) value).getNanos();

					}

					String name = resultMetaData.getColumnLabel(i);
					if (flag) {
						name = getPOFieldName(name);
					}

					obj.put(name, value);

				} catch (Exception ex) {
					log.error(ex);
				}
			} // end for i
		} catch (Exception ex) {
			if (ex instanceof BaseException) {
				throw (BaseException) ex;
			}
			throw new BaseException("转换result对象到类[" + obj.getClass().getName()
					+ "]出错！", ex);
		}

	}

	/**
	 * 将resultset转换到对应的DO对象。
	 *
	 * @param resultSet
	 * @param modelClass
	 * @param convertFieldName
	 *            数据库字段列表与对象属性名匹配规则标识；true:参照hibnerate数据库字段映射机制生成，去掉小划线。
	 *            false:根据原始字段名给对象属性赋值
	 * @return
	 */
	public static Object resultSet(ResultSet resultSet, Class modelClass,
			boolean convertFieldName) {
		try {
			Object object = null;
			if (Map.class.isAssignableFrom(modelClass)) {
				object = new HashMap();
				resultSetToMap(resultSet, (Map) object, convertFieldName);
				return object;
			}
			if (VariantSet.class.isAssignableFrom(modelClass)) {
				object = new Record();
				resultSetToVariantSet(resultSet, (VariantSet) object,
						convertFieldName);
				return object;
			}
			object = ClassLoaderUtils.newInstance(modelClass);
			resultSetToBean(resultSet, object, convertFieldName);
			return object;
		} catch (Exception ex) {
			if (ex instanceof BaseException) {
				throw (BaseException) ex;
			}

			throw new BaseException("转换result对象到类[" + modelClass.getName()
					+ "]出错！", ex);

		}
	}

	/**
	 * 将resultset转换到对应的VariantSet对象
	 *
	 * @param resultSet
	 * @param set
	 * @param flag
	 *            是否获取原始字段名称
	 */
	static void resultSetToVariantSet(ResultSet resultSet,
			VariantSet variantSet, boolean flag) {

		try {

			ResultSetMetaData resultMetaData = resultSet.getMetaData();
			for (int i = 1, len = resultMetaData.getColumnCount(); i <= len; i++) {
				try {
					Object value = resultSet.getObject(i);
					if (value instanceof Clob) {
						Clob clob = (Clob) value;
						value = (clob != null ? clob.getSubString(1,
								(int) clob.length()) : null);

					} else if (value instanceof Timestamp) {
						value = ((Timestamp) value).getNanos();
					}

					String name = resultMetaData.getColumnLabel(i);
					if (flag) {
						name = getPOFieldName(name);
					}
					variantSet.setValue(name, value);

				} catch (Exception ex) {
					log.error(ex);
				}
			} // end for i
		} catch (Exception ex) {
			if (ex instanceof BaseException) {
				throw (BaseException) ex;
			}
			throw new BaseException("转换result对象到类["
					+ variantSet.getClass().getName() + "]出错！", ex);
		}
	}

	/*
	 * Map转换层Bean，使用泛型免去了类型转换的麻烦。
	 *
	 * @param <T>
	 *
	 * @param map
	 *
	 * @param class1
	 *
	 * @return
	 */
	public static <T> T map2Bean(Map<String, String> map, Class<T> class1) {
		T bean = null;
		try {
			bean = class1.newInstance();
			org.apache.commons.beanutils.BeanUtils.populate(bean, map);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 *
	 * @Title: copyProperties
	 * @data:2013-7-10下午1:25:09
	 * @author:zhanghongliang
	 *
	 * @param mapData
	 * @param obj
	 */
	public static void copyProperties(Map mapData, Object obj) {
		if (mapData == null || obj == null) {
			return;
		}

		Iterator names = mapData.keySet().iterator();
		while (names.hasNext()) {
			// Identify the property name and value(s) to be assigned
			String name = (String) names.next();
			if (name == null) {
				continue;
			}
			Object value = mapData.get(name);
			try {
				org.apache.commons.beanutils.BeanUtils.setProperty(obj, name,
						value);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
}
