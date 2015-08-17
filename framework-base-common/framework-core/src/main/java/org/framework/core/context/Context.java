/**
 * 
 */
package org.framework.core.context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述： 当前线程依赖的上下文
 * 
 * @author ocean 2015年8月11日 email：zhangjunfang0505@163.com
 */
public class Context implements Serializable ,ScopeParameter{

	private static final long serialVersionUID = 6683917487351884572L;
	private static final  ThreadLocal<Object> LOCAL = new ThreadLocal<Object>();
    private Map<String,Object> cache=null;
	
	public static void registerContext(Context context) {
		LOCAL.set(context);
	}
	
	public static void unRegisterContext() {
		LOCAL.remove();
		LOCAL.set(null);
	}
	public  Context(){
		super();
		cache=new HashMap<String,Object>(16);
	}
	public static synchronized Context getContext() {
		Object obj;
		if ((obj = (Context) LOCAL.get()) == null) {
			registerContext((Context) (obj = new Context()));
		}
		return (Context) obj;
	}
	private String a(int i, String s) {
		return "$" + ScopeParameter.THREAD + "." + s;
	}
	public Object getAttribute(int i, String s) {
		return this.cache.get(a(i, s));
	}

	public void removeAttribute(int i, String s) {
		this.cache.remove(a(i, s));
	}
	public void setAttribute(int i, String s, Object obj) {
		this.cache.put(a(i, s), obj);
	}
	public Object getAttribute(String s) {
		
		return getAttribute(ScopeParameter.THREAD, s);
	}

	
	@Override
	public String getParameter(String paramString) {	
		return null;
	}
	@Override
	public String[] getParameters(String paramString) {
		return null;
	}
}
