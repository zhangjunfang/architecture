package com.transilink.framework.core.model.variant;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.ClassUtils;

import com.transilink.framework.core.exception.BaseException;
import com.transilink.framework.core.utils.listUtils.ObjectCollection;
import com.transilink.framework.core.utils.listUtils.ObjectList;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class VariantSet implements Serializable, Cloneable {
	private static final long serialVersionUID = -6329100785977056907L;
	private ObjectCollection data = new ObjectList();

	private Variant doGetVariant(String key) {
		Variant variant;
		if ((variant = getVariant(key)) == null) {
			variant = new Variant(key);
			setVariant(key, variant);
		}
		return variant;
	}

	private void checkIndexValid(int index) {
		if ((index < 0) || (index >= this.data.size()))
			throw new ArrayIndexOutOfBoundsException(index);
	}

	private void checkKeyValid(String name) {
		if (!exists(name))
			throw new BaseException("Variant '" + name + "' does not exist!");
	}

	public boolean exists(String name) {
		return getVariant(name) != null;
	}

	public void assign(VariantSet variantSet) {
		int j = 0;
		for (int len = variantSet.count(); j < len; j++) {
			String str = variantSet.indexToName(j);
			if (!exists(str))
				setDataType(str, variantSet.getDataType(j));
			setValue(str, variantSet.getValue(j));
		}
	}

	public void clear() {
		this.data.removeAll();
	}

	public Variant getVariant(int index) {
		return (Variant) this.data.get(index);
	}

	public Variant getVariant(String name) {
		return (Variant) this.data.get(name);
	}

	public int getDataType(int index) {
		checkIndexValid(index);
		return getVariant(index).getDataType();
	}

	public void setDataType(int index, int dataType) {
		checkIndexValid(index);
		getVariant(index).setDataType(dataType);
	}

	public void setDataType(int index, String dataTypeName) {
		checkIndexValid(index);
		getVariant(index).setDataType(dataTypeName);
	}

	public int getDataType(String name) {
		checkKeyValid(name);
		return getVariant(name).getDataType();
	}

	public void setDataType(String name, int dataType) {
		doGetVariant(name).setDataType(dataType);
	}

	public void setDataType(String name, String dataTypeName) {
		doGetVariant(name).setDataType(dataTypeName);
	}

	public Object getValue(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getValue();
		return null;
	}

	public BigDecimal getBigDecimal(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getBigDecimal();
		return null;
	}

	public boolean getBoolean(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getBoolean();
		return false;
	}

	public byte getByte(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getByte();
		return 0;
	}

	public Date getDate(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getDate();
		return null;
	}

	public double getDouble(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getDouble();
		return 0.0D;
	}

	public float getFloat(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getFloat();
		return 0.0F;
	}

	public int getInt(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getInt();
		return 0;
	}

	public long getLong(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getLong();
		return 0L;
	}

	public short getShort(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getShort();
		return 0;
	}

	public String getString(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getString();
		return null;
	}

	public void setValue(int index, Object value) {
		checkIndexValid(index);
		getVariant(index).setValue(value);
	}

	public void setBigDecimal(int index, BigDecimal value) {
		checkIndexValid(index);
		getVariant(index).setBigDecimal(value);
	}

	public void setBoolean(int index, boolean value) {
		checkIndexValid(index);
		getVariant(index).setBoolean(value);
	}

	public void setByte(int index, byte value) {
		checkIndexValid(index);
		getVariant(index).setByte(value);
	}

	public void setDate(int index, Date value) {
		checkIndexValid(index);
		getVariant(index).setDate(value);
	}

	public void setDouble(int index, double value) {
		checkIndexValid(index);
		getVariant(index).setDouble(value);
	}

	public void setFloat(int index, float value) {
		checkIndexValid(index);
		getVariant(index).setFloat(value);
	}

	public void setInt(int index, int value) {
		checkIndexValid(index);
		getVariant(index).setInt(value);
	}

	public void setLong(int index, long value) {
		checkIndexValid(index);
		getVariant(index).setLong(value);
	}

	public void setShort(int index, short value) {
		checkIndexValid(index);
		getVariant(index).setShort(value);
	}

	public void setString(int index, String value) {
		checkIndexValid(index);
		getVariant(index).setString(value);
	}

	public Object getValue(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getValue();
		return null;
	}

	public BigDecimal getBigDecimal(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getBigDecimal();
		return null;
	}

	public boolean getBoolean(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getBoolean();
		return false;
	}

	public byte getByte(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getByte();
		return 0;
	}

	public Date getDate(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getDate();
		return null;
	}

	public double getDouble(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getDouble();
		return 0.0D;
	}

	public float getFloat(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getFloat();
		return 0.0F;
	}

	public int getInt(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getInt();
		return 0;
	}

	public long getLong(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getLong();
		return 0L;
	}

	public short getShort(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getShort();
		return 0;
	}

	public String getString(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getString();
		return null;
	}

	public void setVariant(String name, Variant variant) {
		this.data.forceAdd(name, variant);
	}

	public void setValue(String name, Object object) {
		doGetVariant(name).setValue(object);
	}

	public void setBigDecimal(String name, BigDecimal value) {
		doGetVariant(name).setBigDecimal(value);
	}

	public void setBoolean(String name, boolean value) {
		doGetVariant(name).setBoolean(value);
	}

	public void setByte(String name, byte value) {
		doGetVariant(name).setByte(value);
	}

	public void setDate(String name, Date date) {
		doGetVariant(name).setDate(date);
	}

	public void setDouble(String name, double value) {
		doGetVariant(name).setDouble(value);
	}

	public void setFloat(String name, float value) {
		doGetVariant(name).setFloat(value);
	}

	public void setInt(String name, int value) {
		doGetVariant(name).setInt(value);
	}

	public void setLong(String name, long value) {
		doGetVariant(name).setLong(value);
	}

	public void setShort(String name, short value) {
		doGetVariant(name).setShort(value);
	}

	public void setString(String name, String value) {
		doGetVariant(name).setString(value);
	}

	public boolean isNull(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.isNull();
		return true;
	}

	public void setNull(int index) {
		checkIndexValid(index);
		getVariant(index).setNull();
	}

	public boolean isNull(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.isNull();
		return true;
	}

	public void setNull(String name) {
		doGetVariant(name).setNull();
	}

	public void remove(int index) {
		this.data.remove(index);
	}

	public void remove(String name) {
		this.data.remove(name);
	}

	public int count() {
		return this.data.size();
	}

	public String indexToName(int index) {
		return (String) this.data.getKey(index);
	}

	public Object clone() throws CloneNotSupportedException {
		VariantSet variantSet = (VariantSet) super.clone();
		ObjectList objectList = new ObjectList();
		int i = 0;
		for (int len = this.data.size(); i < len; i++)
			objectList.add(this.data.getKey(i),
					((Variant) this.data.get(i)).clone());
		variantSet.data = objectList;
		return variantSet;
	}

	public boolean equals(Object object) {
		if (object == null)
			return false;
		if ((object instanceof VariantSet))
			return this.data.equals(((VariantSet) object).data);
		return false;
	}

	public int hashCode() {
		return this.data.hashCode();
	}

	public String toString() {
		StringBuffer localStringBuffer = new StringBuffer(
				ClassUtils.getShortClassName(super.getClass())).append(":")
				.append(this.data.toString());

		return localStringBuffer.toString();
	}

	public Object[] keyList() {
		return this.data.keyList().toArray();
	}
}