package com.transilink.framework.core.context;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public abstract class AbstractTransilinkContext extends TransilinkContext {
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