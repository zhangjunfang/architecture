package com.transilink.framework.core.model.variant;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.ObjectUtils;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class Variant implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2290046135176718648L;
	private int dataType;
	private String name;
	private Object value;

	public Variant() {
		this.dataType = 0;
	}

	public Variant(int dataType) {
		this.dataType = dataType;
	}

	public Variant(String name) {
		this.name = name;
	}

	public Variant(String name, int dataType, Object value) {
		this.name = name;
		this.dataType = dataType;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDataType() {
		return this.dataType;
	}

	public void setDataType(int dataType) {
		if (this.dataType == dataType)
			return;
		this.dataType = dataType;
	}

	public void setDataType(String dataTypeName) {
		setDataType(DataType.nameToType(dataTypeName));
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getString() {
		return VariantUtil.parseString(this.value);
	}

	public void setString(String value) {
		if (this.dataType == 0)
			this.dataType = 1;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == 1) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	public byte getByte() {
		return VariantUtil.parseByte(this.value);
	}

	public void setByte(byte value) {
		if (this.dataType == 0)
			this.dataType = 2;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == 2) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	public short getShort() {
		return VariantUtil.parseShort(this.value);
	}

	public void setShort(short value) {
		if (this.dataType == 0)
			this.dataType = 3;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == 3) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	public int getInt() {
		return VariantUtil.parseInt(this.value);
	}

	public void setInt(int value) {
		if (this.dataType == 0)
			this.dataType = 4;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == 4) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	public long getLong() {
		return VariantUtil.parseLong(this.value);
	}

	public void setLong(long value) {
		if (this.dataType == 0)
			this.dataType = 5;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == 5) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	public float getFloat() {
		return VariantUtil.parseFloat(this.value);
	}

	public void setFloat(float value) {
		if (this.dataType == 0)
			this.dataType = 6;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == 6) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	public double getDouble() {
		return VariantUtil.parseDouble(this.value);
	}

	public void setDouble(double value) {
		if (this.dataType == 0)
			this.dataType = 7;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == 7) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	public BigDecimal getBigDecimal() {
		return VariantUtil.parseBigDecimal(this.value);
	}

	public void setBigDecimal(BigDecimal value) {
		if (this.dataType == 0)
			this.dataType = 8;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == 8) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	public boolean getBoolean() {
		return VariantUtil.parseBoolean(this.value);
	}

	public void setBoolean(boolean value) {
		if (this.dataType == 0)
			this.dataType = 9;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == 9) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	public Date getDate() {
		return VariantUtil.parseDate(this.value);
	}

	public void setDate(Date paramDate) {
		if (this.dataType == 0)
			this.dataType = 10;
		Object obj = VariantUtil.toObject(paramDate);
		if ((this.dataType == 10) || (this.dataType == 11)
				|| (this.dataType == 12)) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	public boolean isNull() {
		return this.value == null;
	}

	public void setNull() {
		this.value = null;
	}

	public boolean equals(Object obj) {
		if ((obj instanceof Variant))
			return ObjectUtils.equals(this.value, ((Variant) obj).getValue());
		return false;
	}

	public int hashCode() {
		if (this.value != null)
			return this.value.hashCode();
		return 0;
	}

	protected Object clone() throws CloneNotSupportedException {
		Variant variant = (Variant) super.clone();
		variant.setValue(getValue());
		return variant;
	}

	public String toString() {
		if (this.value == null)
			return "Variant {null}";
		return this.value.toString();
	}
}