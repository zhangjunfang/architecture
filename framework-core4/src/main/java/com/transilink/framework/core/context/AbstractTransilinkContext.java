package com.transilink.framework.core.context;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public abstract class AbstractTransilinkContext extends Context {
	public Object getAttribute(String s) {
		Object obj;
		if (((obj = getAttribute(1, s)) == null)
				&& ((obj = getAttribute(2, s)) == null)
				&& ((obj = getAttribute(5, s)) == null)) {
			obj = getAttribute(9, s);
		}
		return obj;
	}
}