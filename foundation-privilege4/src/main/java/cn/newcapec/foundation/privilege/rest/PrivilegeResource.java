package cn.newcapec.foundation.privilege.rest;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;

import cn.newcapec.framework.core.exception.BaseException;
import cn.newcapec.framework.core.rest.BaseResource;
import cn.newcapec.framework.core.rest.BaseResourceHandler;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.framework.plugins.cache.localcache.DefaultLocalCache;

/**
 * 用户授权模块集成资源类
 * 
 * @author andy.li
 * 
 */
@SuppressWarnings("all")
public abstract class PrivilegeResource extends BaseResource implements
        BaseResourceHandler {

	// page分页查询类
	protected Page<List<Map<String, Object>>> page = new Page<List<Map<String, Object>>>();

	/**
	 * 清除权限缓存
	 */
	protected void clearCache() {
        DefaultLocalCache.instance().clear();
	}
	

	/**
	 * 抛出异常
	 * 
	 * @param e
	 * @param msg
	 */
	protected void ThrowsException(Exception e, String msg) {
        log.error(ExceptionUtils.getFullStackTrace(e));
		if (e instanceof BaseException) {
			throw (BaseException) e;
		} else {
			throw new BaseException(msg, e);
		}
	}


}
