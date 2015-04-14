package cn.newcapec.framework.plugins.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractCacheEngine implements CacheEngine {

	public static Log log = LogFactory.getLog(AbstractCacheEngine.class);

	
	private String jndiName;
	
	@Override
	public Long getDefaultTime() {
		
		return 60L;
	}

	
	/****
	 * 所有缓存都要支持jndi装载
	 */
	public abstract void loadJndi();
	

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
		this.loadJndi();
	}

}
