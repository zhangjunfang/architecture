package com.transilink.framework.log.utils;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.transilink.framework.core.exception.BaseException;
import com.transilink.framework.core.logs.LogEnabled;
import com.transilink.framework.core.utils.springUtils.SpringConext;
import com.transilink.framework.log.biz.OperateLogService;


/**
 * 日志job操作
 * @author andy.li
 *
 */
public class BatchInsertLogTimerJob implements Runnable,LogEnabled{
	
//	@Autowired
//	OperateLogService operateLogService;
	
//	private static OperateLogService operateLogService = (OperateLogService)SpringConext.getApplicationContext().getBean("operateLogService");
	@Override
	public void run() {
		try {
			OperateLogService operateLogService = (OperateLogService)SpringConext.getApplicationContext().getBean("operateLogService");
			operateLogService.addBatchInsertLog();
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			  if (e instanceof BaseException) {
	                throw (BaseException) e;
	            } else {
	                throw new BaseException("系统出错！", e);
	            }
		}
	}
}
