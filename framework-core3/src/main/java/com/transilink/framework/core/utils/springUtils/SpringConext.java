package com.transilink.framework.core.utils.springUtils;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

/**
 * spring上下文工具类
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@Component
public class SpringConext implements ApplicationContextAware,
		ServletContextAware {

	private static ApplicationContext context;

	private static ServletContext servletContext;

	public void setApplicationContext(ApplicationContext acx) {
		context = acx;
	}

	/**
	 * 获取本地spring环境
	 *
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return context;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	@SuppressWarnings("static-access")
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
