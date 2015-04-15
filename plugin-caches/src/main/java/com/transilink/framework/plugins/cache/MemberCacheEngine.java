package com.transilink.framework.plugins.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.transilink.framework.plugins.cache.memoryCache.CacheController;
import com.transilink.framework.plugins.cache.memoryCache.memcached.client.MemCachedClient;
import com.transilink.framework.plugins.cache.memoryCache.memcached.client.SockIOPool;
import com.transilink.framework.plugins.cache.utils.NumberUtils;
import com.transilink.framework.plugins.cache.utils.PropertyUtils;
import com.transilink.framework.plugins.cache.utils.StringUtils;

public class MemberCacheEngine extends AbstractCacheEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -659930763038762971L;
	public MemCachedClient cache = null;
	/**server列表，格式为ip+端口**/
	private List<String> servers = null;
	/**优先级，与servers对应**/
	private List<Integer>  levels = null;
	/**初始连接数**/
	private Integer initConn = 1;
	/**最小连接数**/
	private Integer minConn = 1;
	/**最大连接数**/
	private Integer maxConn = 500;
	/**最大处理时间**/
	private Integer maxIdle = 1000 * 60 * 60 * 6;
	/**主线程睡眠时间**/
	private Long sleepTime = 300000L;
	/**socketTo**/
	private Integer socketTo = 30000;
	/**socketConnectTO**/
	private Integer socketConnectTO = 3000;
	/**压缩设置，超过指定大小（单位为K）的数据都会被压缩**/
	private Long maxSize = 64L * 1024L; 
	/**默认缓存放置时间**/
	private Long defaultTime = 120L;
	
	@Override
	public void init() {
		try {
			cache = new MemCachedClient();
			log.info("装载Memcached缓存客户端");
			final SockIOPool pool = SockIOPool.getInstance();
			pool.setAliveCheck(true);
			pool.setServers(this.getServers().toArray(new String[this.getServers().size()]));
			pool.setWeights(this.getLevels().toArray(new Integer[this.getLevels().size()]));

			pool.setInitConn(this.getInitConn());
			pool.setMinConn(this.getMinConn());
			pool.setMaxConn(this.getMaxConn());
			pool.setMaxIdle(this.getMaxIdle());
			pool.setMaintSleep(this.getSleepTime());
			pool.setNagle(false);
			pool.setSocketTO(this.getSocketTo());
			pool.setSocketConnectTO(this.getSocketConnectTO());
			
			log.info("缓存基本数据装载完成，准备初始化！");
			pool.initialize();
			
			log.info("缓存初始化完毕");
			cache.setCompressEnable(true);
			cache.setCompressThreshold(getMaxSize());
		} catch (Exception e) {
			e.printStackTrace();
			log.info("membercache初始化错误"+e.getMessage());
		}
	}

	@Override
	public void loadJndi() {
		
		try {
			Context context = new InitialContext();
			log.info("准备加载缓存配置：缓存名称为:"+this.getJndiName());
			Object o = context.lookup(this.getJndiName());
			
			log.info("装载完成90%，准备将缓存放入!");
			MemberCacheEngine cs = new MemberCacheEngine();
			cs.setSingleServer((String) PropertyUtils.getProperty(o, "server"));
			cs.setSingleLevel((String) PropertyUtils.getProperty(o, "level"));
			cs.setSleepTime((Long) PropertyUtils.getProperty(o, "sleepTime"));
			cs.setMaxSize((Long) PropertyUtils.getProperty(o, "maxSize"));
			
			cs.setInitConn((Integer) PropertyUtils.getProperty(o, "initConn"));
			cs.setMinConn((Integer) PropertyUtils.getProperty(o, "minConn"));
			cs.setMaxConn((Integer) PropertyUtils.getProperty(o, "maxConn"));
			cs.setMaxIdle((Integer) PropertyUtils.getProperty(o, "maxIdle"));
			cs.setDefaultTime((Long) PropertyUtils.getProperty(o, "defaultTime"));
			cs.setSocketTo((Integer) PropertyUtils.getProperty(o, "socketTo"));
			cs.setSocketConnectTO((Integer) PropertyUtils.getProperty(o, "socketConnectTO"));
			cs.init();
			log.info("加载缓存配置器成功!");
			CacheController.setCache(cs);
			
		} catch (NamingException e) {
			e.printStackTrace();
			log.info("通过JNDI加载缓存配置器失败!"+e.getMessage());
		}
	}

	
	



	

	public void setSingleServer(String server){
		if(server.indexOf(StringUtils.Symbol.COMMA)==-1){
			List<String> servers = new ArrayList<String>(1);
			servers.add(server);
			this.setServers(servers);
		}else{
			String[] servers = server.split(StringUtils.Symbol.COMMA);
			List<String> servers_list = new ArrayList<String>(servers.length);
			for(String s : servers){
				if(StringUtils.hasText(s)){
					servers_list.add(s);
				}
			}
			this.setServers(servers_list);
		}
		
		
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setSingleLevel(String level){
		if(level.indexOf(StringUtils.Symbol.COMMA)==-1){
			List levels = new ArrayList<Integer>(1);
			levels.add(NumberUtils.toInteger(level));
			this.setLevels(levels);
		}else{
			String[] levels = level.split(StringUtils.Symbol.COMMA);
			List<Integer> levels_list = new ArrayList<Integer>(levels.length);
			for(String s : levels){
				if(StringUtils.hasText(s)){
					levels_list.add(NumberUtils.toInteger(s));
				}
			}
			this.setLevels(levels_list);
		}
	}

	@Override
	public void put(String key, Object value) {
		this.put(key, value,getDefaultTime());

	}

	@Override
	public void put(String key, Object value, Long time) {
		if(time<0){
			time = this.getDefaultTime();
		}
		if(cache.keyExists(key)){
			cache.replace(key, value, new Date(time*60*1000));
		}else{
			cache.add(key, value, new Date(time*60*1000));
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key, Class<T> clazz) {
		return (T)cache.get(key);
	}

	@Override
	public Object get(String key) {
		return cache.get(key);
	}

	@Override
	public void flush() {
		this.clear();
	}

	@Override
	public void flush(Integer index) {
		cache.flushAll(new String[]{this.getServers().get(index)});
	}

	@Override
	public void remove(String key) {
		cache.delete(key);

	}

	@Override
	public void clear() {
		cache.flushAll();
	}

	@Override
	public int size() {
		return 0;
	}

	public MemCachedClient getCache() {
		return cache;
	}

	public void setCache(MemCachedClient cache) {
		this.cache = cache;
	}

	public List<String> getServers() {
		return servers;
	}

	public void setServers(List<String> servers) {
		this.servers = servers;
	}

	public List<Integer> getLevels() {
		return levels;
	}

	public void setLevels(List<Integer> levels) {
		this.levels = levels;
	}

	public Integer getInitConn() {
		return initConn;
	}

	public void setInitConn(Integer initConn) {
		this.initConn = initConn;
	}

	public Integer getMinConn() {
		return minConn;
	}

	public void setMinConn(Integer minConn) {
		this.minConn = minConn;
	}

	public Integer getMaxConn() {
		return maxConn;
	}

	public void setMaxConn(Integer maxConn) {
		this.maxConn = maxConn;
	}

	public Integer getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Long getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(Long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public Integer getSocketTo() {
		return socketTo;
	}

	public void setSocketTo(Integer socketTo) {
		this.socketTo = socketTo;
	}

	public Integer getSocketConnectTO() {
		return socketConnectTO;
	}

	public void setSocketConnectTO(Integer socketConnectTO) {
		this.socketConnectTO = socketConnectTO;
	}

	public Long getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Long maxSize) {
		this.maxSize = maxSize;
	}

	public Long getDefaultTime() {
		return defaultTime;
	}

	public void setDefaultTime(Long defaultTime) {
		this.defaultTime = defaultTime;
	}

}
