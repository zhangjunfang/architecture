package cn.newcapec.foundation.portal.util;

import org.restlet.Finder;
import org.restlet.Restlet;
import org.restlet.Router;
import org.restlet.ext.spring.SpringBeanFinder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * rest路由器
 * @author andy.li
 *
 */
public class RestRouter  extends Router  implements  BeanFactoryPostProcessor {
	

    protected Finder createFinder(BeanFactory beanFactory, String beanName) {
        return new SpringBeanFinder(beanFactory, beanName);
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory)
            throws BeansException {
    	attach("/privilegeProxyService", (Restlet)factory.getBean("privilegeRoute"));
    	attach("/portalProxyService", (Restlet)factory.getBean("portalRoute"));
//    	attach("/test",createFinder(factory, "testResource"));
    }

   

	
}
