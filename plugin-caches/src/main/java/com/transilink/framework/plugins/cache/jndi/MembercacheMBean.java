/*
 * 该类功能及其特点的描述（例如：该类是用来……）
 *
 * @see（与该类相关联的类）：(XXX.java)
 */
/**
 * 
 */
package com.transilink.framework.plugins.cache.jndi;


/**
 * @author Administrator
 *
 */
/**  
 * <p>Title: MembercacheMBean</p>  
 * <p>Description: </p>  
 * <p>Copyright: Copyright (c) 郑州新开普电子股份有限公司 2013</p>  
 * @author 杨航(Sntey)
 * @version
 * @date 创建日期：2013-8-23
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface MembercacheMBean {

	
	void rebind(String bindName) throws Exception;
	
	public String getJndiName();
	public void setJndiName(String jndiName) throws Exception;
	public String getServer();
	public void setServer(String server);
	public String getLevel();
	public void setLevel(String level);
	public Long getSleepTime();
	public void setSleepTime(Long sleepTime);
	public Long getMaxSize();
	public void setMaxSize(Long maxSize);
	public Long getDefaultTime();
	public void setDefaultTime(Long defaultTime);
	public Integer getSocketTo();
	public void setSocketTo(Integer socketTo);
	public Integer getSocketConnectTO();
	public void setSocketConnectTO(Integer socketConnectTO);
	public Integer getInitConn();
	public void setInitConn(Integer initConn);
	public Integer getMinConn();
	public void setMinConn(Integer minConn);
	public Integer getMaxConn();
	public void setMaxConn(Integer maxConn);
	public Integer getMaxIdle() ;
	public void setMaxIdle(Integer maxIdle);
}

