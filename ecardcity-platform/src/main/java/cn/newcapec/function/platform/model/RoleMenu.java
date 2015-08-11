package cn.newcapec.function.platform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.newcapec.framework.core.model.CommonModel;

/**
 *
 * @Description: 角色权限
 * @author gaochongfei
 * @date 2014-4-11 下午02:15:03
 * @version V1.0
 */
@Entity
@Table(name = "base_role_menu")
public class RoleMenu extends CommonModel {

	private static final long serialVersionUID = 1L;
	/* 角色编号 */
	@Column(name = "roleid", length = 32)
	private String roleid;
	/* 菜单编号 */
	@Column(name = "menuid", length = 32)
	private String menuid;
	// 应用包编号
	@Column(name = "appid", length = 6)
	private String appid;
	/* 客户代码 */
	@Column(name = "rolestate", length = 6)
	private String customerunitcode;

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getCustomerunitcode() {
		return customerunitcode;
	}

	public void setCustomerunitcode(String customerunitcode) {
		this.customerunitcode = customerunitcode;
	}
}