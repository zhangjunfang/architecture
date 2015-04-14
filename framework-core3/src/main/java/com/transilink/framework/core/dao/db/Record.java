package com.transilink.framework.core.dao.db;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.transilink.framework.core.exception.BaseException;
import com.transilink.framework.core.model.variant.VariantSet;
import com.transilink.framework.core.utils.clazzUtils.BeanUtils;
import com.transilink.framework.core.utils.stringUtils.StringUtil;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@SuppressWarnings("all")
public class Record extends VariantSet {

	private Logger logger = Logger.getLogger(getClass().getName());

	private int state;

	public static final int ADD = 1;

	public static final int MODIFY = 2;

	public static final int DELETE = 3;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	/**
	 * 将DO对象转换为record
	 *
	 * @param object
	 * @return
	 */
	public static Record fromDO(Object object) {
		Record record = null;
		Method method[] = object.getClass().getMethods();
		for (int i = 0, len = method.length; i < len; i++) {
			if (record == null) {
				record = new Record();
			}
			Method aMethod = method[i];
			String methodname = aMethod.getName();
			if (methodname.startsWith("set")) {
				String propertyName = methodname.substring(3);
				String s = String.valueOf(propertyName.charAt(0)).toLowerCase();
				propertyName = s + propertyName.substring(1);
				record.setValue(propertyName,
						BeanUtils.getProperty(object, propertyName));
			} // end if
		} // end for

		return record;
	}

	/**
	 * 将recourd对象转换到DO
	 *
	 * @param obj
	 */
	public void toDO(Object obj) {

		for (int i = 0, len = this.count(); i < len; i++) {
			String name = this.indexToName(i);
			Object value = this.getValue(i);

			if ((value instanceof String)
					&& (StringUtil.isEmpty((String) value))) {
				value = null;
			}
			try {
				BeanUtils.copyProperty(obj, name, value);

			} catch (Exception ex) {
				logger.error(ex);
			}

		}
	}

	/**
	 * 将recourd对象转换到DO
	 *
	 * @param obj
	 */
	public <T> T toDO(Class<T> clazz) {
		try {
			Object object = clazz.newInstance();
			toDO(object);
			return (T) object;
		} catch (Exception ex) {
			throw new BaseException("转换DO对象出错！", ex);
		}
	}
}
