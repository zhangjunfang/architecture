package cn.newcapec.function.platform.dao.impl;

import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.newcapec.framework.core.dao.hibernate.HibernateEntityDao;

/**
 * 
 * @Description: TODO
 * @author gaochongfei
 * @date 2014-4-4 下午04:03:14
 * @version V1.0
 */

@SuppressWarnings("all")
public abstract class BaseUserDAO extends HibernateEntityDao {

	@Override
	protected Class getReferenceClass() {
		return cn.newcapec.function.platform.model.User.class;
	}

	public BaseUserDAO() {
	}

	public cn.newcapec.function.platform.model.User cast(Object object) {
		return (cn.newcapec.function.platform.model.User) object;
	}

	public cn.newcapec.function.platform.model.User load(
			java.io.Serializable key) {
		return (cn.newcapec.function.platform.model.User) load(
				getReferenceClass(), key);
	}

	public cn.newcapec.function.platform.model.User get(java.io.Serializable key) {
		return (cn.newcapec.function.platform.model.User) get(
				getReferenceClass(), key);
	}

	public java.util.List findAll() {
		return find("from " + getReferenceClass().getName());
	}

	public void save(cn.newcapec.function.platform.model.User user) {
		super.save(user);
	}

	public void saveOrUpdate(cn.newcapec.function.platform.model.User user) {
		saveOrUpdate((Object) user);
	}

	public void update(cn.newcapec.function.platform.model.User user) {
		update((Object) user);
	}

	public void delete(java.io.Serializable id) {
		delete((Object) load(id));
	}

	public void delete(cn.newcapec.function.platform.model.User user) {
		delete((Object) user);
	}
}
