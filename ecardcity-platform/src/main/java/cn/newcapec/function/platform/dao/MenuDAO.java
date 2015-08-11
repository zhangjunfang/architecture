package cn.newcapec.function.platform.dao;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.newcapec.framework.core.dao.hibernate.HibernateEntityDao;
import cn.newcapec.framework.core.utils.pagesUtils.Page;

/**
 * 菜单实体
 * 
 * @Description: TODO
 * @author gaochongfei
 * @date 2014-4-4 下午04:01:35
 * @version V1.0
 */
@SuppressWarnings("all")
@Repository("menuDAO")
public class MenuDAO extends HibernateEntityDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.newcapec.framework.core.dao.hibernate.HibernateEntityDao#getReferenceClass
	 * ()
	 */
	@Override
	protected Class getReferenceClass() {
		return cn.newcapec.function.platform.model.Menu.class;
	}

	public cn.newcapec.function.platform.model.Menu load(
			java.io.Serializable key) {
		return (cn.newcapec.function.platform.model.Menu) load(
				getReferenceClass(), key);
	}

	public cn.newcapec.function.platform.model.Menu get(java.io.Serializable key) {
		return (cn.newcapec.function.platform.model.Menu) get(
				getReferenceClass(), key);
	}

	public java.util.List findAll() {
		return find("from " + getReferenceClass().getName());
	}

	public void save(cn.newcapec.function.platform.model.Menu menu) {
		super.save(menu);
	}

	public void saveOrUpdate(cn.newcapec.function.platform.model.Menu menu) {
		saveOrUpdate((Object) menu);
	}

	public void update(cn.newcapec.function.platform.model.Menu menu) {
		update((Object) menu);
	}

	public void delete(java.io.Serializable id) {
		delete((Object) load(id));
	}

	/**
	 * 获取的菜单信息
	 * 
	 * @param userid
	 * @return
	 */
	public Page queryMenus(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		/* 参数集合类 */

		StringBuffer sb = new StringBuffer(
				"select t.* from BASE_ROLE t where 1 = 1 and t.customerunitcode = ?");
		return this.sqlQueryForPage(sb.toString(),
				new Object[] { params.get("customerunitcode") }, orderby);
	}
}
