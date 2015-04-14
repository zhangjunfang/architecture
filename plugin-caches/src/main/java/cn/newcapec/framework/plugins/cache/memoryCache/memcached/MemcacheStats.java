/**
 * 
 */
package cn.newcapec.framework.plugins.cache.memoryCache.memcached;


/**
 * Memcache通过Stat获得的统计信息
 *
 */
@SuppressWarnings("serial")
public class MemcacheStats implements java.io.Serializable
{
	private String serverHost;
	private String statInfo;
	
	public String getServerHost()
	{
		return serverHost;
	}
	public void setServerHost(String serverHost)
	{
		this.serverHost = serverHost;
	}
	public String getStatInfo()
	{
		return statInfo;
	}
	public void setStatInfo(String statInfo)
	{
		this.statInfo = statInfo;
	}

}
