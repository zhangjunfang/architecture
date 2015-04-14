package com.transilink.framework.core.exception;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public abstract interface HttpStatusException {
	public static final String BASE_HTTP = "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html";
	public static final String BASE_WEBDAV = "http://www.webdav.org/specs/rfc2518.html";
	public static final String BASE_RESTLET = "http://www.restlet.org/documentation/1.1/api/";

	public abstract int getCode();

	public abstract String getName();

	public abstract String getDescription();

	public abstract String getUri();
}