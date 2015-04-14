package com.transilink.framework.plugins.cache.memoryCache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.transilink.framework.plugins.cache.Cache;
import com.transilink.framework.plugins.cache.CacheEngine;

@Component
public class CacheController implements Cache {
	/**
	 * 
	 */
	private static final long serialVersionUID = 316552922252767928L;
	protected final static Log log = LogFactory.getLog(CacheController.class);
	@Autowired
	private static CacheEngine cache;
	
	public static void setCache(CacheEngine cache){
		CacheController.cache = cache;
	}
	
	public static CacheEngine getCache(){
		
		if(cache == null){
			log.error("没有加载缓存，无法使用");
			throw new RuntimeException("没有加载缓存，无法使用");
		}
		return cache;
	}
}
