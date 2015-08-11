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
 * 资源类型表
 * @author andy.li
 *
 */
@Entity
@Table(name = "t_resource_type")
public class Resourcetype  extends AppBaseModel implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8386326511443854425L;
	/*名称*/
	private String name;
	/*状态*/
	private String status;
	/*操作人*/
	private String operationId;
	/*操作时间*/
	private Date createDatetime;
	/*更新人*/
	private String updateUserId;
	/*更新时间*/
	private Date updateTime;
	


	@Column(name = "name", length = 32)
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

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_datetime")
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

	@Column(name = "update_user_id", length = 32)
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


}