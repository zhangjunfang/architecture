package cn.newcapec.function.platform.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import cn.newcapec.framework.core.dao.hibernate.HibernateEntityDao;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.function.platform.model.Role;
import cn.newcapec.function.platform.model.RoleGroup;

/**
 * 
 * @Description: 角色组实体类
 * @author gaochongfei
 * @date 2014-4-4 下午04:00:50
 * @version V1.0
 */
@SuppressWarnings("all")
@Repository("roleGroupDAO")
public class RoleGroupDAO extends HibernateEntityDao {

	@Override
	protected Class getReferenceClass() {
		return RoleGroup.class;
	}

	/**
	 * 
	 * @Title: queryRoles --
	 * @Description: 查询所有角色
	 * @param @param params
	 * @param @param orderby
	 * @param @return 设定文件
	 * @return Page 返回类型
	 * @throws
	 */
	public List<Map<String, Object>> queryRoleGroups(
			Map<String, Object> params, LinkedHashMap<String, String> orderby) {
		/* 参数集合类 */
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(
				" select a.groupname, a.id ,a.groupstate,count(t.id) empnum");
		sb.append(" from  base_role_group a ");
		sb.append(" left join BASE_user t ");
		sb.append(" on a.id = t.roleid ");
		sb.append(" and a.customerunitcode=? ");
		sb.append(" and t.roletype = 1 ");
		sb.append(" group by a.groupstate,a.id,a.groupname");
		// orderby.put("t.create_datetime", "desc");
		return this.sqlQueryForList(sb.toString(),
				new Object[] { params.get("customerunitcode").toString() },
				null);
	}

	public Page queryUserRoles(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		/* 参数集合类 */
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(
				"select t1.name role_name ,t1.id role_id, "
						+ "from t_role t1,t_user_role t2  where 1=1 and t1.id=t2.role_id and t2.user_id = t3.id ");
		if (params.containsKey("name")
				&& StringUtils.isNotBlank(params.get("name").toString())) {
			sb.append(" and t.name=:name ");
			param.add(params.get("name"));
		}
		return this.sqlQueryForPage(sb.toString(), param.toArray(), orderby);
	}

	public Role findRoleByName(String name) {
		String hql = "select r from Role r where r.roleName=?";
		Role role = (Role) this.findForObject(hql.toString(),
				new Object[] { name });
		return role;
	}

	public void deleteRole(String[] ids) {
		if (null != ids && ids.length > 0) {
			StringBuffer sb = new StringBuffer(
					"delete from base_role where  1=1");
			StringBuffer roleids = new StringBuffer();
			for (int i = 0; i < ids.length; i++) {
				roleids.append(ids[i]).append(",");
			}
			roleids.deleteCharAt(roleids.length() - 1);
			sb.append(" and id in (" + roleids.toString() + ")");
			getSession().createSQLQuery(sb.toString()).executeUpdate();
		}
	}

	/**
	 * 获取角色信息根据客户code ---
	 * 
	 * @param userid
	 * @return
	 */
	public Page queryRoleGroupByCustomerCode(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		/* 参数集合类 */
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(
				"select t.* from BASE_ROLE_GROUP t where 1 = 1 and t.customerunitcode = ?");
		return this.sqlQueryForPage(sb.toString(),
				new Object[] { params.get("customerunitcode") }, orderby);
	}

	/**
	 * 查询角色组所有的角色 --
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryByRoleGroupidId(String rolegroupid) {
		String sql = "select distinct dr.role_id from BASE_ROLE dr where dr.roleid in  "
				+ "(select du.depatement_id from t_department_user du,t_user u ,t_department d  "
				+ " where du.depatement_id=d.id and du.user_id=u.id and du.user_id=?) ";
		return sqlQueryForList(sql, new Object[] { rolegroupid }, null);

	}

	/**
	 * 
	 * @Description: TODO
	 * @param @param key
	 * @param @return
	 * @return Role
	 * @throws
	 */
	public RoleGroup get(java.io.Serializable key) {
		return (RoleGroup) get(getReferenceClass(), key);
	}

	/**
	 * 
	 * @Description: 删除
	 * @param @param id
	 * @return void
	 * @throws
	 */
	public void delete(java.io.Serializable id) {
		String sql = "delete  from  base_role_group  where id=?";
		getSession().createSQLQuery(sql).setParameter(0, id).executeUpdate();
	}

}
