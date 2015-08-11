package cn.newcapec.function.platform.ocean;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
/**
 *
 * @Description :
 * @author : ocean
 * @date : 2014-5-13 上午10:38:17
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
public abstract interface LocalCache<K, V>
{
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