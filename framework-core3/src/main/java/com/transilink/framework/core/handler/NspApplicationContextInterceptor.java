package com.transilink.framework.core.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.transilink.framework.core.filter.PagerFilter;
import com.transilink.framework.core.logs.LogEnabled;
import com.transilink.framework.core.utils.fileUtils.SysConfigUtil;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class NspApplicationContextInterceptor extends HandlerInterceptorAdapter
		implements LogEnabled {
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Map ctxMap = new HashMap();
		if (modelAndView != null) {
			String viewName = modelAndView.getViewName();

			String envRoot = PagerFilter.getRootPath() + "/"
					+ SysConfigUtil.get("framework.springContext");
			log.info("viewName : " + viewName);
			ctxMap.put("ctx", PagerFilter.getRootPath());
			ctxMap.put("viewName", viewName);
			ctxMap.put("mvcRoot", SysConfigUtil.get("framework.springContext"));
			ctxMap.put("envRoot", envRoot);
			modelAndView.addObject("_project", ctxMap);
		}
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}