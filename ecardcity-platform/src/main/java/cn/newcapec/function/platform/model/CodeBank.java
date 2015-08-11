package cn.newcapec.function.platform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.newcapec.framework.core.model.CommonModel;

/**
 * <p>
 * Title: CodeBank
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
@Table(name = "BASE_BANK")
public class CodeBank extends CommonModel {
	private static final long serialVersionUID = 1L;

	/* 银行名称 */
	private String bankName;

	@Column(name = "bankName", length = 200)
	public String getBankName(){
		return this.bankName;
	}

	public void setBankName(String bankName){
		this.bankName = bankName;
	}

	/* 银行描述 */
	private String describe;

	@Column(name = "describe", length = 200)
	public String getDescribe(){
		return this.describe;
	}

	public void setDescribe(String describe){
		this.describe = describe;
	}

}