/**
 * 
 */
package org.springframework.ocean.remoting.hessian.client;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.ocean.remoting.hessian.common.ServiceExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

/**
 * 
 * @author： ocean
 * 创建时间：2015年12月21日
 * mail：zhangjunfang0505@163.com
 * 描述：
 */

@Component("exector")
public class ExectorFactoryBean extends ExectorInterceptor implements FactoryBean<Object>,
                                                          InitializingBean {
    private Object exectorProxy;

    /** 
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    @Override
    public Object getObject() throws Exception {
        return this.exectorProxy;
    }

    /** 
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    @Override
    public Class<?> getObjectType() {
        return ServiceExecutor.class;
    }

    /** 
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    /** 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.exectorProxy = new ProxyFactory(ServiceExecutor.class, this).getProxy(ClassUtils
            .getDefaultClassLoader());
    }
}
