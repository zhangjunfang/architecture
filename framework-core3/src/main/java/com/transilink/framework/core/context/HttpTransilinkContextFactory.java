package com.transilink.framework.core.context;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * 平台上下文环境工厂类
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public final class HttpTransilinkContextFactory {

	public static final String ATTRIBUTE_KEY = "framework.utils.context.TransilinkContext";

	private HttpTransilinkContextFactory() {
	}

	/**
	 * 获取当请上下文环境
	 *
	 * @param servletrequest
	 * @return
	 */
	public static final TransilinkContext getContext(
			ServletRequest servletrequest) {
		return getContext(servletrequest, true);
	}

	/**
	 * 获取上下文环境
	 *
	 * @param servletrequest
	 * @param flag
	 *            是否创建标识
	 * @return
	 */

	public static final TransilinkContext getContext(
			ServletRequest servletrequest, boolean flag) {
		HttpTransilinkContext httpTransilinkContext;
		if (servletrequest != null) {
			if ((httpTransilinkContext = (HttpTransilinkContext) servletrequest
					.getAttribute(ATTRIBUTE_KEY)) == null) {
				if (!flag) {
					return null;
				}
				httpTransilinkContext = new HttpTransilinkContext();
				servletrequest.setAttribute(ATTRIBUTE_KEY,
						httpTransilinkContext);
			}
			httpTransilinkContext
					.setRequest((HttpServletRequest) servletrequest);
		} else {
			httpTransilinkContext = new HttpTransilinkContext();
		}
		return httpTransilinkContext;
	}
}
