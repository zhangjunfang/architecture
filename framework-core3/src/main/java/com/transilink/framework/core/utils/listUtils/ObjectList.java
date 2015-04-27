package com.transilink.framework.core.utils.listUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.ObjectUtils;

import com.transilink.framework.core.exception.DuplicateKeyException;

/**
 * 对象列表的实现。此类相当于org.apache.commons.collections.map.LinkedMap 的Adapter
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@SuppressWarnings("all")
public class ObjectList implements ObjectCollection, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5149700417023169004L;
	private LinkedMap data = new LinkedMap();

	/**
	 * 向对象列表中添加一个对象。
	 */
	public void add(Object key, Object object) {
		if (this.data.containsKey(key))
			throw new DuplicateKeyException(key);
		this.data.put(key, object);
	}

	/**
	 * 强行向对象列表中添加一个对象，如果发现重复的键值则覆盖该对象。
	 */
	public void forceAdd(Object key, Object object) {
		this.data.put(key, object);
	}

	private class MapEntry {
		Object key;
		Object value;

		public MapEntry(Object key, Object value) {
			this.key = key;
			this.value = value;
		}

		public Object getKey() {
			return key;
		}

		public void setKey(Object key) {
			this.key = key;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	}

	/**
	 * 指定位置，向对象列表中添加一个对象。
	 */
	public void add(int index, Object key, Object object) {
		// 判断键值是否重复
		if (this.data.containsKey(key)) {
			throw new DuplicateKeyException(key);
		}
		// 判断位置是否合法
		int size = this.data.size();
		if (size > 0 && index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		int i = 0;
		List<MapEntry> entryList = new ArrayList();
		// 遍历map，重新加入元素
		for (Iterator iterator = this.data.entrySet().iterator(); iterator
				.hasNext();) {
			Map.Entry entry = (Entry) iterator.next();
			if (i++ >= index) {
				MapEntry mapEntry = new MapEntry(entry.getKey(),
						entry.getValue());
				entryList.add(mapEntry);
				iterator.remove();
			}
		}
		this.data.put(key, object);
		// 重新追加按顺序加入
		for (Iterator iterator = entryList.iterator(); iterator.hasNext(); i++) {
			MapEntry entry = (MapEntry) iterator.next();
			this.data.put(entry.getKey(), entry.getValue());
		}

	}

	/**
	 * 返回对象列表中的一个对象。
	 */
	public Object get(Object key) {
		return this.data.get(key);
	}

	/**
	 * 返回对象列表中的一个对象。
	 */
	public Object get(int index) {
		return this.data.getValue(index);
	}

	/**
	 * 设置对象列表中的一个对象
	 */
	public void set(int index, Object object) {
		Object key;
		if ((key = this.data.get(index)) != null) {
			this.data.put(key, object);
			return;
		}
		throw new ArrayIndexOutOfBoundsException(index);
	}

	/**
	 * 返回给定的键值对应的索引号。
	 */
	public int indexOf(Object key) {
		return this.data.indexOf(key);
	}

	/**
	 * 返回给定的索引号对应的键值。
	 */
	public Object getKey(int index) {
		return this.data.get(index);
	}

	/**
	 * 删除对象列表中的一个对象。
	 */
	public Object remove(Object key) {
		return this.data.remove(key);
	}

	/**
	 * 删除对象列表中的一个对象。
	 */
	public Object remove(int index) {
		return this.data.remove(index);
	}

	/**
	 * 删除所有对象。
	 */
	public void removeAll() {
		this.data.clear();
	}

	/**
	 * 获取对象数量
	 */
	public int size() {
		return this.data.size();
	}

	/**
	 * 获取键值列表
	 */
	public List keyList() {
		return this.data.asList();
	}

	/**
	 * 获取map
	 */
	public Map map() {
		return this.data;
	}

	/**
	 * 获取主键迭代器
	 */
	public Iterator iterator() {
		return this.data.asList().iterator();
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public boolean equals(Object obj) {
		if (obj instanceof ObjectCollection) {
			ObjectCollection objectCollection = (ObjectCollection) obj;
			;
			if (this.size() != objectCollection.size())
				return false;
			for (int j = 0, i = this.size(); j < i; ++j) {
				Object o = getKey(j);
				if (!(ObjectUtils.equals(get(o), objectCollection.get(o))))
					return false;
			}
			return true;
		}
		return false;
	}

	public int hashCode() {
		if (this.data.size() == 0)
			return 0;
		return this.data.hashCode();
	}

	public String toString() {
		return this.data.toString();
	}
}