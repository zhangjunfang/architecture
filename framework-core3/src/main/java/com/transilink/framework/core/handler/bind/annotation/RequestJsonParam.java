package com.transilink.framework.core.handler.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@Target({ java.lang.annotation.ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestJsonParam {
	public abstract String value();

	public abstract boolean required();
}