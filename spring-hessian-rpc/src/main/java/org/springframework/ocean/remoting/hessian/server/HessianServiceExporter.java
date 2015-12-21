/**
 * 
 */
package org.springframework.ocean.remoting.hessian.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.util.NestedServletException;

/**
 * 
 * @author： ocean
 * 创建时间：2015年12月21日
 * mail：zhangjunfang0505@163.com
 * 描述：
 */
public class HessianServiceExporter extends HessianExporter implements HttpRequestHandler {

    /**
     * Processes the incoming Hessian request and creates a Hessian response.
     */
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!"POST".equals(request.getMethod())) {
            throw new HttpRequestMethodNotSupportedException(request.getMethod(),
                    new String[] {"POST"}, "HessianServiceExporter only supports POST requests");
        }

        response.setContentType(CONTENT_TYPE_HESSIAN);
        try {
          invoke(request.getInputStream(), response.getOutputStream());
        }
        catch (Throwable ex) {
          throw new NestedServletException("Hessian skeleton invocation failed", ex);
        }
    }

}
