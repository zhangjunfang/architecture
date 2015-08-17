/**
 * 
 */
package org.framework.core.context;

/**
 * 描述：
 * 
 * @author ocean
 * 2015年8月11日
 *  email：zhangjunfang0505@163.com
 */
public interface ScopeParameter {
	
	/***
	 * 对象存储作用域
	 */
	public static final int REQUEST = 1;
	public static final int SESSION = 5;
	public static final int APPLICATION = 9;
	public static final int THREAD = 11;
	public abstract String getParameter(String paramString);

	public abstract String[] getParameters(String paramString);

	public abstract Object getAttribute(String paramString);

	public abstract Object getAttribute(int paramInt, String paramString);

	public abstract void setAttribute(int paramInt, String paramString,
			Object paramObject);

	public abstract void removeAttribute(int paramInt, String paramString);
}
