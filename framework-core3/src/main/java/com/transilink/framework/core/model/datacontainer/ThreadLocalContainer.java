package com.transilink.framework.core.model.datacontainer;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class ThreadLocalContainer {
	private ThreadLocal<HashMap<String, Object>> threadLocals = new ThreadLocal<HashMap<String,Object>>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Map<String, Object> getThreadLocals() {
		if (null == this.threadLocals.get()) {
			this.threadLocals.set(new HashMap());
		}
		return (Map) this.threadLocals.get();
	}

	public Object getValue(String name) {
		return getThreadLocals().get(name);
	}

	public void setValue(String name, Object value) {
		getThreadLocals().put(name, value);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void clearAll() {
		for (Map.Entry entry : getThreadLocals().entrySet())
			entry.setValue(null);
	}
}