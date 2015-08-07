package com.transilink.framework.core.utils.listUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 对象列表接口,定义对象列表的基本方法。
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@SuppressWarnings("all")
public interface ObjectCollection {
	/**
	 * 向对象列表中添加一个对象。
	 *
	 * @param key
	 * @param object
	 */
	public void add(Object key, Object object);

	/**
	 * 强行向对象列表中添加一个对象，如果发现重复的键值则覆盖该对象。
	 *
	 * @param key
	 * @param object
	 */
	public void forceAdd(Object key, Object object);

	/**
	 * 指定位置，向对象列表中添加一个对象。
	 *
	 * @param index
	 * @param key
	 * @param object
	 */
	public void add(int index, Object key, Object object);

	/**
	 * 获取对象
	 *
	 * @param key
	 * @return
	 */
	public Object get(Object key);

	/**
	 * 获取对象
	 *
	 * @param index
	 * @return
	 */
	public Object get(int index);

	/**
	 * 设置对象
	 *
	 * @param index
	 * @param object
	 */
	public void set(int index, Object object);

	/**
	 * 返回给定的键值对应的索引号。
	 *
	 * @param key
	 * @return
	 */
	public int indexOf(Object key);

	/**
	 * 返回给定的索引号对应的键值。
	 *
	 * @param index
	 * @return
	 */
	public Object getKey(int index);

	/**
	 * 删除对象列表中的一个对象。
	 *
	 * @param key
	 * @return
	 */
	public Object remove(Object key);

	/**
	 * 删除对象列表中的一个对象。
	 *
	 * @param index
	 * @return
	 */
	public Object remove(int index);

	/**
	 * 删除全部对象。
	 *
	 */
	public void removeAll();

	/**
	 * 获取大小
	 *
	 * @return
	 */
	public int size();

	/**
	 * 返回键List
	 *
	 * @return
	 */
	public List keyList();

	/**
	 * 返回Map
	 *
	 * @return
	 */
	public Map map();

	/**
	 * 返回迭代器
	 *
	 * @return
	 */
	public Iterator iterator();

	/**
	 * 克隆对象.
	 *
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException;
}