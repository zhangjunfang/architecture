package cn.newcapec.function.platform.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.newcapec.framework.core.model.CommonModel;

/**
 * 用户实体类
 * 
 * @author chongfeigao
 * 
 */
@Entity
@Table(name = "base_user")
public class User extends CommonModel {

	private static final long serialVersionUID = -693841966186554489L;
	/* 用户登录账号 */
	private String empCode;
	/* 登陆密码 */
	private String empPwd;
	/* 性别 1：男、0：女 */
	private String sex;
	/* 用户真实姓名 */
	private String name;
	/* 用户民族 */
	private String nation;
	/* 证件类型: */
	private String certificateid;
	/* 证件编号 */
	private String idcardno;
	/* 邮件 */
	private String email;
	/* 手机 */
	private String telephone;
	/* 账号状态 0：禁用 1：启用 */
	private String empstate;
	/* 用户选择的主题样式 */
	private String themes;
	/* 创建时间 */
	private Date opdt;
	/* 是否删除状态：0正常 1删除 */
	private String isdelete;
	/* 用户ID */
	private String empid;
	/* 客户代码 */
	private String customerunitcode;
	/* 登陆限制 */
	private String loglimit;
	/* 角色或者角色组编号 */
	private String roleid;
	/* 角色类型：0 单个角色 1 角色组 */
//	private String roletype;

	@Column(name = "roleid", length = 12)
	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

//	@Column(name = "roletype", length = 12)
//	public String getRoletype() {
//		return roletype;
//	}
//
//	public void setRoletype(String roletype) {
//		this.roletype = roletype;
//	}

	@Column(name = "empid", length = 12)
	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	@Column(name = "loglimit", length = 2)
	public String getLoglimit() {
		return loglimit;
	}

	public void setLoglimit(String loglimit) {
		this.loglimit = loglimit;
	}

	@Column(name = "empcode", length = 16)
	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	@Column(name = "emppwd", length = 32)
	public String getEmpPwd() {
		return empPwd;
	}

	public void setEmpPwd(String empPwd) {
		this.empPwd = empPwd;
	}

	@Column(name = "themes", length = 15)
	public String getThemes() {
		return themes;
	}

	public void setThemes(String themes) {
		this.themes = themes;
	}

	@Column(name = "nation", length = 2)
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Column(name = "name", length = 25)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "email", length = 25)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "telephone", length = 13)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "empstate", length = 8)
	public String getEmpstate() {
		return this.empstate;
	}

	public void setEmpstate(String empstate) {
		this.empstate = empstate;
	}

	@Column(name = "sex", length = 8)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "opdt")
	public Date getOpdt() {
		return opdt;
	}

	public void setOpdt(Date opdt) {
		this.opdt = opdt;
	}

	@Column(name = "certificateid", length = 5)
	public String getCertificateid() {
		return certificateid;
	}

	public void setCertificateid(String certificateid) {
		this.certificateid = certificateid;
	}

	@Column(name = "idcardno", length = 32)
	public String getIdcardno() {
		return idcardno;
	}

	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
	}

	@Column(name = "isdelete", length = 2)
	public String getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}

	@Column(name = "customerunitcode", length = 6)
	public String getCustomerunitcode() {
		return customerunitcode;
	}

	public void setCustomerunitcode(String customerunitcode) {
		this.customerunitcode = customerunitcode;
	}
}