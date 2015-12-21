
package org.springframework.ocean.remoting.server;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.remoting.support.RemoteExporter;

import com.caucho.hessian.server.HessianSkeleton;

/**
 * 
 * @author： ocean
 * @创建时间：2015年12月21日
 * @mail：zhangjunfang0505@163.com
 * @描述：抽象Hessian服务暴露,service(具体对象)和 serviceinterface(暴露出的接口) 注入设置参见 {@link RemoteExporter}
 */
public abstract class AbstractHessianServiceExporter extends RemoteExporter implements IHessianServiceLifeCycle
{

	protected int port;
	/**
	 * Spring容器状态，已启动
	 */
	protected volatile boolean started = false;
	protected HessianSkeleton skeleton;
	/**
	 * 服务状态，参见 {@link ServiceStatus}
	 */
	private ServiceStatus serviceStatus = ServiceStatus.NotStarted;
	/**
	 * -----------端口服务映射
	 */
	private static Map<Integer, AbstractHessianServiceExporter> portServiceMap = new ConcurrentHashMap<Integer, AbstractHessianServiceExporter>();

	/**
	 * 初始化 {@link HessianSkeleton}
	 */
	public void initSkeleton()
	{
		checkService();
		checkServiceInterface();
		skeleton = new HessianSkeleton(getService(), getServiceInterface());
	}

	/**
	 * 设置服务占用的端口
	 * 
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port)
	{
		this.port = port;
	}

	/**
	 * @return the port
	 */
	public int getPort()
	{
		return port;
	}

	@Override
	public void setService(Object service)
	{
		super.setService(service);
		started = true;
	}

	/**
	 * 等待服务对象就绪【阻塞方法】
	 */
	protected void waitServiceReady()
	{
		// 等待所有Spring Context就绪
		while (!started)
		{
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				// ignore
			}
		}
	}

	/**
	 * @return the skeleton
	 */
	public HessianSkeleton getSkeleton()
	{
		return skeleton;
	}

	/**
	 * @return the serverStatus
	 */
	public ServiceStatus getServiceStatus()
	{
		return serviceStatus;
	}

	/**
	 * @param serverStatus
	 *            the serverStatus to set
	 */
	protected void setServerStatus(ServiceStatus serviceStatus)
	{
		this.serviceStatus = serviceStatus;
	}

	/**
	 * 获取服务
	 * 
	 * @param port
	 * @return
	 */
	public static AbstractHessianServiceExporter getService(int port)
	{
		return portServiceMap.get(port);
	}

	/**
	 * 获取所有Hessian 服务
	 * 
	 * @return
	 */
	public static Collection<AbstractHessianServiceExporter> getAllService()
	{
		return Collections.unmodifiableCollection(portServiceMap.values());
	}

	/**
	 * 注册服务
	 * 
	 * @param port
	 */
	protected void registerService(int port)
	{
		portServiceMap.put(port, this);
	}

	/**
	 * 注销服务
	 * 
	 * @param port
	 */
	protected void unregisterService(int port)
	{
		portServiceMap.remove(port);
	}
}
