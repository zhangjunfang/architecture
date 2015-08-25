/**
 * 
 */
package org.framework.core.spi.extension.factory;

import java.util.Set;

import org.framework.core.spi.extension.ClassPathSPI;


/**
 * 描述：存储以及查找对应的SPI实现类
 * 
 * @author ocean 2015年8月20日 email：zhangjunfang0505@163.com
 */
public class SPIFactoryBean {

	/**
	 * 通过类名找到对应的Class对象
	 * 
	 * */
	public static Class<?> getSPI(String ClassName){
		return ClassPathSPI.NAME_SPI_MAP.get(ClassName);
	}

	/**
	 * 通过SPI接口找到对应的实现类Class对象
	 * 
	 * */
	public static  Set<Class<?>>  getSPI(Class<?> clazz){
		return ClassPathSPI.JAR_MAP.get(clazz);
	}

}
