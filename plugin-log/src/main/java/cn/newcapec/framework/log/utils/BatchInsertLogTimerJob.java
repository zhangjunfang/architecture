package cn.newcapec.framework.log.utils;

import org.apache.commons.lang.exception.ExceptionUtils;

import cn.newcapec.framework.core.exception.BaseException;
import cn.newcapec.framework.core.logs.LogEnabled;
import cn.newcapec.framework.core.utils.springUtils.SpringConext;
import cn.newcapec.framework.log.biz.OperateLogService;


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
