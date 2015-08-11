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
 * 用户实体类
 * 
 * @author andy.li
 * 
 */
@Entity
@Table(name = "t_user")
public class User  extends AppBaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -693841966186554489L;
	/* 部门编号 */
	private String departId;
	/*用户登录账号*/
	private String accountName;
	/* 用户真实姓名 */
	private String userName;
	/* 用户编号 */
	private String userCode;
	/* 邮件 */
	private String email;
	/* 手机 */
	private String mobile;
	/* 登陆密码 */
	private String password;
	/* 状态 */
	private String status;
	/*性别*/
	private String sex;
	/* 操作人 */
	private String operationId;
	/* 操作时间 */
	private Date openDate;
	/* 更新人 */
	private String updateUserId;
	/* 更新时间 */
	private Date invaildDate;
	/*证件类型: 0:身份证，1：学生证，2：其他*/
	private String certificateType;
	/*证件编号*/
	private String certificateCode;
	/*用户类型*/
	private String userType;
	/*用户民族*/
	private String nation;
	


	@Column(name = "departid",length=32)
	public String getDepartId() {
		return this.departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}
	@Column(name = "nation",length=32)
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Column(name = "username", length = 60)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "usercode", length = 32)
	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Column(name = "email", length = 32)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "mobile", length = 16)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "password", length = 64)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "status", length = 8)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	@Column(name = "accountName", length = 60)
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name = "sex", length = 8)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "open_date")
	public Date getOpenDate() {
		return openDate;
	}
	
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "invaild_date")
	public Date getInvaildDate() {
		return invaildDate;
	}
	
	public void setInvaildDate(Date invaildDate) {
		this.invaildDate = invaildDate;
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

	@Column(name = "certificate_type", length = 32)
	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	@Column(name = "certificate_code", length = 32)
	public String getCertificateCode() {
		return certificateCode;
	}

	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}
	
	@Column(name = "user_type", length = 32)
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
}