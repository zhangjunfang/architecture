package com.transilink.framework.core.model.datacontainer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.MappedSuperclass;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@MappedSuperclass
public abstract class DataContainer implements Serializable {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> propertyValueMap = new HashMap();
	private Map<String, Property> propertyMap = new HashMap();

	protected void init(Property[][] properties) {
		for (Property[] ps : properties)
			for (Property p : ps)
				this.propertyMap.put(p.getName(), p);
	}

	public void setValue(Property property, Object obj) {
		if (!this.propertyMap.containsValue(property)) {
			throw new RuntimeException("DataContainer不包含此属性!");
		}
		if ((property.getType().isInstance(obj)) || (null == obj)) {
			this.propertyValueMap.put(property.getName(), obj);
		} else
			throw new RuntimeException("非法类型!");
	}

	public Object getValue(Property property) {
		return this.propertyValueMap.get(property.getName());
	}

	public Property getProperty(String propertyName) {
		Property result = (Property) this.propertyMap.get(propertyName);
		return result;
	}
}