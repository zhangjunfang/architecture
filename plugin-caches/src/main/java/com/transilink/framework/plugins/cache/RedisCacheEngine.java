package com.transilink.framework.plugins.cache;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.transilink.framework.plugins.cache.memoryCache.CacheController;
import com.transilink.framework.plugins.cache.utils.PropertyUtils;
import com.transilink.framework.plugins.cache.utils.SerializeUtils;
import redis.clients.jedis.Jedis;
/****
 * 
 * @author Sntey
 *
 */
public class RedisCacheEngine extends AbstractCacheEngine {

	private Jedis redis = null;
	private String host;
	private Integer port;
	/**默认缓存放置时间**/ 
	private Long defaultTime = 120L;
	
	@Override
	public void init() {
		try {
			redis = new Jedis(this.getHost(), this.getPort());
			log.info("redis准备完毕");
			log.info("缓存初始化完毕");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("redis初始化错误"+e.getMessage());
		}
	}
	@Override
	public void loadJndi() {
		try {
			Context context = new InitialContext();
			log.info("准备加载缓存配置：缓存名称为:"+this.getJndiName());
			Object o = context.lookup(this.getJndiName());
			
			RedisCacheEngine cs = new RedisCacheEngine();
			cs.setHost((String) PropertyUtils.getProperty(o, "host"));
			cs.setPort((Integer) PropertyUtils.getProperty(o, "port"));
			cs.init();
			log.info("加载缓存配置器成功!");
			CacheController.setCache(cs);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("通过JNDI加载缓存配置器失败!"+e.getMessage());
		}
	}
	@Override
	public void put(String key, Object value) {
		this.put(key, value, this.getDefaultTime());
	}

	@Override
	public void put(String key, Object value, Long time) {
		if(time<0){
			time = this.getDefaultTime();
		}
		redis.set(key.getBytes(), SerializeUtils.serialize(value));
		redis.expire(key, Integer.valueOf(time.toString())*60);
	}

	@Override
	public <T> T get(String key, Class<T> clazz) {
		// TODO Auto-generated method stub
		return (T)SerializeUtils.unserialize(redis.get(key.getBytes()));
	}

	@Override
	public Object get(String key) {
		// TODO Auto-generated method stub
		return SerializeUtils.unserialize(redis.get(key.getBytes()));
	}

	@Override
	public void flush() {
		this.clear();
	}

	@Override
	public void flush(Integer index) {
		/**暂不支持**/
		throw new RuntimeException("redis-暂不支持该方法");
	}

	@Override
	public void remove(String key) {
		redis.del(key.getBytes());
	}

	@Override
	public void clear() {
		redis.flushAll();
	}

	@Override
	public int size() {
		return redis.keys("*").size();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Jedis getRedis() {
		return redis;
	}

	public void setRedis(Jedis redis) {
		this.redis = redis;
	}

	public Long getDefaultTime() {
		return defaultTime;
	}

	public void setDefaultTime(Long defaultTime) {
		this.defaultTime = defaultTime;
	}

}
