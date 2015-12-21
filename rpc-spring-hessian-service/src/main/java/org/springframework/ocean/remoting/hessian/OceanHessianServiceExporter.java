/**
 * 2015年12月19日
 */
package org.springframework.ocean.remoting.hessian;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.remoting.caucho.HessianServiceExporter;

/**
 * @author： ocean 创建时间：2015年12月19日 mail：zhangjunfang0505@163.com 描述：
 */
public class OceanHessianServiceExporter extends HessianServiceExporter {

	private HandleInterceptorHessian[] handleInterceptor;

	private boolean interceptorMark = false;

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (interceptorMark && handleInterceptor != null
				&& handleInterceptor.length > 0) {
			int size = handleInterceptor.length;
			for (int i = 0; i < size; i++) {
				if (!handleInterceptor[i].preHandle(request, response)) {
					response.sendError(403, "Access is not enough");
					return;
				}
			}
			super.handleRequest(request, response);
			for (int j = size - 1; j >= 0; j--) {
				handleInterceptor[j].postHandle(request, response);
			}
		} else {
			super.handleRequest(request, response);
		}
	}

	public HandleInterceptorHessian[] getHandleInterceptor() {
		return handleInterceptor;
	}

	public void setHandleInterceptor(
			HandleInterceptorHessian[] handleInterceptor) {

		this.handleInterceptor = handleInterceptor;
		this.interceptorMark = true;
	}
}