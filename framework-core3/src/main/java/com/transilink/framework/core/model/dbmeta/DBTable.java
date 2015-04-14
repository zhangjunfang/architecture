package com.transilink.framework.core.model.dbmeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class DBTable implements Comparable {
	private String name;
	private String metaData;
	private Container container;
	private List columns;
	private Map pKeys;
	private Map fKeys;
	private Map columnMap;

	public void removeColumn(String columnName) {
		DBColumn column = getColumn(columnName);
		this.pKeys.remove(column);
		this.fKeys.remove(column);
		this.columnMap.remove(column.getName());
		this.columns.remove(column);
	}

	public DBTable(Container container) {
		this.columns = new ArrayList();

		this.columnMap = new HashMap();
		this.container = container;
	}

	public DBTable(Container container, String name) {
		this.columns = new ArrayList();

		this.columnMap = new HashMap();
		setName(name);
		this.container = container;
	}

	public DBTable(String name, Container container) {
		this.columns = new ArrayList();

		this.columnMap = new HashMap();
		setName(name);
		this.container = container;
	}

	public void init() {
		if (this.pKeys == null)
			this.pKeys = new HashMap();
		if (this.fKeys == null)
			this.fKeys = new HashMap();
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public void notifyPrimaryKey(String columnName) {
		DBColumn column = (DBColumn) this.columnMap.get(columnName);
		if (column != null) {
			if (this.pKeys == null)
				this.pKeys = new HashMap();
			this.pKeys.put(column.getName(), column);
			column.primaryKey = true;
		}
	}

	public void notifyColumn(DBColumn column) {
		column.setTable(this);
		this.columns.add(column);
		this.columnMap.put(column.getName(), column);
	}

	public void notifyForeignKey(String columnName, DBColumn parentKey) {
		DBColumn column = (DBColumn) this.columnMap.get(columnName);
		if (column != null) {
			column.fkParentKey = parentKey;
			if (this.fKeys == null)
				this.fKeys = new HashMap();
			this.fKeys.put(column.getName(), column);
		}
	}

	protected Properties getProperties() {
		return this.container.getProperties();
	}

	public boolean hasCompositeKey() {
		return getPkColumns().size() > 1;
	}

	public boolean isCompositeKeyOnly() {
		return (getPkColumns().size() > 1)
				&& (getPkColumns().size() == getColumns().size());
	}

	public boolean isForeignKey(DBColumn column) {
		if (this.fKeys == null)
			return false;
		return this.fKeys.get(column.getName()) != null;
	}

	public Collection getFKColumns() {
		if (this.fKeys == null) {
			return new ArrayList(0);
		}
		return this.fKeys.values();
	}

	public Collection getPkColumns() {
		if (this.pKeys == null) {
			return new ArrayList(0);
		}
		return this.pKeys.values();
	}

	public String getName() {
		return this.name;
	}

	public String getClassName() {
		return getName();
	}

	public void setName(String name) {
		this.name = name;
	}

	public List getColumns() {
		return this.columns;
	}

	public DBColumn getColumn(String columnName) {
		return (DBColumn) this.columnMap.get(columnName);
	}

	public String getMetaData() {
		return this.metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	public int compareTo(Object obj) {
		if ((obj == null) || (!(obj instanceof DBTable))) {
			return -1;
		}
		return getName().compareTo(((DBTable) obj).getName());
	}
}