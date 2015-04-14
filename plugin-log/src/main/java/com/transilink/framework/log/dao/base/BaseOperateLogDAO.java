package com.transilink.framework.log.dao.base;


import com.transilink.framework.core.dao.hibernate.HibernateEntityDao;

@SuppressWarnings("all")
public class BaseOperateLogDAO extends  HibernateEntityDao {
	
	protected Class getReferenceClass() {
		return com.transilink.framework.log.model.OperateLog.class;
	}
	

	
}
