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
@Table(name = "base_legal_person")
public class LegalPerson extends CommonModel {

	private static final long serialVersionUID = -693841966186554489L;
	/* 客户代码（国家地区编号（3）+预留（5）+分配序号（3）），如08600000001 */
	@Column(name = "customerunitcode", length = 12)
	private String customerunitcode;
	/* 客户名称 */
	@Column(name = "customername", length = 50)
	private String customername;
	/* 客户名称简称 */
	@Column(name = "customernamejp", length = 50)
	private String customernamejp;
	/* 联系人 */
	@Column(name = "linkman", length = 50)
	private String linkman;
	/* 联系电话 */
	@Column(name = "telephone", length = 13)
	private String telephone;
	/* 客户地址: */
	@Column(name = "address", length = 100)
	private String address;
	/* 邮件 */
	@Column(name = "email", length = 100)
	private String email;
	/* 所属银行(base_bank) */
	@Column(name = "bankcode", length = 10)
	private Integer bankcode;
	/* 转账卡号（银行卡号） */
	@Column(name = "bankcardno", length = 15)
	private String bankcardno;
	/* 数据存储数据库连接字符串（预留） */
	@Column(name = "databasestr", length = 100)
	private String databasestr;
	/* 访问Web服务地址 */
	@Column(name = "weburl", length = 100)
	private String weburl;
	/* 最后一次操作时间（每次操作都更新） */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "opdt")
	private Date opdt;
	/* 客户注册时间（添加时候更新，其他时间不维护）） */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "opendt")
	private Date opendt;
	/* 备注信息 */
	@Column(name = "remark", length = 100)
	private String remark;
	/* 0：禁用状态 1：未授权 2：已授权 */
	@Column(name = "status")
	private Integer status;
	/* 逻辑删除字段（0未删除，1 删除） */
	@Column(name = "isdelete")
	private String isdelete;
	/* 是否正在结帐（0：否；1：是 2：准备结账 非0状态不允许执行日结操作） */
	@Column(name = "isbalance")
	private String isbalance;

	public String getCustomerunitcode() {
		return customerunitcode;
	}

	public void setCustomerunitcode(String customerunitcode) {
		this.customerunitcode = customerunitcode;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getCustomernamejp() {
		return customernamejp;
	}

	public void setCustomernamejp(String customernamejp) {
		this.customernamejp = customernamejp;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getBankcode() {
		return bankcode;
	}

	public void setBankcode(Integer bankcode) {
		this.bankcode = bankcode;
	}

	public String getBankcardno() {
		return bankcardno;
	}

	public void setBankcardno(String bankcardno) {
		this.bankcardno = bankcardno;
	}

	public String getDatabasestr() {
		return databasestr;
	}

	public void setDatabasestr(String databasestr) {
		this.databasestr = databasestr;
	}

	public String getWeburl() {
		return weburl;
	}

	public void setWeburl(String weburl) {
		this.weburl = weburl;
	}

	public Date getOpdt() {
		return opdt;
	}

	public void setOpdt(Date opdt) {
		this.opdt = opdt;
	}

	public Date getOpendt() {
		return opendt;
	}

	public void setOpendt(Date opendt) {
		this.opendt = opendt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}

	public String getIsbalance() {
		return isbalance;
	}

	public void setIsbalance(String isbalance) {
		this.isbalance = isbalance;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}