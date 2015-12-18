package com.ocean.rpc.server;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ocean.rpc.common.RpcContext;

/**
 * 
 * @author： ocean 创建时间：2015年12月18日 mail：zhangjunfang0505@163.com 描述：
 */
public class HttpContext extends RpcContext {
	private final ServletContext application;
	private final ServletConfig config;
	private final HttpServletRequest request;
	private final HttpServletResponse response;

	public HttpContext(HttpServletRequest request,
			HttpServletResponse response, ServletConfig config,
			ServletContext application) {
		this.request = request;
		this.response = response;
		this.config = config;
		this.application = application;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public HttpSession getSession() {
		return request.getSession();
	}

	public HttpSession getSession(boolean create) {
		return request.getSession(create);
	}

	public ServletConfig getConfig() {
		return config;
	}

	public ServletContext getApplication() {
		return application;
	}
}