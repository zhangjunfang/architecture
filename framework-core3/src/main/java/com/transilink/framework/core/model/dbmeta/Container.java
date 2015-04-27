package com.transilink.framework.core.model.dbmeta;

import java.util.Map;
import java.util.Properties;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class Container {
	private String packageName;
	private Properties properties;
	@SuppressWarnings("rawtypes")
	private Map tables;
	boolean fullyLoaded;

	public Properties getProperties() {
		return this.properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@SuppressWarnings("rawtypes")
	public Map getTables() {
		return this.tables;
	}

	@SuppressWarnings("rawtypes")
	public void setTables(Map tables) {
		this.tables = tables;
	}

	public boolean isFullyLoaded() {
		return this.fullyLoaded;
	}

	public String getProperty(String propertyName) {
		return this.properties.getProperty(propertyName);
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
}