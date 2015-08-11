package cn.newcapec.function.platform.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import cn.newcapec.framework.core.dao.hibernate.HibernateEntityDao;

/**
 * 
 * @Description: TODO
 * @author gaochongfei
 * @date 2014-4-4 下午04:03:23
 * @version V1.0
 */

@SuppressWarnings("all")
public abstract class BaseRoleDAO extends HibernateEntityDao {

	@Override
	protected Class getReferenceClass() {
		return cn.newcapec.function.platform.model.Role.class;
	}

	public BaseRoleDAO() {

	}

	public cn.newcapec.function.platform.model.Role cast(Object object) {
		return (cn.newcapec.function.platform.model.Role) object;
	}

	public cn.newcapec.function.platform.model.Role load(
			java.io.Serializable key) {
		return (cn.newcapec.function.platform.model.Role) load(
				getReferenceClass(), key);
	}

	public cn.newcapec.function.platform.model.Role get(java.io.Serializable key) {
		return (cn.newcapec.function.platform.model.Role) get(
				getReferenceClass(), key);
	}

	public java.util.List findAll() {
		return find("from " + getReferenceClass().getName());
	}

	public void save(cn.newcapec.function.platform.model.Role role) {
		super.save(role);
	}

	public void saveOrUpdate(cn.newcapec.function.platform.model.Role role) {
		saveOrUpdate((Object) role);
	}

	public void update(cn.newcapec.function.platform.model.Role role) {
		update((Object) role);
	}

	public void delete(java.io.Serializable id) {
		delete((Object) load(id));
	}

	public void delete(cn.newcapec.function.platform.model.Role role) {
		delete((Object) role);
	}

}
