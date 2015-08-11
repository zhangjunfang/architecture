package cn.newcapec.foundation.privilege.biz;

import cn.newcapec.foundation.privilege.model.DepartmentRole;
import cn.newcapec.framework.core.biz.BaseService;

/**
 * 部门权限业务关联接口类
 * @author andy.li
 *
 */
public interface DepartmentRoleService  extends BaseService<DepartmentRole> {
	
	/**
	 * 删去所有的角色下的部门信息
	 * @param roleid
	 */
	public void deleteDepartmentRoleByRoleId(String roleid);

}
