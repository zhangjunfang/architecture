/*
 * 该类功能及其特点的描述（例如：该类是用来……）
 *
 * 
 */
/**
 * 
 */
package com.transilink.framework.plugins.cache.jndi;

import java.io.Serializable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;

/**
 * @author Administrator
 *
 */
/**  
 * <p>Title: Membercache</p>  
 * <p>Description: </p>  
 * <p>Copyright: Copyright (c) 郑州新开普电子股份有限公司 2013</p>  
 * @author 杨航(Sntey)
 * @version
 * @date 创建日期：2013-8-23
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class Membercache implements MembercacheMBean,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6968655988543783696L;
	private String jndiName;
	private String server;
	private String level;
	private Long sleepTime;
	private Long maxSize;
	private Long defaultTime = 120L;
	private Integer socketTo = 30000;
	/**socketConnectTO**/
	private Integer socketConnectTO = 3000;
	private Integer initConn = 1;
	/**最小连接数**/
	private Integer minConn = 1;
	/**最大连接数**/
	private Integer maxConn = 500;
	/**最大处理时间**/
	private Integer maxIdle = 1000 * 60 * 60 * 6;
	
	
	public String getJndiName() {
		return jndiName;
	}
	public void setJndiName(String jndiName) throws Exception {
		this.jndiName = jndiName;
		this.rebind(this.jndiName);
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
		this.rebind();
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
		this.rebind();
	}
	public Long getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(Long sleepTime) {
		this.sleepTime = sleepTime;
		this.rebind();
	}
	public Long getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(Long maxSize) {
		this.maxSize = maxSize;
		this.rebind();
	}
	public Long getDefaultTime() {
		return defaultTime;
	}
	public void setDefaultTime(Long defaultTime) {
		this.defaultTime = defaultTime;
		this.rebind();
	}
	public Integer getSocketTo() {
		return socketTo;
	}
	public void setSocketTo(Integer socketTo) {
		this.socketTo = socketTo;
		this.rebind();
	}
	public Integer getSocketConnectTO() {
		return socketConnectTO;
	}
	public void setSocketConnectTO(Integer socketConnectTO) {
		this.socketConnectTO = socketConnectTO;
		this.rebind();
	}
	public Integer getInitConn() {
		return initConn;
	}
	public void setInitConn(Integer initConn) {
		this.initConn = initConn;
		this.rebind();
	}
	public Integer getMinConn() {
		return minConn;
	}
	public void setMinConn(Integer minConn) {
		this.minConn = minConn;
		this.rebind();
	}
	public Integer getMaxConn() {
		return maxConn;
	}
	public void setMaxConn(Integer maxConn) {
		this.maxConn = maxConn;
		this.rebind();
	}
	public Integer getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
		this.rebind();
	}
	private void rebind(){
		try {
			this.rebind(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void rebind(String bindName) throws Exception{
		InitialContext ictx = new InitialContext();
		if (jndiName != null) {
			Name name = ictx.getNameParser(jndiName).parse(jndiName);
			Context ctx = ictx;
			int i = 0;
			for (int max = name.size() - 1; i < max; i++)
				try {
					ctx = ctx.createSubcontext(name.get(i));
				} catch (NameAlreadyBoundException ignore) {
					ctx = (Context) ctx.lookup(name.get(i));
				}

			ictx.rebind(jndiName, this);
		}
	}

	

}

