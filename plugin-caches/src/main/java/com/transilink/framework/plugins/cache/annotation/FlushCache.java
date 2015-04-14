/*
 * 该类功能及其特点的描述（例如：该类是用来……）
 *
 * @see（与该类相关联的类）：(XXX.java)
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
 * 缓存自动清除策略<br/>
 * 当某个方法上打上这个注解时，代表自动清除与该方法所在类相关的所有缓存。<br/>
 * 缓存KEY值默认为【head+当前方法所在类的名+foot】<br/>
 * 缓存中存在的内容应该为一个MAP，MAP的KEY应该为【方法名+参数类型】<br/>
 * MAP的内容为缓存的方法返回的值<br/>
 * orderfirst代表是刚进入方法时清除缓存。<br/>
 * 如果orderfist=false 则代表方法执行完后清除缓存<br/>
 * 如果"defaulttype"设为false则key值生成策略变为head+foot<br/>
 *
 * *joins 类似于外键关联机制，代码该缓存在清除时除了清空自身外，还会清空joins关联的缓存,joins可以为多个用逗号分开
 *注：刷新会优先刷新本身缓存，再刷新关联缓存。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface FlushCache {
	
	
	String head() default "";
	
	String foot() default "";
	
	boolean defaulttype() default true;
	
	boolean orderfirst() default false;
	
	String joins() default "";
}

