package com.transilink.framework.core.context;

/**
 * 
 * 描述：
 * 
 * @author ocean
 * 2015年4月14日
 *  email：zhangjunfang0505@163.com
 */
@SuppressWarnings("rawtypes")
public abstract class TransilinkContext {
	public static final int REQUEST = 1;
	public static final int SESSION = 5;
	public static final int APPLICATION = 9;
	private static ThreadLocal cache = new ThreadLocal();

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
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