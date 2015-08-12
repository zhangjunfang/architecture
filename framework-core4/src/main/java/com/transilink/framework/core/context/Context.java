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
public abstract class Context {
	public static final int REQUEST = 1;
	public static final int SESSION = 5;
	public static final int APPLICATION = 9;
	private static ThreadLocal cache = new ThreadLocal();

	@SuppressWarnings("unchecked")
	public static void registerContext(Context context) {
		cache.set(context);
	}

	public static synchronized Context getContext() {
		Object obj;
		if ((obj = (Context) cache.get()) == null) {
			registerContext((Context) (obj = new DynaTransilinkContext()));
		}
		return (Context) (Context) obj;
	}

	@SuppressWarnings("unchecked")
	public static void unregisterContext() {
		cache.remove();
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