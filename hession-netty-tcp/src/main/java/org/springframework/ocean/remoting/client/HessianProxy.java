
package org.springframework.ocean.remoting.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ocean.remoting.util.BeanUtil;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.caucho.services.server.AbstractSkeleton;

/**
 * 
 * @author： ocean
 * @创建时间：2015年12月21日
 * @mail：zhangjunfang0505@163.com
 * @描述：Hessian底层代理，基于Hessian协议版本1，socket短连接，每次调用创建一个socket，<b>非线程安全</b>
 */
public class HessianProxy implements InvocationHandler
{

	private static Log logger = LogFactory.getLog(HessianProxyFactory.class);
	private final String ip;
	private final int port;
	private final Map<Object, Object> globalHeaders;
	private Socket conn = null;
	private HessianOutput out;
	private HessianInput in;
	private BufferedOutputStream bos;
	private BufferedInputStream bis;
	/**
	 * socket选项，每次调用都有效
	 */
	private boolean tcpNoDelay = true;
	private int soTimeOut = 15000;// 服务器处理能力可能跟不上，默认大些
	private int timeOut = 10000;

	public HessianProxy(String ip, int port, Map<Object, Object> globalHeaders)
	{
		this.ip = ip;
		this.port = port;
		this.globalHeaders = globalHeaders;
	}

	/**
	 * 获取底层代理 {@link HessianProxy}
	 * 
	 * @param targetProxyObject
	 *            代理对象
	 * @return
	 */
	public static HessianProxy getInstanceFrom(Object targetProxyObject)
	{
		if (targetProxyObject == null)
			throw new NullPointerException("参数不能为空");
		if (Proxy.isProxyClass(targetProxyObject.getClass()))
			return (HessianProxy) Proxy.getInvocationHandler(targetProxyObject);
		else
			throw new IllegalArgumentException("传入非代理对象");
	}

	/**
	 * 判断socket是否连接上
	 * 
	 * @param socket
	 * @return
	 */
	private boolean isConnected(Socket socket)
	{
		return socket != null && socket.isConnected() && !socket.isClosed();
	}

	/**
	 * 打开一个socket连接
	 * 
	 * @return
	 * @throws HessianConnectFaildException
	 */
	private boolean open() throws HessianConnectFaildException
	{
		if (!isConnected(conn))
		{
			try
			{
				conn = new Socket();
				// 在连接前设置选项有效
				conn.setTcpNoDelay(tcpNoDelay);
				conn.setSoTimeout(soTimeOut);
				InetSocketAddress addr = new InetSocketAddress(ip, port);
				conn.connect(addr, timeOut);
				bos = new BufferedOutputStream(conn.getOutputStream());
				out = getHessianOutput(bos);
				bis = new BufferedInputStream(conn.getInputStream());
				in = getHessianInput(bis);
				return isConnected(conn);
			}
			catch (IOException e)
			{
				close();
				throw new HessianConnectFaildException("Hessian连接[" + ip + ":" + port + "]失败", e);
			}
		}
		return true;
	}

	/**
	 * 关闭socket连接并释放相关资源
	 */
	private void close()
	{
		BeanUtil.closeResource(new Object[] { bos, out, bis, in, conn });
		out = null;
		in = null;
		bos = null;
		bis = null;
		conn = null;
	}

	protected String mangleName(Method method)
	{
		Class<?>[] param = method.getParameterTypes();
		if (param == null || param.length == 0)
			return method.getName();
		else
			return AbstractSkeleton.mangleName(method, false);
	}

	/**
	 * Handles the object invocation.
	 * 
	 * @param proxy
	 *            the proxy object to invoke
	 * @param method
	 *            the method to call
	 * @param args
	 *            the arguments to the proxy object
	 */
	@Override
	public synchronized Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		String methodName = method.getName();
		Class<?>[] params = method.getParameterTypes();
		// equals and hashCode are special cased
		if (methodName.equals("equals") && params.length == 1 && params[0].equals(Object.class))
		{
			Object value = args[0];
			if (value == null || !Proxy.isProxyClass(value.getClass()))
				return new Boolean(false);
			HessianProxy handler = (HessianProxy) Proxy.getInvocationHandler(value);
			return new Boolean(ip.equals(handler.getIp()) && port == handler.getPort());
		}
		else if (methodName.equals("hashCode") && params.length == 0)
			return new Integer(ip.hashCode());
		else if (methodName.equals("getHessianType"))
			return proxy.getClass().getInterfaces()[0].getName();
		else if (methodName.equals("getHessianURL"))
			return "hessian://" + ip + ":" + port;
		else if (methodName.equals("open"))
			return new Boolean(open());
		else if (methodName.equals("close"))
		{
			close();
			return null;
		}
		else if (methodName.equals("toString") && params.length == 0)
			return "[HessianProxy " + ip + ":" + port + "]";
		try
		{
			open();
			methodName = mangleName(method);// 解决方法重载问题
			out.startCall();
			if (globalHeaders != null)
			{
				for (Iterator<Map.Entry<Object, Object>> it = globalHeaders.entrySet().iterator(); it.hasNext();)
				{
					Map.Entry<Object, Object> e = it.next();
					out.writeHeader(String.valueOf(e.getKey()));
					out.writeObject(e.getValue());
					if (logger.isDebugEnabled())
						logger.debug("Head " + e.getKey() + "=" + e.getValue());
				}
			}
			out.writeMethod(methodName);
			if (args != null)
			{
				for (int i = 0; i < args.length; i++)
					out.writeObject(args[i]);
			}
			out.completeCall();
			bos.flush();
			return in.readReply(method.getReturnType());
		}
		catch (HessianConnectFaildException e)
		{
			throw e;
		}
		finally
		{
			close();
		}
	}

	/**
	 * @return 返回 ip。
	 */
	public String getIp()
	{
		return ip;
	}

	/**
	 * @return 返回 port。
	 */
	public int getPort()
	{
		return port;
	}

	public void setTcpNoDelay(boolean tcpNoDelay)
	{
		this.tcpNoDelay = tcpNoDelay;
	}

	/**
	 * 设置SO_TIMEOUT（读取超时时间）,外部代码调用：
	 * 
	 * <pre>
	 * HessianProxy realProxy = HessianProxy.getInstanceFrom(proxyedConfigManager);
	 * realProxy.setSoTimeOut(0);
	 * @param soTimeOut 单位毫秒
	 */
	public void setSoTimeOut(int soTimeOut)
	{
		this.soTimeOut = soTimeOut;
		if (isConnected(conn))
		{
			try
			{
				conn.setSoTimeout(soTimeOut);
			}
			catch (SocketException e)
			{
				logger.warn("设置[" + conn + "]的SO_TIMEOUT[" + soTimeOut + "]失败");
			}
		}
	}

	public HessianInput getHessianInput(InputStream is)
	{
		return new HessianInput(is);
	}

	public HessianOutput getHessianOutput(OutputStream os)
	{
		return new HessianOutput(os);
	}

	/**
	 * 设置连接超时时间
	 * 
	 * @param timeOut
	 *            单位毫秒
	 */
	public void setTimeOut(int timeOut)
	{
		this.timeOut = timeOut;
	}
}