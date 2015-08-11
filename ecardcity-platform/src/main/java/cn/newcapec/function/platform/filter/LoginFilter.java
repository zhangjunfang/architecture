package cn.newcapec.function.platform.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;







/**
 *用户登录过滤器
 * @author ocean
 * date : 2014-4-18 下午04:24:58
 * email : zhangjunfang0505@163.com
 * Copyright : newcapec zhengzhou
 */
@SuppressWarnings("all")
public class LoginFilter implements Filter {



	/**
	 *允许访问的url
	 */
	private List<String> allowUrl = new ArrayList<String>();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;



	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		splitUrl(config.getInitParameter("allowUrl"));
	}

	private void splitUrl(String urlStr) {
		if (urlStr != null && urlStr.length() > 0) {
			allowUrl = Arrays.asList(StringUtils.stripAll(urlStr.split(";")));
		}
	}
}