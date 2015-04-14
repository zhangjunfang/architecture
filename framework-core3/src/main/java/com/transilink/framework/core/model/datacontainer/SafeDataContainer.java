package com.transilink.framework.core.model.datacontainer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public abstract class SafeDataContainer extends DataContainer {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Map<String, Boolean> readControlInfo = new ConcurrentHashMap<String, Boolean>();

	@SuppressWarnings("unused")
	private Map<String, Boolean> writeControlInfo = new ConcurrentHashMap<String, Boolean>();

	public Object getSafeValue(Property property) {
		return super.getValue(property);
	}

	public void setSafeValue(Property property, Object obj) {
		super.setValue(property, obj);
	}
}