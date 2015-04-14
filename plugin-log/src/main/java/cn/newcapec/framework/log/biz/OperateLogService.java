package cn.newcapec.framework.log.biz;

import cn.newcapec.framework.core.biz.BaseService;
import cn.newcapec.framework.log.model.OperateLog;

/**
 *
 */ 
public interface OperateLogService extends BaseService<OperateLog> {
	
	/**
	  * @param log
	  * 添加日志
	  */
	public void addLog(OperateLog log);
	
	/**
	 * @param log
	 * 将日志对象推到缓存中，等待批量添加日志
	 */
	public void pushLog(OperateLog log);
	
	/**
	  * @return
	  * 批量提交到数据库
	  */
	public void addBatchInsertLog();

}
