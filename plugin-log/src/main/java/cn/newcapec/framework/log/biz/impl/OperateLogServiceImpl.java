package cn.newcapec.framework.log.biz.impl;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.framework.log.biz.OperateLogService;
import cn.newcapec.framework.log.dao.OperateLogDao;
import cn.newcapec.framework.log.model.OperateLog;
import cn.newcapec.framework.log.utils.NspLogger;
import cn.newcapec.framework.log.utils.NspLoggerFactory;
import cn.newcapec.framework.log.utils.OperateLogUtils;



@Service("operateLogService")
@Transactional
public class OperateLogServiceImpl  implements OperateLogService, DisposableBean {
	
	private Collection<OperateLog> logs =new  CopyOnWriteArrayList<OperateLog>();
	
	private boolean actualTimeCommit = false;
	
	protected NspLogger nspLogger = NspLoggerFactory.getNspLogger(this.getClass());
	
	@Autowired
	OperateLogDao operateLogDao;
	
	
	@Override
	public void addLog(OperateLog log) {
		if(OperateLogUtils.isRecordLog()) {
			if (this.actualTimeCommit) {
				operateLogDao.save(log);
			} else {
				logs.add(log);
			}
		}
	}
	
	@Override
	public void pushLog(OperateLog log) {
		if(OperateLogUtils.isRecordLog()) {
			logs.add(log);
		}
	}

	@Override
	public void addBatchInsertLog() {
		if(null!=logs && logs.size()>0){
			operateLogDao.saveOrUpdateAll(logs);
			logs.clear();
		}
	}

	@Override
	public void destroy() throws Exception {
		OperateLog systemLog = new OperateLog();
		systemLog.setTitle("绯荤粺寮�鍏抽棴");
		this.nspLogger.info(systemLog);
		this.addBatchInsertLog();
	}
	
	@Override
	public OperateLog get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeUnused(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveOrUpdate(OperateLog arg0) {
		// TODO Auto-generated method stub
		
	}

}
