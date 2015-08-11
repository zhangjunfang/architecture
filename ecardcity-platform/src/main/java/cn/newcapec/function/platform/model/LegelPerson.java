package cn.newcapec.function.platform.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.newcapec.framework.core.model.CommonModel;

/**
 * <p>
 * Title: LegelPerson
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright(c) 2011 郑州新开普电子股份有限公司
 * </p>
 * 
 * @author 黄鑫 (huangxin)
 * @version
 * @data 创建日期：2011-11-11 修改日期： 修改人： 复审人：
 * @generated
 */
@Entity
@Table(name = "BASE_LEGAL_PERSON")
public class LegelPerson extends CommonModel {
	private static final long serialVersionUID = 1L;

	/* 客户代码 */
	private String customerUnitCode;

	@Column(name = "customerUnitCode", length = 12)
	public String getCustomerUnitCode() {
		return this.customerUnitCode;
	}

	public void setCustomerUnitCode(String customerUnitCode) {
		this.customerUnitCode = customerUnitCode;
	}

	/* 客户名称 */
	private String customerName;

	@Column(name = "customerName", length = 100)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/* 客户名称简拼 */
	private String customerNameJP;

	@Column(name = "customerNameJP", length = 100)
	public String getCustomerNameJP() {
		return this.customerNameJP;
	}

	public void setCustomerNameJP(String customerNameJP) {
		this.customerNameJP = customerNameJP;
	}

	/* 联系人 */
	private String linkMan;

	@Column(name = "linkMan", length = 100)
	public String getLinkMan() {
		return this.linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	/* 联系电话 */
	private String telephone;

	@Column(name = "telephone", length = 13)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/* 客户地址 */
	private String address;

	@Column(name = "address", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/* 邮箱 */
	private String email;

	@Column(name = "email", length = 200)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/* 所属银行 */
	private Integer bankCode;

	@Column(name = "bankCode", length = 255)
	public Integer getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(Integer bankCode) {
		this.bankCode = bankCode;
	}

	/* 转账卡号 */
	private String bankCodeNo;

	@Column(name = "bankCodeNo", length = 30)
	public String getBankCodeNo() {
		return this.bankCodeNo;
	}

	public void setBankCodeNo(String bankCodeNo) {
		this.bankCodeNo = bankCodeNo;
	}

	/* 数据存储数据库连接字符串 */
	private String dataBaseStr;

	@Column(name = "dataBaseStr", length = 200)
	public String getDataBaseStr() {
		return this.dataBaseStr;
	}

	public void setDataBaseStr(String dataBaseStr) {
		this.dataBaseStr = dataBaseStr;
	}

	/* 访问Web服务地址 */
	private String webUrl;

	@Column(name = "webUrl", length = 200)
	public String getWebUrl() {
		return this.webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	/* 最后一次操作时间 */
	private Date opDt;

	@Column(name = "opDt", length = 255)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getOpDt() {
		return this.opDt;
	}

	public void setOpDt(Date opDt) {
		this.opDt = opDt;
	}

	/* 最后一次操作时间 */
	private Date openDt;

	@Column(name = "openDt", length = 255)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getOpenDt() {
		return this.openDt;
	}

	public void setOpenDt(Date openDt) {
		this.openDt = openDt;
	}

	/* 备注信息 */
	private String remark;

	@Column(name = "remark", length = 255)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/* 状态 */
	private Integer status;

	@Column(name = "status", length = 255)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/* 逻辑删除字段 */
	private Integer isDelete;

	@Column(name = "isDelete", length = 255)
	public Integer getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	/* 是否正在结帐 */
	private Integer isBalance;

	@Column(name = "isBalance", length = 255)
	public Integer getIsBalance() {
		return this.isBalance;
	}

	public void setIsBalance(Integer isBalance) {
		this.isBalance = isBalance;
	}

}