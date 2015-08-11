package cn.newcapec.function.platform.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import cn.newcapec.framework.core.dao.hibernate.HibernateEntityDao;
import cn.newcapec.function.platform.model.LegalPerson;
import cn.newcapec.function.platform.model.User;

/**
 *
 * @Description: TODO
 * @author gaochongfei
 * @date 2014-4-4 下午04:02:53
 * @version V1.0
 */
@SuppressWarnings("all")
@Repository("legalPersonDAO")
public class LegalPersonDAO extends HibernateEntityDao {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * cn.newcapec.framework.core.dao.hibernate.HibernateEntityDao#getReferenceClass
	 * ()
	 */
	@Override
	protected Class getReferenceClass() {
		return cn.newcapec.function.platform.model.LegalPerson.class;
	}

	public cn.newcapec.function.platform.model.LegalPerson load(
			java.io.Serializable key) {
		return (cn.newcapec.function.platform.model.LegalPerson) load(
				getReferenceClass(), key);
	}

	public cn.newcapec.function.platform.model.LegalPerson get(
			java.io.Serializable key) {
		return (cn.newcapec.function.platform.model.LegalPerson) get(
				getReferenceClass(), key);
	}

	public java.util.List findAll() {
		return find("from " + getReferenceClass().getName());
	}

	public void save(cn.newcapec.function.platform.model.LegalPerson legalPerson) {
		super.save(legalPerson);
	}

	public void saveOrUpdate(
			cn.newcapec.function.platform.model.LegalPerson legalPerson) {
		saveOrUpdate((Object) legalPerson);
	}

	public void update(
			cn.newcapec.function.platform.model.LegalPerson legalPerson) {
		update((Object) legalPerson);
	}

	public void delete(java.io.Serializable id) {
		delete((Object) load(id));
	}

	/**
	 * @Title: queryByCustomerunitcode --
	 * @Description: 查询客户根据客户代码
	 * @param @param code
	 * @param @return 设定文件
	 * @return LegalPerson 返回类型
	 * @throws
	 */
	public LegalPerson queryByCustomerunitcode(String code) {
		String hql = "select u from LegalPerson u where  u.customerunitcode =?";
		LegalPerson legalPerson = (LegalPerson) this.findForObject(
				hql.toString(), new Object[] { code });
		return legalPerson;
	}

}
