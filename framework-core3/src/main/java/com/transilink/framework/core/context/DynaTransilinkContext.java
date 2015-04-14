package com.transilink.framework.core.context;

import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class DynaTransilinkContext extends AbstractTransilinkContext {
	private Map cache;

	public DynaTransilinkContext() {
		this.cache = new Hashtable();
	}

	private String a(int i, String s) {
		return "$" + i + "." + s;
	}

	public Object getAttribute(int i, String s) {
		return this.cache.get(a(i, s));
	}

	public String getParameter(String s) {
		return null;
	}

	public String[] getParameters(String s) {
		return null;
	}

	public void removeAttribute(int i, String s) {
		this.cache.remove(a(i, s));
	}

	public void setAttribute(int i, String s, Object obj) {
		this.cache.put(a(i, s), obj);
	}
}