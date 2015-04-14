package com.transilink.framework.plugins.aspect;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.transilink.framework.plugins.cache.Cache;
import com.transilink.framework.plugins.cache.CacheEngine;
import com.transilink.framework.plugins.cache.annotation.FlushCache;
import com.transilink.framework.plugins.cache.memoryCache.CacheController;
import com.transilink.framework.plugins.cache.utils.StringUtils;

@Component
@Aspect 
public class FlushCacheAspect implements Cache {

	private static final long serialVersionUID = 3599645696069239251L;
	protected final static Log log = LogFactory.getLog(FlushCacheAspect.class);
	
	@Pointcut("@annotation(com.transilink.framework.plugins.cache.annotation.FlushCache)")
	private void doMethod(){
	}
	
	private CacheEngine getCache(){
		return CacheController.getCache();
	}
	
	@Around("doMethod()")
	public Object doAround(ProceedingJoinPoint  pjp) throws Throwable{
		log.info("开始刷新缓存数据……");
		long starttime = System.currentTimeMillis();
		MethodSignature methodSignature = (MethodSignature)pjp.getSignature();  
		Method method = methodSignature.getMethod();
		
		Class<?>[] methodParameterTypes = method.getParameterTypes();
		
		Class<?> target = pjp.getTarget().getClass();
		
		Method meth =  target.getMethod(method.getName(), methodParameterTypes);
		
		FlushCache uc = meth.getAnnotation(FlushCache.class);
		
		StringBuffer key = new StringBuffer();
		
		if(uc.defaulttype()){
			key.append(uc.head());
			key.append(target.getName());
			key.append(uc.foot());
		}else{
			key.append(uc.head()).append(uc.foot()).toString();
		}
		if(uc.orderfirst()){
			this.clearFromCache(key.toString(),uc.joins());
		}
		Object val = pjp.proceed();
		
		if(!uc.orderfirst()){
			this.clearFromCache(key.toString(),uc.joins());
		}
		log.info("缓存刷新结束,总用时:"+(System.currentTimeMillis()-starttime)+"毫秒.");
		return val;
	}
	private void clearFromCache(String key,String joins){
		if(getCache().get(key) != null){
			getCache().remove(key);
			if(StringUtils.hasText(joins)){
				if(joins.indexOf(StringUtils.Symbol.COMMA)!=-1){
					String[] joinsgroup = joins.split(StringUtils.Symbol.COMMA);
					for(String join : joinsgroup){
						if(StringUtils.hasText(join)){
							getCache().remove(join);
						}
					}
				}else{
					getCache().remove(joins);
				}
			}
		}
	}
}
