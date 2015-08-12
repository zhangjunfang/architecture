/**
 * 
 */
package org.framework.core.context;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * 描述：
 * 
 * @author ocean
 * 2015年8月11日
 *  email：zhangjunfang0505@163.com
 */
public final  class HttpContextFactory {
	public static final String ATTRIBUTE_KEY = "framework.utils.context.Context";

	private HttpContextFactory() {
	}

	/**
	 * 获取当请上下文环境
	 *
	 * @param servletrequest
	 * @return
	 */
	public static final Context getContext(
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

	public static final Context getContext(
			ServletRequest servletrequest, boolean flag) {
		HttpContext httpContext;
		if (servletrequest != null) {
			if ((httpContext = (HttpContext) servletrequest
					.getAttribute(ATTRIBUTE_KEY)) == null) {
				if (!flag) {
					return null;
				}
				httpContext = new HttpContext();
				servletrequest.setAttribute(ATTRIBUTE_KEY,
						httpContext);
			}
			httpContext
					.setRequest((HttpServletRequest) servletrequest);
		} else {
			httpContext = new HttpContext();
		}
		return httpContext;
	}
}
