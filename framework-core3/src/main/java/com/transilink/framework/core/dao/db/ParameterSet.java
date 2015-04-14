package com.transilink.framework.core.dao.db;

import java.util.HashMap;
import java.util.Map;

import com.transilink.framework.core.model.variant.Variant;
import com.transilink.framework.core.model.variant.VariantSet;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class ParameterSet extends VariantSet {
	private Map b = new HashMap();

	public Variant getVariant(String key) {
		String str = key.toLowerCase();
		return super.getVariant(str);
	}

	public void setVariant(String name, Variant variant) {
		String str = name.toLowerCase();
		this.b.put(str, name);
		super.setVariant(str, variant);
	}

	public void remove(String key) {
		String str = key.toLowerCase();
		this.b.remove(str);
		super.remove(str);
	}

	public String indexToName(int index) {
		String name = super.indexToName(index);
		String key;
		if ((key = (String) this.b.get(name)) == null)
			key = name;
		return key;
	}
}