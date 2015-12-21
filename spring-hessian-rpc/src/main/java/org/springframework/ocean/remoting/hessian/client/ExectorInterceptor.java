/**
 * 
 */
package org.springframework.ocean.remoting.hessian.client;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.ocean.remoting.hessian.common.Request;
import org.springframework.ocean.remoting.hessian.common.ServiceExecutor;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 
 * @author： ocean 创建时间：2015年12月21日 mail：zhangjunfang0505@163.com 描述：
 */
public class ExectorInterceptor implements MethodInterceptor {

	private HessianProxyFactory proxyFactory = new HessianProxyFactory();

	private static final Map<String, Object> proxyCache = new ConcurrentHashMap<String, Object>(64);

	/**
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if ("toString".equals(invocation.getMethod().getName())) {
			// System.out.println("调用toString方法");
			return invocation.getThis();
		} else if ("execute".equals(invocation.getMethod().getName())) {
			// System.out.println("调用execute方法");
			Request request = (Request) invocation.getArguments()[0];
			// 远程暴露的统一地址
			// http://localhost:8080/account/service
			String serviceUrl = request.getUrl();
			return invocation.getMethod().invoke(
					createHessianProxy(serviceUrl, ServiceExecutor.class),
					invocation.getArguments());
		}
		throw new Exception("Methods the unrealized");
	}

	protected Object createHessianProxy(String serviceUrl,
			Class<?> serviceInterface) throws MalformedURLException {
		// 缓存每个系统的代理对象
		Object proxy = proxyCache.get(serviceUrl);
		if (proxy == null) {
			proxy = proxyFactory.create(serviceInterface, serviceUrl);
			proxyCache.put(serviceUrl, proxy);
		}
		return proxy;
	}
}