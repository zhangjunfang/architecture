package com.ocean.rpc.server;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ocean.rpc.common.RpcContext;
import com.ocean.rpc.common.RpcMethods;
import com.ocean.rpc.io.ByteBufferStream;

/**
 * 
 * @author： ocean 创建时间：2015年12月18日 mail：zhangjunfang0505@163.com 描述：
 */
public class RpcHttpService extends RpcService {
	private boolean crossDomainEnabled = false;
	private boolean p3pEnabled = false;
	private boolean getEnabled = true;
	private long timeout = 30000;
	private final HashMap<String, Boolean> origins = new HashMap<String, Boolean>();
	private final static ThreadLocal<HttpContext> currentContext = new ThreadLocal<HttpContext>();

	public static HttpContext getCurrentContext() {
		return currentContext.get();
	}

	@Override
	public RpcMethods getGlobalMethods() {
		if (globalMethods == null) {
			globalMethods = new RpcHttpMethods();
		}
		return globalMethods;
	}

	@Override
	public void setGlobalMethods(RpcMethods methods) {
		if (methods instanceof RpcHttpMethods) {
			this.globalMethods = methods;
		} else {
			throw new ClassCastException(
					"methods must be a HproseHttpMethods instance");
		}
	}

	public boolean isCrossDomainEnabled() {
		return crossDomainEnabled;
	}

	public void setCrossDomainEnabled(boolean enabled) {
		crossDomainEnabled = enabled;
	}

	public boolean isP3pEnabled() {
		return p3pEnabled;
	}

	public void setP3pEnabled(boolean enabled) {
		p3pEnabled = enabled;
	}

	public boolean isGetEnabled() {
		return getEnabled;
	}

	public void setGetEnabled(boolean enabled) {
		getEnabled = enabled;
	}

	public void addAccessControlAllowOrigin(String origin) {
		origins.put(origin, true);
	}

	public void removeAccessControlAllowOrigin(String origin) {
		origins.remove(origin);
	}

	public void setTimeout(long value) {
		timeout = value;
	}

	public long getTimeout() {
		return timeout;
	}

	@Override
	protected Object[] fixArguments(Type[] argumentTypes, Object[] arguments,
			RpcContext context) {
		int count = arguments.length;
		HttpContext httpContext = (HttpContext) context;
		if (argumentTypes.length != count) {
			Object[] args = new Object[argumentTypes.length];
			System.arraycopy(arguments, 0, args, 0, count);
			Class<?> argType = (Class<?>) argumentTypes[count];
			if (argType.equals(RpcContext.class)) {
				args[count] = context;
			} else if (argType.equals(HttpContext.class)) {
				args[count] = httpContext;
			} else if (argType.equals(HttpServletRequest.class)) {
				args[count] = httpContext.getRequest();
			} else if (argType.equals(HttpServletResponse.class)) {
				args[count] = httpContext.getResponse();
			} else if (argType.equals(HttpSession.class)) {
				args[count] = httpContext.getSession();
			} else if (argType.equals(ServletContext.class)) {
				args[count] = httpContext.getApplication();
			} else if (argType.equals(ServletConfig.class)) {
				args[count] = httpContext.getConfig();
			}
			return args;
		}
		return arguments;
	}

	protected void sendHeader(HttpContext httpContext) throws IOException {
		if (event != null && RpcHttpServiceEvent.class.isInstance(event)) {
			((RpcHttpServiceEvent) event).onSendHeader(httpContext);
		}
		HttpServletRequest request = httpContext.getRequest();
		HttpServletResponse response = httpContext.getResponse();
		response.setContentType("text/plain");
		if (p3pEnabled) {
			response.setHeader("P3P",
					"CP=\"CAO DSP COR CUR ADM DEV TAI PSA PSD "
							+ "IVAi IVDi CONi TELo OTPi OUR DELi SAMi "
							+ "OTRi UNRi PUBi IND PHY ONL UNI PUR FIN "
							+ "COM NAV INT DEM CNT STA POL HEA PRE GOV\"");
		}
		if (crossDomainEnabled) {
			String origin = request.getHeader("Origin");
			if (origin != null && !origin.equals("null")) {
				if (origins.isEmpty() || origins.containsKey(origin)) {
					response.setHeader("Access-Control-Allow-Origin", origin);
					response.setHeader("Access-Control-Allow-Credentials",
							"true");
				}
			} else {
				response.setHeader("Access-Control-Allow-Origin", "*");
			}
		}
	}

	public void handle(HttpContext httpContext) throws IOException {
		handle(httpContext, null);
	}

	public void handle(HttpContext httpContext, RpcHttpMethods methods)
			throws IOException {
		sendHeader(httpContext);
		String method = httpContext.getRequest().getMethod();
		if (method.equals("GET")) {
			if (getEnabled) {
				ByteBufferStream ostream = null;
				try {
					ostream = doFunctionList(methods, httpContext);
					httpContext.getResponse().setContentLength(
							ostream.available());
					ostream.writeTo(httpContext.getResponse().getOutputStream());
				} catch (IOException ex) {
					fireErrorEvent(ex, httpContext);
				} finally {
					if (ostream != null) {
						ostream.close();
					}
				}
			} else {
				httpContext.getResponse().sendError(
						HttpServletResponse.SC_FORBIDDEN);
			}
		} else if (method.equals("POST")) {
			if (httpContext.getRequest().isAsyncSupported()) {
				asyncHandle(httpContext, methods);
			} else {
				syncHandle(httpContext, methods);
			}
		}
	}

	private void asyncHandle(final HttpContext httpContext,
			final RpcHttpMethods methods) {
		final AsyncContext async = httpContext.getRequest().startAsync();
		async.setTimeout(timeout);
		async.addListener(new AsyncListener() {
			@Override
			public void onComplete(AsyncEvent ae) throws IOException {
			}

			@Override
			public void onTimeout(AsyncEvent ae) throws IOException {
				ByteBufferStream ostream = sendError(ae.getThrowable(),
						httpContext);
				ae.getSuppliedResponse().setContentLength(ostream.available());
				ostream.writeTo(ae.getSuppliedResponse().getOutputStream());
			}

			@Override
			public void onError(AsyncEvent ae) throws IOException {
			}

			@Override
			public void onStartAsync(AsyncEvent ae) throws IOException {
			}
		});
		async.start(new Runnable() {
			@Override
			public void run() {
				ByteBufferStream istream = null;
				ByteBufferStream ostream = null;
				try {
					currentContext.set(httpContext);
					istream = new ByteBufferStream();
					istream.readFrom(async.getRequest().getInputStream());
					ostream = handle(istream, methods, httpContext);
					async.getResponse().setContentLength(ostream.available());
					ostream.writeTo(async.getResponse().getOutputStream());
				} catch (IOException ex) {
					fireErrorEvent(ex, httpContext);
				} finally {
					currentContext.remove();
					if (istream != null) {
						istream.close();
					}
					if (ostream != null) {
						ostream.close();
					}
					async.complete();
				}
			}
		});
	}

	private void syncHandle(HttpContext httpContext, RpcHttpMethods methods)
			throws IOException {
		ByteBufferStream istream = null;
		ByteBufferStream ostream = null;
		try {
			currentContext.set(httpContext);
			istream = new ByteBufferStream();
			istream.readFrom(httpContext.getRequest().getInputStream());
			ostream = handle(istream, methods, httpContext);
			httpContext.getResponse().setContentLength(ostream.available());
			ostream.writeTo(httpContext.getResponse().getOutputStream());
		} catch (IOException ex) {
			fireErrorEvent(ex, httpContext);
		} finally {
			currentContext.remove();
			if (istream != null) {
				istream.close();
			}
			if (ostream != null) {
				ostream.close();
			}
		}
	}
}
