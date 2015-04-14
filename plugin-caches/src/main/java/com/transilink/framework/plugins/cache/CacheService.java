package com.transilink.framework.plugins.cache;

/****
 * 
 * @author Sntey
 * 
 * 缓存发启方
 *
 */
public interface CacheService extends Cache {

	
	CacheEngine getTempCache();
	
	
	CacheEngine getDataCache();
}
