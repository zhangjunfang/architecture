/**
 * 
 */
package org.springframework.ocean.remoting.hessian.client;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.UUID;

import javax.annotation.Resource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.ocean.remoting.hessian.common.Request;
import org.springframework.ocean.remoting.hessian.common.Response;
import org.springframework.ocean.remoting.hessian.common.ServiceExecutor;
import org.springframework.ocean.remoting.hessian.common.Utils;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.RemoteProxyFailureException;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;
import org.springframework.util.Assert;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianRuntimeException;
import com.caucho.hessian.io.HessianFieldException;
import com.caucho.hessian.io.HessianProtocolException;
import com.caucho.hessian.io.SerializerFactory;

/**
 * 
 * @author： ocean 创建时间：2015年12月21日 mail：zhangjunfang0505@163.com 描述：
 */
public class ServiceInterceptor extends UrlBasedRemoteAccessor implements
		MethodInterceptor {
	private HessianProxyFactory proxyFactory = new HessianProxyFactory();

	@Resource
	ServiceExecutor serviceExecutor;

	private Object hessianProxy;

	private String serviceName;

	/**
	 * Getter method for property <tt>serviceName</tt>.
	 * 
	 * @return property value of serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Setter method for property <tt>serviceName</tt>.
	 * 
	 * @param serviceName
	 *            value to be assigned to property serviceName
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * Set the HessianProxyFactory instance to use. If not specified, a default
	 * HessianProxyFactory will be created.
	 * <p>
	 * Allows to use an externally configured factory instance, in particular a
	 * custom HessianProxyFactory subclass.
	 */
	public void setProxyFactory(HessianProxyFactory proxyFactory) {
		this.proxyFactory = (proxyFactory != null ? proxyFactory
				: new HessianProxyFactory());
	}

	/**
	 * Specify the Hessian SerializerFactory to use.
	 * <p>
	 * This will typically be passed in as an inner bean definition of type
	 * {@code com.caucho.hessian.io.SerializerFactory}, with custom bean
	 * property values applied.
	 */
	public void setSerializerFactory(SerializerFactory serializerFactory) {
		this.proxyFactory.setSerializerFactory(serializerFactory);
	}

	/**
	 * Set whether to send the Java collection type for each serialized
	 * collection. Default is "true".
	 */
	public void setSendCollectionType(boolean sendCollectionType) {
		this.proxyFactory.getSerializerFactory().setSendCollectionType(
				sendCollectionType);
	}

	/**
	 * Set whether overloaded methods should be enabled for remote invocations.
	 * Default is "false".
	 * 
	 * @see com.caucho.hessian.client.HessianProxyFactory#setOverloadEnabled
	 */
	public void setOverloadEnabled(boolean overloadEnabled) {
		this.proxyFactory.setOverloadEnabled(overloadEnabled);
	}

	/**
	 * Set the username that this factory should use to access the remote
	 * service. Default is none.
	 * <p>
	 * The username will be sent by Hessian via HTTP Basic Authentication.
	 * 
	 * @see com.caucho.hessian.client.HessianProxyFactory#setUser
	 */
	public void setUsername(String username) {
		this.proxyFactory.setUser(username);
	}

	/**
	 * Set the password that this factory should use to access the remote
	 * service. Default is none.
	 * <p>
	 * The password will be sent by Hessian via HTTP Basic Authentication.
	 * 
	 * @see com.caucho.hessian.client.HessianProxyFactory#setPassword
	 */
	public void setPassword(String password) {
		this.proxyFactory.setPassword(password);
	}

	/**
	 * Set whether Hessian's debug mode should be enabled. Default is "false".
	 * 
	 * @see com.caucho.hessian.client.HessianProxyFactory#setDebug
	 */
	public void setDebug(boolean debug) {
		this.proxyFactory.setDebug(debug);
	}

	/**
	 * Set whether to use a chunked post for sending a Hessian request.
	 * 
	 * @see com.caucho.hessian.client.HessianProxyFactory#setChunkedPost
	 */
	public void setChunkedPost(boolean chunkedPost) {
		this.proxyFactory.setChunkedPost(chunkedPost);
	}

	/**
	 * Set the timeout to use when waiting for a reply from the Hessian service.
	 * 
	 * @see com.caucho.hessian.client.HessianProxyFactory#setReadTimeout
	 */
	public void setReadTimeout(long timeout) {
		this.proxyFactory.setReadTimeout(timeout);
	}

	/**
	 * Set whether version 2 of the Hessian protocol should be used for parsing
	 * requests and replies. Default is "false".
	 * 
	 * @see com.caucho.hessian.client.HessianProxyFactory#setHessian2Request
	 */
	public void setHessian2(boolean hessian2) {
		this.proxyFactory.setHessian2Request(hessian2);
		this.proxyFactory.setHessian2Reply(hessian2);
	}

	/**
	 * Set whether version 2 of the Hessian protocol should be used for parsing
	 * requests. Default is "false".
	 * 
	 * @see com.caucho.hessian.client.HessianProxyFactory#setHessian2Request
	 */
	public void setHessian2Request(boolean hessian2) {
		this.proxyFactory.setHessian2Request(hessian2);
	}

	/**
	 * Set whether version 2 of the Hessian protocol should be used for parsing
	 * replies. Default is "false".
	 * 
	 * @see com.caucho.hessian.client.HessianProxyFactory#setHessian2Reply
	 */
	public void setHessian2Reply(boolean hessian2) {
		this.proxyFactory.setHessian2Reply(hessian2);
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		prepare();
	}

	/**
	 * Initialize the Hessian proxy for this interceptor.
	 * 
	 * @throws RemoteLookupFailureException
	 *             if the service URL is invalid
	 */
	public void prepare() throws RemoteLookupFailureException {
		try {
			this.hessianProxy = createHessianProxy(this.proxyFactory);
		} catch (MalformedURLException ex) {
			throw new RemoteLookupFailureException("Service URL ["
					+ getServiceUrl() + "] is invalid", ex);
		}
	}

	/**
	 * Create the Hessian proxy that is wrapped by this interceptor.
	 * 
	 * @param proxyFactory
	 *            the proxy factory to use
	 * @return the Hessian proxy
	 * @throws MalformedURLException
	 *             if thrown by the proxy factory
	 * @see com.caucho.hessian.client.HessianProxyFactory#create
	 */
	protected Object createHessianProxy(HessianProxyFactory proxyFactory)
			throws MalformedURLException {
		Assert.notNull(getServiceInterface(), "'serviceInterface' is required");
		return proxyFactory.create(getServiceInterface(), getServiceUrl());
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (this.hessianProxy == null) {
			throw new IllegalStateException(
					"HessianClientInterceptor is not properly initialized - "
							+ "invoke 'prepare' before attempting any operations");
		}

		ClassLoader originalClassLoader = overrideThreadContextClassLoader();
		try {

			// 将原有的请求进行包装
			// 调用统一的服务接口
			// 调用的实际参数
			Method method = invocation.getMethod();
			Object[] arguments = invocation.getArguments();

			Request request = new Request();
			String[] methodArgSigs = Utils.createParamSignature(method
					.getParameterTypes());
			request.setMethodName(method.getName());
			request.setMethodArgSigs(methodArgSigs);
			request.setMethodArgs(arguments);
			request.setServiceName(serviceName);
			request.setRequestId(UUID.randomUUID().toString());
			request.setUrl(getServiceUrl());
			request.setTime(System.currentTimeMillis());
			request.setInterfaceName(getServiceInterface().getName());
			request.setLocalAddr(Utils.getNetworkAddress());

			System.out.println("=================请求信息=================");
			System.out.println("请求ID：" + request.getRequestId());
			System.out.println("发送时间：" + request.getTime());
			System.out.println("URL：" + request.getUrl());
			System.out.println("服务名称：" + request.getServiceName());
			System.out.println("IP：" + request.getLocalAddr());
			System.out.println("方法名：" + request.getMethodName());
			System.out.println("方法参数签名："
					+ Arrays.toString(request.getMethodArgSigs()));
			System.out.println("接口：" + request.getInterfaceName());

			Response response = serviceExecutor.execute(request);

			System.out.println("=================响应信息=================");
			System.out.println("请求ID：" + response.getRequest().getRequestId());
			System.out.println("响应时间：" + response.getTime());
			System.out.println("响应结果：" + response.getResponseData());
			System.out.println("被调用IP：" + response.getLocalAddr());

			return response.getResponseData();
		} catch (Exception ex) {
			// // Hessian 4.0 check: another layer of InvocationTargetException.
			// if (targetEx instanceof InvocationTargetException) {
			// targetEx = ((InvocationTargetException)
			// targetEx).getTargetException();
			// }
			// if (targetEx instanceof HessianConnectionException) {
			// throw convertHessianAccessException(targetEx);
			// }
			if (ex instanceof HessianRuntimeException) {
				Throwable cause = ex.getCause();
				throw convertHessianAccessException(cause != null ? cause : ex);
			} else if (ex instanceof UndeclaredThrowableException) {
				UndeclaredThrowableException utex = (UndeclaredThrowableException) ex;
				throw convertHessianAccessException(utex
						.getUndeclaredThrowable());
			} else {
				throw ex;
			}
		} catch (Throwable ex) {
			throw new RemoteProxyFailureException(
					"Failed to invoke Hessian proxy for remote service ["
							+ getServiceUrl() + "]", ex);
		} finally {
			resetThreadContextClassLoader(originalClassLoader);
		}
	}

	/**
	 * Convert the given Hessian access exception to an appropriate Spring
	 * RemoteAccessException.
	 * 
	 * @param ex
	 *            the exception to convert
	 * @return the RemoteAccessException to throw
	 */
	protected RemoteAccessException convertHessianAccessException(Throwable ex) {
		if (ex instanceof ConnectException) {
			return new RemoteAccessException("[" + getServiceUrl()
					+ "] 服务无法连接，");
		} else if (ex instanceof HessianFieldException) {
			return new RemoteAccessException("[" + getServiceUrl()
					+ "] 接口返回对象异常，" + ex.getMessage());
		} else if (ex instanceof HessianProtocolException) {
			return new RemoteAccessException("[" + getServiceUrl()
					+ "] Hessian协议异常，" + ex.getMessage());
		} else {
			return new RemoteAccessException(
					"Cannot access Hessian remote service at ["
							+ getServiceUrl() + "]", ex);
		}
	}

}