package com.transilink.framework.log.dao;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.transilink.framework.log.dao.base.BaseOperateLogDAO;
import com.transilink.framework.log.model.OperateLog;

/**
 * 系统日志接口实现类
 * @author andy.li
 *
 */
@SuppressWarnings("all")
@Repository("operateLogDao")
public class OperateLogDao extends BaseOperateLogDAO {



	public void addLog(Collection<OperateLog> logs) {
		if (logs == null || logs.isEmpty())
		 this.saveOrUpdateAll(logs);

	}

	public void addLog(OperateLog log) {
		if (log == null)
			this.save(log);
	}
}
