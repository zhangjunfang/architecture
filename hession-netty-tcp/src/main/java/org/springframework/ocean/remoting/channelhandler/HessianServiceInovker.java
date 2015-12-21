
package org.springframework.ocean.remoting.channelhandler;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.springframework.ocean.remoting.io.InputStreamBuffer;
import org.springframework.ocean.remoting.server.AbstractHessianServiceExporter;
import org.springframework.ocean.remoting.server.ServiceStatus;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.caucho.hessian.server.HessianSkeleton;
import com.caucho.services.server.ServiceContext;

/**
 *  
 * @author： ocean
 * @创建时间：2015年12月21日
 * @mail：zhangjunfang0505@163.com
 * @描述：hessian服务执行handler
 */
 
public class HessianServiceInovker extends SimpleChannelHandler
{

	private static Log log = LogFactory.getLog(HessianServiceInovker.class);
	private HessianSkeleton hessianSkeleton;
	public static final String ID_FROM = "__from";
	/**
	 * 未完成请求数
	 */
	private static AtomicInteger currentRequstNumber = new AtomicInteger(0);
	/**
	 * 异常请求数
	 */
	private static AtomicInteger exceptionRequestNumber = new AtomicInteger(0);
	/**
	 * 成功请求数
	 */
	private static AtomicInteger sucessRequestNumber = new AtomicInteger(0);
	static
	{
		new Thread(new Runnable()
		{

			private void threadSleep()
			{
				try
				{
					Thread.sleep(10000);
				}
				catch (InterruptedException e)
				{
				}
			}

			@Override
			public void run()
			{
				Collection<AbstractHessianServiceExporter> services = null;
				while (true)
				{
					services = AbstractHessianServiceExporter.getAllService();
					log.info("#Hessian存活对外暴露服务：");
					for (AbstractHessianServiceExporter service : services)
					{
						if (service != null && service.getServiceStatus() == ServiceStatus.Started)
						{
							log.info("	[" + service.getServiceInterface().getName() + "][" + service.getPort() + "]");
						}
					}
					log.info("#未完成请求数：" + currentRequstNumber.get());
					log.info("#异常请求数：" + exceptionRequestNumber.get());
					log.info("#成功请求数：" + sucessRequestNumber.get());
					threadSleep();
				}
			}
		}).start();
	}

	@SuppressWarnings("unchecked")
	static <T> T cast(Object obj)
	{
		return (T) obj;
	}

	/**
	 * 从 {@link Channel}获取远程IP地址信息
	 * 
	 * @param channel
	 * @return
	 */
	static String retrieveRemoteIPFrom(Channel channel)
	{
		if (channel != null && channel.getRemoteAddress() != null)
		{
			if (channel.getRemoteAddress() instanceof InetSocketAddress)
			{
				return ((InetSocketAddress) channel.getRemoteAddress()).getAddress().getHostAddress();
			}
		}
		return null;
	}

	/**
	 * 打印当前方法的调用栈信息
	 */
	public static void printCallStack()
	{
		/**
		 * 最顶上的为Thread.currentThread().getStackTrace()<br>
		 * 其次是printCallStack
		 */
		StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
		StackTraceElement currentElement;
		for (int i = 2; i < callStack.length; i++)
		{
			currentElement = callStack[i];
			log.info(currentElement.getClassName() + "." + currentElement.getMethodName() + "(" + currentElement.getFileName()
					+ ":" + currentElement.getLineNumber() + ")");
		}
	}

	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
		ServiceContext.begin(null, null, null);
		ServiceContext.getContext().addHeader(ID_FROM, retrieveRemoteIPFrom(e.getChannel()));
		InputStreamBuffer inputStreamBuffer = cast(e.getMessage());
		/**
		 * 采用while循环，兼容长连接
		 */
		// 可能连接为长连接，判断是否结束
		boolean isEnd = false;
		while (!isEnd)
		{
			try
			{
				getHessianSkeleton().invoke(new HessianInput(inputStreamBuffer),
						new HessianOutput(OutputStreamEncoder.getOutputStream(ctx)));
			}
			catch (Exception e1)
			{
				// 需要准确判断异常类型，来判断是否请求结束
				isEnd = true;
			}
		}
		sucessRequestNumber.incrementAndGet();
		ServiceContext.end();
	}

	/*
	 * 客户端关闭channel触发服务端对应的channel关闭
	 */
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
	{
		currentRequstNumber.incrementAndGet();
		if (getHessianSkeleton() == null)
		{
			HessianSkeleton skeleton = cast(ctx.getAttachment());
			setHessianSkeleton(skeleton);
		}
		super.channelConnected(ctx, e);
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
	{
		log.error("发生异常:" + e.getCause().getMessage(), e.getCause());
		exceptionRequestNumber.incrementAndGet();
		e.getChannel().close();
	}

	
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
	{
		// printCallStack();
		log.debug("断开连接[" + e.getChannel().getRemoteAddress() + "," + e.getChannel().getLocalAddress() + "]");
		currentRequstNumber.decrementAndGet();
		super.channelDisconnected(ctx, e);
	}

	
	public HessianSkeleton getHessianSkeleton()
	{
		return hessianSkeleton;
	}

	
	private void setHessianSkeleton(HessianSkeleton hessianSkeleton)
	{
		this.hessianSkeleton = hessianSkeleton;
	}
}
