package cn.newcapec.foundation.privilege.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.newcapec.framework.core.model.AppBaseModel;

/**
 * 部门权限关联表
 * 
 * @author andy.li
 * 
 */
@Entity
@Table(name = "t_department_role")
public class DepartmentRole   extends AppBaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2006033056324440859L;
	/* 角色编号 */
	public String roleid;
	/* 部门编号 */
	public String departmentid;

	@Column(name = "role_id", length = 32)
	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	@Column(name = "department_id", length = 32)
	public String getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}
}
