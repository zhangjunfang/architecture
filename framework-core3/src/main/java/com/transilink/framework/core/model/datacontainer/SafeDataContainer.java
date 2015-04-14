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
	private Map<String, Boolean> readControlInfo = new ConcurrentHashMap();

	private Map<String, Boolean> writeControlInfo = new ConcurrentHashMap();

	public Object getSafeValue(Property property) {
		return super.getValue(property);
	}

	public void setSafeValue(Property property, Object obj) {
		super.setValue(property, obj);
	}
}