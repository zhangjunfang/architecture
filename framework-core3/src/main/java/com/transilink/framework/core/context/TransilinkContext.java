package com.transilink.framework.core.context;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public abstract class TransilinkContext {
	public static final int REQUEST = 1;
	public static final int SESSION = 5;
	public static final int APPLICATION = 9;
	private static ThreadLocal cache = new ThreadLocal();

	public static void registerContext(TransilinkContext context) {
		cache.set(context);
	}

	public static synchronized TransilinkContext getContext() {
		Object obj;
		if ((obj = (TransilinkContext) cache.get()) == null) {
			registerContext((TransilinkContext) (obj = new DynaTransilinkContext()));
		}
		return (TransilinkContext) (TransilinkContext) obj;
	}

	public static void unregisterContext() {
		cache.set(null);
	}

	public abstract String getParameter(String paramString);

	public abstract String[] getParameters(String paramString);

	public abstract Object getAttribute(String paramString);

	public abstract Object getAttribute(int paramInt, String paramString);

	public abstract void setAttribute(int paramInt, String paramString,
			Object paramObject);

	public abstract void removeAttribute(int paramInt, String paramString);
}