package cn.newcapec.function.platform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.newcapec.framework.core.model.CommonModel;

/**
 * 
 * @Description: 角色组实体类
 * @author gaochongfei
 * @date 2014-4-4 下午04:05:18
 * @version V1.0
 */
@Entity
@Table(name = "base_role_group")
public class RoleGroup extends CommonModel {

	private static final long serialVersionUID = 1L;
	/* 名称 */
	@Column(name = "groupname", length = 32)
	private String groupname;
	/* 客户代码 */
	@Column(name = "customerunitcode", length = 6)
	private String customerunitcode;
	// 状态 0：禁用 1：启用
	@Column(name = "groupstate")
	private String groupstate;
	// 角色组关联的角色ID，用逗号分隔
	@Column(name = "roleids", length = 500)
	private String roleids;

	public String getRoleids() {
		return roleids;
	}

	public void setRoleids(String roleids) {
		this.roleids = roleids;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getGroupstate() {
		return groupstate;
	}

	public void setGroupstate(String groupstate) {
		this.groupstate = groupstate;
	}

	public String getCustomerunitcode() {
		return customerunitcode;
	}

	public void setCustomerunitcode(String customerunitcode) {
		this.customerunitcode = customerunitcode;
	}
}