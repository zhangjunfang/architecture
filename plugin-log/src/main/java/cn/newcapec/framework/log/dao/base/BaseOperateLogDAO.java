package cn.newcapec.framework.log.dao.base;


import cn.newcapec.framework.core.dao.hibernate.HibernateEntityDao;

@SuppressWarnings("all")
public class BaseOperateLogDAO extends  HibernateEntityDao {
	
	protected Class getReferenceClass() {
		return cn.newcapec.framework.log.model.OperateLog.class;
	}
	

	
}
