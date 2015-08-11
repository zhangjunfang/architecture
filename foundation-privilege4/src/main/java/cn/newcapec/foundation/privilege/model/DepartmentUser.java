package cn.newcapec.foundation.privilege.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.newcapec.framework.core.model.AppBaseModel;

/**
 * 用户部门关联表
 * 
 * @author andy.li
 * 
 */
@Entity
@Table(name = "t_department_user")
public class DepartmentUser extends AppBaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1018669362188410422L;
	/* 用户编号 */
	private String userId;
	/* 部门编号 */
	private String depatementId;

	@Column(name = "user_id", length = 32)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "depatement_id", length = 32)
	public String getDepatementId() {
		return depatementId;
	}

	public void setDepatementId(String depatementId) {
		this.depatementId = depatementId;
	}

}
