
package org.springframework.ocean.remoting.client;

/**
 * 
 * @author： ocean
 * @创建时间：2015年12月21日
 * @mail：zhangjunfang0505@163.com
 * @描述：
 */
public class HessianConnectFaildException extends RuntimeException
{
	private static final long serialVersionUID = 6999867662961657788L;

	public HessianConnectFaildException(String error, Throwable cause)
	{
		super(error, cause);
	}
}
