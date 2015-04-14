package com.transilink.framework.core.model.datacontainer;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class ThreadLocalContainer {
	private ThreadLocal<HashMap<String, Object>> threadLocals = new ThreadLocal();

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

	public void clearAll() {
		for (Map.Entry entry : getThreadLocals().entrySet())
			entry.setValue(null);
	}
}