package cn.newcapec.framework.log.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.newcapec.framework.log.model.OperateLog;
import cn.newcapec.framework.log.model.Operator;

/**
 * @author 
 */
public class NspLoggerImpl implements NspLogger {

	private static final long serialVersionUID = -6560244151660620173L;
	private transient Logger logger = null;
	private static final String MSG_FORMATE = "登录人[{}]标题[{}] - {}";

	/**
	 * Package access allows only {@link SimpleLoggerFactory} to instantiate
	 * SimpleLogger instances.
	 */
	NspLoggerImpl(Class<?> clazz) {
		this.logger = LoggerFactory.getLogger(clazz);
	}

	@Override
	public void error(OperateLog log) {
		if(this.logger.isErrorEnabled()){
			this.logger.error(MSG_FORMATE, new Object[]{log.getUsername(), log.getTitle(), log.getDescription()});
			OperateLogUtils.getOperateLogService().pushLog(log);
		}
	}

	@Override
	public void error(Operator operator, String title, String desc) {
		this.error(this.createSystemLog(operator, title, desc));
	}

	@Override
	public void error(String title, String desc) {
		this.error(this.createSystemLog(title, desc));
	}
	
	@Override
	public void warn(OperateLog log) {
		if(this.isWarnEnabled()){
			this.logger.warn(MSG_FORMATE, new Object[]{log.getUsername(), log.getTitle(), log.getDescription()});
			OperateLogUtils.getOperateLogService().pushLog(log);
		}
	}
	
	@Override
	public void warn(Operator operator, String title, String desc) {
		this.warn(this.createSystemLog(operator, title, desc));
	}
	
	@Override
	public void warn(String title, String desc) {
		this.warn(this.createSystemLog(title, desc));
	}
	
	@Override
	public void info(OperateLog log) {
		if(this.logger.isInfoEnabled()){
			this.logger.info(MSG_FORMATE, new Object[]{log.getUsername(), log.getTitle(), log.getDescription()});
			OperateLogUtils.getOperateLogService().pushLog(log);
		}
	}
	
	@Override
	public void info(Operator operator, String title, String desc) {
		this.info(this.createSystemLog(operator, title, desc));
	}
	
	@Override
	public void info(String title, String desc) {
		this.info(this.createSystemLog(title, desc));
	}

	@Override
	public boolean isErrorEnabled() {
		return this.logger.isErrorEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return this.logger.isErrorEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return this.logger.isWarnEnabled();
	}

	private OperateLog createSystemLog(Operator operator, String title, String desc){
		OperateLog systemLog = new OperateLog();
		systemLog.setId(operator.getOperatorId().toString());
		systemLog.setUsername(operator.getUsername());
		systemLog.setTitle(title);
		systemLog.setDescription(desc);
		return systemLog;
	}
	
	private OperateLog createSystemLog(String title, String desc){
		OperateLog systemLog = new OperateLog();
		systemLog.setUsername("system");
		systemLog.setTitle(title);
		systemLog.setDescription(desc);
		return systemLog;
	}
}
