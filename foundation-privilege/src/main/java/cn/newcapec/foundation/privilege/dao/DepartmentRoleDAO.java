package cn.newcapec.foundation.privilege.dao;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import cn.newcapec.foundation.privilege.dao.base.BaseDepartmentRoleDAO;

/**
 * 部门角色关联接口类
 * @author andy.li
 *
 */
@Repository("departmentRoleDAO")
public class DepartmentRoleDAO extends BaseDepartmentRoleDAO {
	
	/**
	 * 删去所有的角色下的部门信息
	 * @param roleid
	 */
	public void deleteDepartmentRoleByRoleId(String roleid){
		if(StringUtils.isNotBlank(roleid)){
			StringBuffer sb = new StringBuffer("delete from t_department_role where  role_id=?");
			getSession().createSQLQuery(sb.toString()).setParameter(0, roleid).executeUpdate();
		}
	}

}
