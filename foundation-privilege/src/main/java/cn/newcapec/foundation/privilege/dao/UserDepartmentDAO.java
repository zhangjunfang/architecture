package cn.newcapec.foundation.privilege.dao;

import cn.newcapec.foundation.privilege.dao.base.BaseUserDepartmentDAO;
import cn.newcapec.foundation.privilege.model.DepartmentUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户部门接口类
 * @author andy.li
 *
 */
@SuppressWarnings("all")
@Repository("userDepartmentDAO")
public class UserDepartmentDAO  extends BaseUserDepartmentDAO {
	
//
//	/**
//	 * 删去用户和部门的关联信息
//	 * @param userid
//	 */
//	public void deleteDepartmentByUserid(String[] userid);
//	/**
//	 * 查询当前部门下 是否有 人员
//	 * 如果存在返回 true 否则 返回 false
//	 * **/
//	public boolean findDepartmentsbyIdsExist(String[] idss);
//	
	
	public void deleteDepartmentByUserid(String[] ids) {

		if (null != ids && ids.length > 0) {
			StringBuffer sb = new StringBuffer(
					"delete from t_department_user where  1=1");
			StringBuffer userids = new StringBuffer();
			for (int i = 0; i < ids.length; i++) {
				userids.append(ids[i]).append(",");

			}
			userids.deleteCharAt(userids.length() - 1);
			sb.append(" and user_id in (" + userids.toString() + ")");
			getSession().createSQLQuery(sb.toString()).executeUpdate();
		}
	}
	
	public boolean findDepartmentsbyIdsExist(String[] idss) {

		for (int i = 0; i < idss.length; i++) {
			List<DepartmentUser> departmentUsers = (List<DepartmentUser>) getSession()
					.createQuery(
							"  from   DepartmentUser  where  depatementId=:id ")
					.setString("id", idss[i]).list();
			if (null == departmentUsers || departmentUsers.size() != 0) {
				return true;
			}
		}
		return false;
	}
}
