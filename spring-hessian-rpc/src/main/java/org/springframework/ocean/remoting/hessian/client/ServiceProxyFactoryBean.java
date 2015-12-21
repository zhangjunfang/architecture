/**
 * 
 */
package org.springframework.ocean.remoting.hessian.client;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * 
 * @author： ocean
 * 创建时间：2015年12月21日
 * mail：zhangjunfang0505@163.com
 * 描述：
 */
public class ServiceProxyFactoryBean extends ServiceInterceptor implements
                                                                     FactoryBean<Object> {

    private Object serviceProxy;

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        this.serviceProxy = new ProxyFactory(getServiceInterface(), this)
            .getProxy(getBeanClassLoader());
    }

    public Object getObject() {
        return this.serviceProxy;
    }

    public Class<?> getObjectType() {
        return getServiceInterface();
    }

    public boolean isSingleton() {
        return true;
    }

}
