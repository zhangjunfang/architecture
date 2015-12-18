package com.ocean.rpc.server;

import java.lang.reflect.Type;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ocean.rpc.common.RpcContext;
import com.ocean.rpc.common.RpcMethods;

/**
 * 
 * @author： ocean 创建时间：2015年12月18日 mail：zhangjunfang0505@163.com 描述：
 */
public class RpcHttpMethods extends RpcMethods {

	@Override
	protected int getCount(Type[] paramTypes) {
		int i = paramTypes.length;
		if ((i > 0) && (paramTypes[i - 1] instanceof Class<?>)) {
			Class<?> paramType = (Class<?>) paramTypes[i - 1];
			if (paramType.equals(RpcContext.class)
					|| paramType.equals(HttpContext.class)
					|| paramType.equals(HttpServletRequest.class)
					|| paramType.equals(HttpServletResponse.class)
					|| paramType.equals(HttpSession.class)
					|| paramType.equals(ServletContext.class)
					|| paramType.equals(ServletConfig.class)) {
				--i;
			}
		}
		return i;
	}
}
