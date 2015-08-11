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
 * 角色实体类
 * @author andy.li
 *
 */
@Entity
@Table(name = "t_role")
public class Role  extends AppBaseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*名称*/
	private String roleName;
	/*状态*/
	private String status;
	/*描述*/
	private String memo;
	/*操作人*/
	private String operationId;
	/*操作时间*/
	private Date createDatetime = new Date();
	/*更新人*/
	private String updateUserId;
	/*更新时间*/
	private Date updateTime;

	@Column(name = "name", length = 32)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "status", length = 8)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "memo", length = 500)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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