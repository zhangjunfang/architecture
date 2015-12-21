
package org.springframework.ocean.remoting.server;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * 
 * @author： ocean
 * @创建时间：2015年12月21日
 * @mail：zhangjunfang0505@163.com
 * @描述：Hessian服务暴露组件，基于Netty 3.x和Spring  服务发布方式
 */
public class HessianServiceExporter extends AbstractHessianServiceExporter implements Runnable
{

	private static Log log = LogFactory.getLog(HessianServiceExporter.class);
	/**
	 * -----------socket设置：
	 * 
	 * <pre>
	 * <li>没有child前缀的为ServerSocket的设置
	 * <li>有child的为接受到的Socket的设置
	 */
	private Map<String, Object> options;
	/**
	 * -----------线程池设置
	 */
	/**
	 * 线程池容量也是初始容量和最大容量
	 */
	private int corePoolSize = 100;
	/**
	 * 空闲线程最大存活时间,单位秒
	 */
	private int keepAliveTime = 10;
	/**
	 * -----------Netty相关
	 */
	private ChannelFactory channelFactory;
	private Channel serverChannel;
	private ThreadPoolExecutor executor;

	/*
	 * 由线程池异步启动
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		waitServiceReady();
		setServerStatus(ServiceStatus.Starting);
		channelFactory = new NioServerSocketChannelFactory(executor, executor);
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.setFactory(channelFactory);
		bootstrap.setPipelineFactory(new HessianServicePipelineFactory(getSkeleton(), executor));
		bootstrap.setOptions(getOptions());
		serverChannel = bootstrap.bind(new InetSocketAddress(port));
		setServerStatus(ServiceStatus.Started);
		registerService(port);
		log.info("服务[" + getServiceInterface().getName() + "]端口[" + port + "]绑定成功");
	}

	@Override
	public void startUp()
	{
		initSkeleton();
		/**
		 * 采用无边界队列防止由于饱和导致客户端请求失败，然而采用无边界的队列必然要让线程池线程数固定
		 */
		executor = new ThreadPoolExecutor(corePoolSize, corePoolSize, keepAliveTime, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		/**
		 * 通过jvisualvm可以看出在任务完成后活动线程全部被回收，如果不设置或者为false任务完成后活动数还是为corePoolSize
		 */
		executor.allowCoreThreadTimeOut(true);
		executor.execute(this);
	}

	@Override
	public void shutDown()
	{
		setServerStatus(ServiceStatus.Stopping);
		serverChannel.close().awaitUninterruptibly();
		channelFactory.releaseExternalResources();
		setServerStatus(ServiceStatus.Stopped);
		unregisterService(port);
		log.info("服务[" + getServiceInterface().getName() + "]端口[" + port + "]停止");
	}

	/**
	 * 设置socket属性
	 */
	public void setOptions(Map<String, Object> options)
	{
		this.options = options;
	}

	/**
	 * socket选项设置，没有配置则采用默认配置
	 * 
	 * @return the options
	 */
	Map<String, Object> getOptions()
	{
		if (options == null)
			options = new HashMap<String, Object>(3);
		if (options.isEmpty())
		{
			options.put("backlog", 500);
			options.put("reuseAddress", true);
			options.put("child.tcpNoDelay", true);
		}
		return options;
	}

	/**
	 * @param corePoolSize
	 *            the corePoolSize to set
	 */
	public void setCorePoolSize(int corePoolSize)
	{
		this.corePoolSize = corePoolSize;
	}

	/**
	 * @param keepAliveTime
	 *            the keepAliveTime to set
	 */
	public void setKeepAliveTime(int keepAliveTime)
	{
		this.keepAliveTime = keepAliveTime;
	}
}
