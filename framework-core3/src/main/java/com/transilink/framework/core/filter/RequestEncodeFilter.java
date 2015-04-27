package com.transilink.framework.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.transilink.framework.core.utils.httpUtils.EscapeUtil;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class RequestEncodeFilter implements Filter {
	private String fromChartSet;
	private String toChartSet;

	public RequestEncodeFilter() {
		this.fromChartSet = "iso8859-1";

		this.toChartSet = "UTF-8";
	}

	public void setFromChartSet(String fromChartSet) {
		this.fromChartSet = fromChartSet;
	}

	public void setToChartSet(String toChartSet) {
		this.toChartSet = toChartSet;
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String method = httpServletRequest.getMethod();
		if (method.equalsIgnoreCase("get")) {
			EscapeUtil.requestConvertEncode(httpServletRequest,
					this.fromChartSet, this.toChartSet);
		} else if (method.equalsIgnoreCase("post")) {
			String qs = httpServletRequest.getQueryString();
			if (qs != null) {
				EscapeUtil.requestConvertPostUrlEncode(httpServletRequest,
						this.fromChartSet, this.toChartSet,
						new String(qs.getBytes(this.fromChartSet),
								this.toChartSet));
			}
		}
		arg2.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}