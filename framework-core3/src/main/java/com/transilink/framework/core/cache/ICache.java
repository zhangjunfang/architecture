package com.transilink.framework.core.cache;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 * @param <K>
 * @param <V>
 */
public abstract interface ICache<K, V> {
	public abstract V put(K paramK, V paramV);

	public abstract V put(K paramK, V paramV, Date paramDate);

	public abstract V put(K paramK, V paramV, int paramInt);

	public abstract V get(K paramK);

	public abstract V remove(K paramK);

	public abstract boolean clear();

	public abstract int size();

	public abstract Set<K> keySet();

	public abstract Collection<V> values();

	public abstract boolean containsKey(K paramK);

	public abstract void destroy();
}