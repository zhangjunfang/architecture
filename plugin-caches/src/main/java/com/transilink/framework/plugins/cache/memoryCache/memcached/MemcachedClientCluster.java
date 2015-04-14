/**
 * 
 */
package com.transilink.framework.plugins.cache.memoryCache.memcached;

import java.util.List;

import com.transilink.framework.plugins.cache.memoryCache.IMemcachedCache;

/**
 * Memcache Cluster的结构
 *
 */
public class MemcachedClientCluster
{
	private String name;
	private List<IMemcachedCache> caches;
	/**
	 * 集群模式：active/standby，active支持复制，standby仅仅支持切换,默认是active
	 */
	private String mode = MemcachedClientClusterConfig.CLUSTER_MODE_ACTIVE;
	
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public List<IMemcachedCache> getCaches()
	{
		return caches;
	}
	public void setCaches(List<IMemcachedCache> caches)
	{
		this.caches = caches;
	}
	public String getMode()
	{
		return mode;
	}
	public void setMode(String mode)
	{
		this.mode = mode;
	}
}
