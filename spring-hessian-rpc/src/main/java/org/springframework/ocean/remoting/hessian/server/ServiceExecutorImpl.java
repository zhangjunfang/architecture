/**
 * 
 */
package org.springframework.ocean.remoting.hessian.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.ocean.remoting.hessian.common.Request;
import org.springframework.ocean.remoting.hessian.common.Response;
import org.springframework.ocean.remoting.hessian.common.ServiceExecutor;
import org.springframework.ocean.remoting.hessian.common.Utils;
import org.springframework.ocean.remoting.hessian.service.stereotype.ServiceExporter;

/**
 * 
 * @author： ocean
 * 创建时间：2015年12月21日
 * mail：zhangjunfang0505@163.com
 * 描述：
 */
public class ServiceExecutorImpl implements ServiceExecutor, ApplicationContextAware {
    Map<String, Object>        services;

    private ApplicationContext applicationContext;

    /** 
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.services = applicationContext.getBeansWithAnnotation(ServiceExporter.class);
        this.applicationContext = applicationContext;
    }

    /** 
     * @see org.springframework.ocean.remoting.hessian.common.ServiceExecutor#execute(org.springframework.ocean.remoting.hessian.common.Request)
     */
    @Override
    public Response execute(Request request) {
        String serviceName = request.getServiceName();
        Object service = applicationContext.getBean(serviceName);
        try {
            Class<?>[] parameterTypes = Utils.createParameterTypes(request.getMethodArgSigs());
            Method method = service.getClass().getMethod(request.getMethodName(), parameterTypes);
            Object data = method.invoke(service, request.getMethodArgs());
            Response response = new Response();
            response.setRequest(request);
            response.setResponseData(data);
            response.setLocalAddr(Utils.getNetworkAddress());
            response.setTime(System.currentTimeMillis());
            return response;
        } catch (SecurityException e) {
            e.printStackTrace();
            //logger.error("", e);
        } catch (NoSuchMethodException e) {
            //logger.error("", e);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            //logger.error("", e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // logger.error("", e);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            //logger.error("", e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            //logger.error("", e);
            e.printStackTrace();
        }

        return null;
    }

}
