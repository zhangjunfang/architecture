package com.transilink.framework.plugins.aspect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.transilink.framework.plugins.cache.Cache;
import com.transilink.framework.plugins.cache.annotation.UseCache;
import com.transilink.framework.plugins.cache.memoryCache.CacheController;
import com.transilink.framework.plugins.cache.utils.NumberUtils;

@Component
@Aspect
public class UseCacheAspect implements Cache{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6440409674984357444L;
	protected final static Log log = LogFactory.getLog(UseCacheAspect.class);
	
	
	@Pointcut("@annotation(com.transilink.framework.plugins.cache.annotation.UseCache)")
	private void doMethod(){
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Around("doMethod()")
	public Object doAround(ProceedingJoinPoint  pjp) throws Throwable{
		log.info("进入缓存区……");
		Method method = ((MethodSignature)pjp.getSignature()).getMethod();
		Class<?>[] methodParameterTypes = method.getParameterTypes();
		Class<?> target = pjp.getTarget().getClass();
		Method meth =  target.getMethod(method.getName(), methodParameterTypes);
		
		UseCache uc = meth.getAnnotation(UseCache.class);
		
		String cacheKey = getKey(target.getName(),uc);
		
		String cacheKeyExpand = this.getKeyExpand(method,pjp.getArgs());
		
		Object cachecontent = CacheController.getCache().get(cacheKey);
		
		if(null != cachecontent){
			Map<String, Object> map = (Map<String, Object>)cachecontent;
			
			Object value = map.get(cacheKeyExpand);
			
			if(value == null){
				
				Object retVal = pjp.proceed();  
				
				map.put(cacheKeyExpand, retVal);
				
				CacheController.getCache().put(cacheKey, map,NumberUtils.toLong(uc.time()));
				
				return retVal;
			}else{
				return value;
			}
		}else{
			Object retVal = pjp.proceed();  
			
			Map map = new HashMap();
			
			map.put(cacheKeyExpand, retVal);
			
			CacheController.getCache().put(cacheKey, map,NumberUtils.toLong(uc.time()));
			
			return retVal;
		}
		
	}
	
	public String getKey(String targetClassName,UseCache uc) throws SecurityException, NoSuchMethodException{
		StringBuffer key = new StringBuffer();
		if(uc.defaulttype()){
			key.append(uc.head());
			key.append(targetClassName);
			key.append(uc.foot());
		}
		return key.append(uc.head()).append(uc.foot()).toString();
	}
	
	private String getKeyExpand(Method method,Object[] args){
		
		String methodName = method.getName();
		
		StringBuffer key = new StringBuffer();
		
		key.append(methodName);
		for(Object c : args){
			key.append(c);
		}
		return key.toString();
	}
	
	public void doThrowing(JoinPoint jp, Throwable ex) {  
		log.info("throwing:" + jp.getTarget().getClass().getName()  
					+ "." + jp.getSignature().getName() + " error!");  

	}
}
