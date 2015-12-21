/**
 * 2015年12月19日
 */
package org.springframework.ocean.remoting.hessian;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author： ocean 创建时间：2015年12月19日 mail：zhangjunfang0505@163.com 描述:
 */
public interface HandleInterceptorHessian {

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response) throws  IOException, ServletException;

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response) throws  IOException, ServletException;

}
