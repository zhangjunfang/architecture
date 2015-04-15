/*
 * 该类功能及其特点的描述（例如：该类是用来……）
 *
 * @see（与该类相关联的类）：
 */
/**
 * 
 */
package com.transilink.framework.plugins.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/****
 * 
 * @author Sntey 2013-12-10
 * 缓存自动生成策略<br/>
 * 当某个方法上打上这个注解时，代表自动缓存该方法返回的值。<br/>
 * 缓存KEY值默认为【head+当前方法所在类的名+foot】<br/>
 * 缓存中存在的内容应该为一个MAP，MAP的KEY应该为【方法名+参数类型】<br/>
 * MAP的内容为缓存的方法返回的值<br/>
 * 如果"defaulttype"设为false则key值生成策略变为head+foot<br/>
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface UseCache {
	
	String head() default "";
	
	String foot() default "";
	
	boolean defaulttype() default true;
	
	String time() default "120";
}

