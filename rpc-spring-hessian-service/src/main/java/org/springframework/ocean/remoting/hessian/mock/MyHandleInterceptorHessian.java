/**
 * 2015年12月20日
 */
package org.springframework.ocean.remoting.hessian.mock;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ocean.remoting.hessian.HandleInterceptorHessian;

/**
 * @author： ocean
 * 创建时间：2015年12月20日
 * mail：zhangjunfang0505@163.com
 * 描述： 
 */
public class MyHandleInterceptorHessian implements HandleInterceptorHessian {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		System.err.println("session Id :"+request.getSession().getId());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		System.err.println("response status :"+response.getStatus());
	}

}
