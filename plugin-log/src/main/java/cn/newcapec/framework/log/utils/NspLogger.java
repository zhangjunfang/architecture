package cn.newcapec.framework.log.utils;

import cn.newcapec.framework.log.model.OperateLog;
import cn.newcapec.framework.log.model.Operator;

/**
 * @author 
 */
public interface NspLogger {
	
	public boolean isInfoEnabled();
	
	public boolean isWarnEnabled();
	
	public boolean isErrorEnabled();

	public void info(OperateLog systemLog);
	
	public void info(Operator operator, String title, String desc);
	
	public void info(String title, String desc);
	
	public void warn(OperateLog systemLog);
	
	public void warn(Operator operator, String title, String desc);
	
	public void warn(String title, String desc);
	
	public void error(OperateLog systemLog);
	
	public void error(Operator operator, String title, String desc);
	
	public void error(String title, String desc);

}
