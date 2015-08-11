package cn.newcapec.function.platform.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.function.platform.dao.impl.BaseRoleDAO;
import cn.newcapec.function.platform.model.Role;

/**
 * 
 * @Description: 角色实体类
 * @author gaochongfei
 * @date 2014-4-4 下午04:00:50
 * @version V1.0
 */
@SuppressWarnings("all")
@Repository("roleDAO")
public class RoleDAO extends BaseRoleDAO {

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
	public List<Map<String, Object>> queryRoles(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		/* 参数集合类 */
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.id, a.rolename ,a.rolestate ,count(t.id) empnum");
		sb.append(" from  base_role a ");
		sb.append(" left join BASE_user t ");
		sb.append(" on a.id = t.roleid ");
		sb.append(" and a.customerunitcode=? ");
		sb.append("  and t.roletype = 0 ");
		sb.append(" group by a.id, a.rolestate,a.rolename ");
		// orderby.put("t.create_datetime", "desc");
		return this.sqlQueryForList(sb.toString(),
				new Object[] { params.get("customerunitcode").toString() },
				null);
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
	public Page queryRoleByCustomerCode(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		/* 参数集合类 */
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(
				"select t.* from BASE_ROLE t where 1 = 1 and t.customerunitcode = ?");
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
}
