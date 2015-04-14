/**
 * 
 */
package cn.newcapec.framework.plugins.cache.memoryCache.memcached;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newcapec.framework.plugins.cache.memoryCache.memcached.client.ErrorHandler;
import cn.newcapec.framework.plugins.cache.memoryCache.memcached.client.MemCachedClient;

/**
 * 基本的出错处理
 *
 */
public class MemcachedErrorHandler implements ErrorHandler
{
	private static final Log Logger = LogFactory.getLog(MemcachedErrorHandler.class);
	
	public void handleErrorOnDelete(MemCachedClient client, Throwable error,
			String cacheKey)
	{
		Logger.error(new StringBuilder("ErrorOnDelete, cacheKey: ")
				.append(cacheKey).toString(),error);
	}

	public void handleErrorOnFlush(MemCachedClient client, Throwable error)
	{
		Logger.error("ErrorOnFlush",error);
	}

	public void handleErrorOnGet(MemCachedClient client, Throwable error,
			String cacheKey)
	{
		Logger.error(new StringBuilder("ErrorOnGet, cacheKey: ")
			.append(cacheKey).toString(),error);
	}

	public void handleErrorOnGet(MemCachedClient client, Throwable error,
			String[] cacheKeys)
	{
		Logger.error(new StringBuilder("ErrorOnGet, cacheKey: ")
			.append(cacheKeys).toString(),error);
	}

	public void handleErrorOnInit(MemCachedClient client, Throwable error)
	{
		Logger.error("ErrorOnInit",error);
	}

	public void handleErrorOnSet(MemCachedClient client, Throwable error,
			String cacheKey)
	{
		Logger.error(new StringBuilder("ErrorOnSet, cacheKey: ")
			.append(cacheKey).toString(),error);
	}

	public void handleErrorOnStats(MemCachedClient client, Throwable error)
	{
		Logger.error("ErrorOnStats",error);
	}

}
