package cn.newcapec.function.platform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.newcapec.framework.core.model.CommonModel;

/**
 * 用户角色关联表
 * 
 * @author chongfeigao
 * 
 */
@Entity
@Table(name = "base_loginlimit_type")
public class LoginlimitType extends CommonModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1399875599793424185L;
	/* 登录限制类型ID */
	@Column(name = "typeid")
	private Integer typeid;
	/* 登录限制描述 */
	@Column(name = "typename", length = 30)
	private String typename;

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

}