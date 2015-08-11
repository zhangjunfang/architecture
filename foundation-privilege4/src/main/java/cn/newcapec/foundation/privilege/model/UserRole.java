package cn.newcapec.foundation.privilege.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.newcapec.framework.core.model.AppBaseModel;

/**
 * 用户角色关联表
 * @author andy.li
 *
 */
@Entity
@Table(name = "t_user_role")
public class UserRole  extends AppBaseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1399875599793424185L;
	/*用户编号*/
	private String userId;
	/*角色编号*/
	private String roleId;
	/*操作人*/
	private String operationId;
	/*操作时间*/
	private Date createDatetime;
	/*更新人*/
	private String updateUserId;
	/*更新时间*/
	private Date updateTime;
	

	@Column(name = "user_id",length=32)
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "role_id",length=32)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "create_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}
	
	@Column(name = "operation_id", length = 32)
	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}


	@Column(name = "update_user_Id", length = 32)
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	
}