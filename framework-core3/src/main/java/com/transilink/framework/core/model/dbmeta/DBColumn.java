package com.transilink.framework.core.model.dbmeta;

import java.util.Properties;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class DBColumn {
	private DBTable table;
	private String name;
	private String dataType;
	private int size;
	private int digits;
	private int nullable;
	private String metaData;
	boolean primaryKey;
	DBColumn fkParentKey;
	@SuppressWarnings("unused")
	private String fkPropName;
	private String propName;
	private String javaType;
	private Integer hashCode;

	public DBColumn() {
	}

	public DBColumn(DBTable table, String name, String dataType, int size,
			int digits, int nullable) {
		setName(name);
		setDataType(dataType);
		if (size <= 4096)
			setSize(size);
		setDigits(digits);
		setNullable(nullable);
	}

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	public boolean isForeignKey() {
		return this.fkParentKey != null;
	}

	public DBTable getParentTable() {
		if (this.fkParentKey == null) {
			return null;
		}
		return this.fkParentKey.getTable();
	}

	protected Properties getProperties() {
		return this.table.getProperties();
	}

	public void setPropName(String propName) {
		this.propName = propName;
		this.fkPropName = propName;
	}

	public boolean isNull() {
		return this.nullable == 1;
	}

	private boolean isInteger(String type) {
		if (null == type)
			return false;
		if (type.equals("int"))
			return true;
		if (type.equals("short"))
			return true;
		return type.equals("long");
	}

	public boolean isTypeResolved() {
		return null != TypeResolver.resolveType(getDataType(), false);
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getDigits() {
		return this.digits;
	}

	public void setDigits(int digits) {
		this.digits = digits;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNullable(int nullable) {
		this.nullable = nullable;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public DBTable getTable() {
		return this.table;
	}

	public void setTable(DBTable table) {
		this.table = table;
	}

	public String toString() {
		return getName() + " (" + getDataType() + ")";
	}

	public String getMetaData() {
		return this.metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	public boolean equals(Object obj) {
		if ((obj == null) || (!(obj instanceof DBColumn)))
			return false;
		if ((getTable().getName() == null) || (getName() == null))
			return false;
		DBColumn col = (DBColumn) obj;
		if ((col.getTable().getName() == null) || (col.getName() == null))
			return false;
		return (col.getTable().getName().equals(getTable().getName()))
				&& (col.getName().equals(getName()));
	}

	public int hashCode() {
		if (this.hashCode == null) {
			if ((getTable().getName() == null) || (getName() == null))
				return super.hashCode();
			this.hashCode = new Integer(new String(getTable().getName() + ":"
					+ getName()).hashCode());
		}

		return this.hashCode.intValue();
	}

	public String getJavaType() {
		if (null == this.javaType) {
			this.javaType = TypeResolver.resolveType(getDataType(), true);
			if ((null != this.javaType) && (isInteger(this.javaType))
					&& (this.digits > 0))
				this.javaType = "bigdecimal";

			if (null == this.javaType)
				this.javaType = getDataType();
		}
		return this.javaType;
	}

	public String getPropName() {
		return this.propName;
	}
}