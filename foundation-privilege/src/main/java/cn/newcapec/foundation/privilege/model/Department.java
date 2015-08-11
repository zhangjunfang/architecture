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
 * 部门业务实体类
 * 
 * @author andy.li
 * 
 */
@Entity
@Table(name = "t_department")
public class Department  extends AppBaseModel implements Serializable {

	private static final long serialVersionUID = 4345338315141135462L;
	/* 编码 */
	private String departCode;
	/* 名称 */
	private String name;
	/*部门简称*/
	private String simpName;
	/* 父节点 */
	private String parentId;
	/* 状态 */
	private String status;
	/* 描述 */
	private String memo;
	/* 操作人 */
	private String operationId;
	/* 操作时间 */
	private Date createDatetime;
	/* 更新人 */
	private String updateUserId;
	/* 更新时间 */
	private Date updateTime;


	public Department(){
		
	}
	
	public  Department(String parentId){
		this.parentId=parentId;
	}
	
	
	@Column(name = "simpName", length = 32)
	public String getSimpName() {
		return simpName;
	}

	public void setSimpName(String simpName) {
		this.simpName = simpName;
	}

	@Column(name = "parent_id", length = 32)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "name", length = 62)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "status", length = 8)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "depart_code")
	public String getDepartCode() {
		return departCode;
	}

	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}

	@Column(name = "memo", length = 500)
	public String getMemo() {
		return memo;
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