package cn.newcapec.function.platform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.newcapec.framework.core.model.CommonModel;

/**
 * 角色实体类
 * 
 * @author chongfeigao
 * 
 */
@Entity
@Table(name = "base_role")
public class Role extends CommonModel {

	private static final long serialVersionUID = 1L;
	/* 名称 */
	private String roleName;
	/* 客户代码 */
	private String customerunitcode;
	// 状态 0：禁用 1：启用
	private String rolestate;

	@Column(name = "rolestate")
	public String getRolestate() {
		return rolestate;
	}

	public void setRolestate(String rolestate) {
		this.rolestate = rolestate;
	}

	@Column(name = "rolename", length = 32)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "customerunitcode", length = 6)
	public String getCustomerunitcode() {
		return customerunitcode;
	}

	public void setCustomerunitcode(String customerunitcode) {
		this.customerunitcode = customerunitcode;
	}
}