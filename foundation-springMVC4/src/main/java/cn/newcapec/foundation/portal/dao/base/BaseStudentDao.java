/**
 * 
 */
package cn.newcapec.foundation.portal.dao.base;

import cn.newcapec.framework.core.dao.hibernate.HibernateEntityDao;


/**
 * 学生操作基础类
 * @author andy
 *
 */
@SuppressWarnings("all")
public abstract class BaseStudentDao   extends HibernateEntityDao {


	@Override
	protected Class getReferenceClass() {
		return cn.newcapec.foundation.portal.model.Student.class;
	}



	public cn.newcapec.foundation.portal.model.Student cast(Object object) {
		return (cn.newcapec.foundation.portal.model.Student) object;
	}

	public cn.newcapec.foundation.portal.model.Student load(
			java.io.Serializable key) {
		return (cn.newcapec.foundation.portal.model.Student) load(
				getReferenceClass(), key);
	}

	public cn.newcapec.foundation.portal.model.Student get(
			java.io.Serializable key) {
		return (cn.newcapec.foundation.portal.model.Student) get(
				getReferenceClass(), key);
	}

	public java.util.List findAll() {
		return find("from " + getReferenceClass().getName());
	}

	public void save(cn.newcapec.foundation.portal.model.Student student) {
		super.save(student);
	}

	public void saveOrUpdate(cn.newcapec.foundation.portal.model.Student student) {
		saveOrUpdate((Object) student);
	}

	public void update(cn.newcapec.foundation.portal.model.Student student) {
		update((Object) student);
	}

	public void delete(java.io.Serializable id) {
		delete((Object) load(id));
	}

	public void delete(cn.newcapec.foundation.portal.model.Student student) {
		delete((Object) student);
	}

}
