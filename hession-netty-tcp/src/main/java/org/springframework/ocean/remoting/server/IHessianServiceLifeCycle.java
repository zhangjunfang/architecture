
package org.springframework.ocean.remoting.server;

/**
 * 
 * @author： ocean
 * @创建时间：2015年12月21日
 * @mail：zhangjunfang0505@163.com
 * @描述：Hessian服务生命周期控制
 */
public interface IHessianServiceLifeCycle
{

	/**
	 * 服务开启
	 */
	public void startUp();

	/**
	 * 服务停止
	 */
	public void shutDown();
}
