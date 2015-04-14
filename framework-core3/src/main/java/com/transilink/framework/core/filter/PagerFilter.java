package com.transilink.framework.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.transilink.framework.core.utils.fileUtils.SysConfigUtil;
import com.transilink.framework.core.utils.pagesUtils.PageContext;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@SuppressWarnings("all")
public class PagerFilter implements Filter {
	public static final String PAGE_SIZE_NAME = "ps";
	private static String ROOT_PATH = "";
	private static String WEBROOT_PATH = "";
	private static int framework_default_pagesize = 10;

	public void destroy() {
		// TODO
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		PageContext.setOffset(getOffset(httpRequest));
		PageContext.setPageSize(getPageSize(httpRequest));
		try {
			chain.doFilter(request, response);
		} finally {
			PageContext.removeOffset();
			PageContext.removePageSize();
		}
	}

	private int getOffset(HttpServletRequest request) {
		int offset = 0;
		try {
			offset = Integer.parseInt(request.getParameter("offset"));
		} catch (Exception ignore) {
		}
		return offset;
	}

	private int getPageSize(HttpServletRequest httpRequest) {
		String psvalue = httpRequest.getParameter("limit");
		if (null != psvalue && !"".equals(psvalue.trim())) {
			Integer ps = 0;
			try {
				ps = Integer.parseInt(psvalue);
			} catch (Exception e) {
			}
			if (0 != ps) {
				httpRequest.getSession().setAttribute(PAGE_SIZE_NAME, ps);
			}
		}

		Integer pagesize = (Integer) httpRequest.getSession().getAttribute(
				PAGE_SIZE_NAME);
		if (null == pagesize) {
			if (StringUtils.isNotBlank(SysConfigUtil
					.get("framework.default.pagesize"))) {
				framework_default_pagesize = NumberUtils.toInt(SysConfigUtil
						.get("framework_default_pagesize"));
			}
			httpRequest.getSession().setAttribute(PAGE_SIZE_NAME, 10);
			return 10;
		}

		return pagesize;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		String realPath = arg0.getServletContext().getRealPath("/");
		WEBROOT_PATH = realPath;
		String rootPath = arg0.getServletContext().getContextPath();
		if (null == rootPath) {
			rootPath = "";
		}
		ROOT_PATH = rootPath;
	}

	public static String getRootPath() {
		return ROOT_PATH;
	}

	public static String getWebRootPath() {
		return WEBROOT_PATH;
	}
}
