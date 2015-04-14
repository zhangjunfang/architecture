package cn.newcapec.framework.plugins.cache.jndi;

public interface RediscacheMBean {

	void rebind(String bindName) throws Exception;
	
	public String getJndiName();
	public void setJndiName(String jndiName) throws Exception;
	 
	public String getHost();
	public void setHost(String host);
	
	public Integer getPort();
	public void setPort(Integer port);
}
