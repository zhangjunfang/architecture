package com.transilink.framework.log.utils;

import com.transilink.framework.core.utils.springUtils.SpringConext;
import com.transilink.framework.log.biz.OperateLogService;

/**
 */ 
public class OperateLogUtils   {
	
	
	private static OperateLogService operateLogService = (OperateLogService)SpringConext.getApplicationContext().getBean("operateLogService");
//	@Autowired
//	static OperateLogService operateLogService;
	
	static OperateLogService getOperateLogService() {
		return operateLogService;
	}
	
	
	/**
	 * @return
	 * 是否实时提交日志，默认false，日志量大时，使用实时提交会很影响性能。
	 */
	public static Boolean isActualTimeCommit(){
		return getCommitPeriod() <= 0;
	}
	
	/**
	 * @return
	 * 是否记录日志
	 */
	public static Boolean isRecordLog(){
		return getCommitDelay() > 0;
	}
	
	/**
	 * @return
	 * 日志非实时提交时，首次提交的延时时间，单位毫秒。
	 */
	public static long getCommitDelay(){
		return 30;
	}
	
	/**
	 * @return
	 * 日志非实时提交时，等待提交的间隔时间，单位毫秒。
	 */
	public static long getCommitPeriod(){
		return 30;
	}
}
