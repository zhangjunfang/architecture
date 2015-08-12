/**
 * 
 */
package org.framework.core.context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：父子线程依赖的上下文
 * 
 * @author ocean 2015年8月11日 email：zhangjunfang0505@163.com
 */
public class InheritableContext implements Serializable ,ScopeParameter {

	private static final long serialVersionUID = 5848691781665976843L;

	private static final  InheritableThreadLocal<Object> INHERITABLE_THREAD_LOCAL = new InheritableThreadLocal<Object>();

	private Map<String, Object> cache = null;

	public static void registerContext(InheritableContext context) {
		INHERITABLE_THREAD_LOCAL.set(context);
	}

	public static void unRegisterContext() {
		INHERITABLE_THREAD_LOCAL.set(null);

	}

	private InheritableContext() {
		super();
		cache = new HashMap<String, Object>(16);
	}

	public static synchronized InheritableContext getContext() {
		Object obj;
		if ((obj = (InheritableContext) INHERITABLE_THREAD_LOCAL.get()) == null) {
			registerContext((InheritableContext) (obj = new InheritableContext()));
		}
		return (InheritableContext) obj;
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
	@Override
	public String getParameter(String paramString) {
		return null;
	}
	@Override
	public String[] getParameters(String paramString) {
		return null;
	}
	@Override
	public Object getAttribute(String paramString) {
		return this.cache.get(a(0, paramString));
	}

}
