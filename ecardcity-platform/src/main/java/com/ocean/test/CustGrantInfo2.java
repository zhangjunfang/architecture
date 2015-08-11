/**
 *
 */
package com.ocean.test;

import java.io.Serializable;

/**
 *
 * @Description :
 * @author : ocean
 * @date : 2014-5-13 上午10:35:29
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
public class CustGrantInfo2 implements Serializable {

	private static final long serialVersionUID = 6865900508091860479L;

	/**
	 *
	 */
	public CustGrantInfo2() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param customerName
	 * @param grantSerialNO
	 * @param customerCode
	 * @param grantDt
	 * @param pwdVer
	 * @param market
	 * @param grantRights
	 * @param sysID
	 * @param grantPwd
	 * @param status
	 * @param invalidDt
	 * @param sysRegCode
	 * @param grantInfoVer
	 * @param subSystemType
	 * @param grantCode
	 * @param clientSysCode
	 * @param systemGrantNumber
	 * @param custbh
	 * @param custCountLimited
	 * @param custReleaseCardLimited
	 * @param custRepairCardLimited
	 * @param softSetUpType
	 * @param randomNumber
	 * @param custGrantKey
	 * @param custM1CardKey
	 * @param aLlGrantInfo
	 */
	public CustGrantInfo2(String customerName, int grantSerialNO,
			String customerCode, String grantDt, String pwdVer, String market,
			String grantRights, int sysID, String grantPwd, byte status,
			String invalidDt, String sysRegCode, String grantInfoVer,
			String subSystemType, String grantCode, String clientSysCode,
			String systemGrantNumber, String custbh, int custCountLimited,
			int custReleaseCardLimited, int custRepairCardLimited,
			String softSetUpType, String randomNumber, String custGrantKey,
			String custM1CardKey, String aLlGrantInfo) {
		super();
		this.customerName = customerName;
		this.grantSerialNO = grantSerialNO;
		this.customerCode = customerCode;
		this.grantDt = grantDt;
		this.pwdVer = pwdVer;
		this.market = market;
		this.grantRights = grantRights;
		this.sysID = sysID;
		this.grantPwd = grantPwd;
		this.status = status;
		this.invalidDt = invalidDt;
		this.sysRegCode = sysRegCode;
		this.grantInfoVer = grantInfoVer;
		this.subSystemType = subSystemType;
		this.grantCode = grantCode;
		this.clientSysCode = clientSysCode;
		this.systemGrantNumber = systemGrantNumber;
		this.custbh = custbh;
		this.custCountLimited = custCountLimited;
		this.custReleaseCardLimited = custReleaseCardLimited;
		this.custRepairCardLimited = custRepairCardLimited;
		this.softSetUpType = softSetUpType;
		this.randomNumber = randomNumber;
		this.custGrantKey = custGrantKey;
		this.custM1CardKey = custM1CardKey;
		ALlGrantInfo = aLlGrantInfo;
	}

	// 07原有的 共12个
	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 65)]
	// 客户名称
	public String customerName;
	// 授权序列号（C4）
	public int grantSerialNO;

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 7)]
	public String customerCode; // 授权客户代码（C6）

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 13)]
	public String grantDt; // 授权时间(C12,YYYYMMDDHH)

	// [MarshalAs(UnmanagedType.ByValArray, SizeConst = 2)]
	public String pwdVer; // 密钥版本（C2, B1.B2）

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 5)]
	public String market; // 市场区域（C4）

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 7)]
	public String grantRights; // 使用权限（C6）

	public int sysID; // 系统ID

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 33)]
	public String grantPwd; // 系统的库内密码

	public byte status; // 授权状态（C1）

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 9)]
	public String invalidDt; // 有效期（C8,YYYYMMDD）

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 33)]
	public String sysRegCode; // 系统注册码（C6）

	// 新增加的 共15个
	// ======================================================================
	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 7)]
	public String grantInfoVer;// 授权信息版本=系统发布类型(01 02)+系统使用级别(01 02)+客户类型(01
								// 02)，共6中组合, 取值范围(010101 010102 010201 020101
								// 020102 020201)

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 7)]
	public String subSystemType;// 子系统类型 01 02 03 04 05

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 129)]
	public String grantCode;// 授权码128位

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 33)]
	public String clientSysCode;// 特征码32位

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 9)]
	public String systemGrantNumber;// 系统授权流水号

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 17)]
	public String custbh;// 客户编号

	public int custCountLimited; // 客户数量限制

	public int custReleaseCardLimited; // 客户发卡数量限制

	public int custRepairCardLimited; // 客户修卡数量限制

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 3)]
	public String softSetUpType;// 授权软件安装方式 00 01 CS或BS模式

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 9)]
	public String randomNumber; // 随机数

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 49)]
	public String custGrantKey; // 客户授权根密钥

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 49)]
	public String custM1CardKey; // 客户M1卡根密钥

	// 特殊的
	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 2049)]
	public String ALlGrantInfo; // 授权文件的授权信息密文

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getGrantSerialNO() {
		return grantSerialNO;
	}

	public void setGrantSerialNO(int grantSerialNO) {
		this.grantSerialNO = grantSerialNO;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getGrantDt() {
		return grantDt;
	}

	public void setGrantDt(String grantDt) {
		this.grantDt = grantDt;
	}

	public String getPwdVer() {
		return pwdVer;
	}

	public void setPwdVer(String pwdVer) {
		this.pwdVer = pwdVer;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getGrantRights() {
		return grantRights;
	}

	public void setGrantRights(String grantRights) {
		this.grantRights = grantRights;
	}

	public int getSysID() {
		return sysID;
	}

	public void setSysID(int sysID) {
		this.sysID = sysID;
	}

	public String getGrantPwd() {
		return grantPwd;
	}

	public void setGrantPwd(String grantPwd) {
		this.grantPwd = grantPwd;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getInvalidDt() {
		return invalidDt;
	}

	public void setInvalidDt(String invalidDt) {
		this.invalidDt = invalidDt;
	}

	public String getSysRegCode() {
		return sysRegCode;
	}

	public void setSysRegCode(String sysRegCode) {
		this.sysRegCode = sysRegCode;
	}

	public String getGrantInfoVer() {
		return grantInfoVer;
	}

	public void setGrantInfoVer(String grantInfoVer) {
		this.grantInfoVer = grantInfoVer;
	}

	public String getSubSystemType() {
		return subSystemType;
	}

	public void setSubSystemType(String subSystemType) {
		this.subSystemType = subSystemType;
	}

	public String getGrantCode() {
		return grantCode;
	}

	public void setGrantCode(String grantCode) {
		this.grantCode = grantCode;
	}

	public String getClientSysCode() {
		return clientSysCode;
	}

	public void setClientSysCode(String clientSysCode) {
		this.clientSysCode = clientSysCode;
	}

	public String getSystemGrantNumber() {
		return systemGrantNumber;
	}

	public void setSystemGrantNumber(String systemGrantNumber) {
		this.systemGrantNumber = systemGrantNumber;
	}

	public String getCustbh() {
		return custbh;
	}

	public void setCustbh(String custbh) {
		this.custbh = custbh;
	}

	public int getCustCountLimited() {
		return custCountLimited;
	}

	public void setCustCountLimited(int custCountLimited) {
		this.custCountLimited = custCountLimited;
	}

	public int getCustReleaseCardLimited() {
		return custReleaseCardLimited;
	}

	public void setCustReleaseCardLimited(int custReleaseCardLimited) {
		this.custReleaseCardLimited = custReleaseCardLimited;
	}

	public int getCustRepairCardLimited() {
		return custRepairCardLimited;
	}

	public void setCustRepairCardLimited(int custRepairCardLimited) {
		this.custRepairCardLimited = custRepairCardLimited;
	}

	public String getSoftSetUpType() {
		return softSetUpType;
	}

	public void setSoftSetUpType(String softSetUpType) {
		this.softSetUpType = softSetUpType;
	}

	public String getRandomNumber() {
		return randomNumber;
	}

	public void setRandomNumber(String randomNumber) {
		this.randomNumber = randomNumber;
	}

	public String getCustGrantKey() {
		return custGrantKey;
	}

	public void setCustGrantKey(String custGrantKey) {
		this.custGrantKey = custGrantKey;
	}

	public String getCustM1CardKey() {
		return custM1CardKey;
	}

	public void setCustM1CardKey(String custM1CardKey) {
		this.custM1CardKey = custM1CardKey;
	}

	public String getALlGrantInfo() {
		return ALlGrantInfo;
	}

	public void setALlGrantInfo(String aLlGrantInfo) {
		ALlGrantInfo = aLlGrantInfo;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ALlGrantInfo == null) ? 0 : ALlGrantInfo.hashCode());
		result = prime * result
				+ ((clientSysCode == null) ? 0 : clientSysCode.hashCode());
		result = prime * result + custCountLimited;
		result = prime * result
				+ ((custGrantKey == null) ? 0 : custGrantKey.hashCode());
		result = prime * result
				+ ((custM1CardKey == null) ? 0 : custM1CardKey.hashCode());
		result = prime * result + custReleaseCardLimited;
		result = prime * result + custRepairCardLimited;
		result = prime * result + ((custbh == null) ? 0 : custbh.hashCode());
		result = prime * result
				+ ((customerCode == null) ? 0 : customerCode.hashCode());
		result = prime * result
				+ ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result
				+ ((grantCode == null) ? 0 : grantCode.hashCode());
		result = prime * result + ((grantDt == null) ? 0 : grantDt.hashCode());
		result = prime * result
				+ ((grantInfoVer == null) ? 0 : grantInfoVer.hashCode());
		result = prime * result
				+ ((grantPwd == null) ? 0 : grantPwd.hashCode());
		result = prime * result
				+ ((grantRights == null) ? 0 : grantRights.hashCode());
		result = prime * result + grantSerialNO;
		result = prime * result
				+ ((invalidDt == null) ? 0 : invalidDt.hashCode());
		result = prime * result + ((market == null) ? 0 : market.hashCode());
		result = prime * result + ((pwdVer == null) ? 0 : pwdVer.hashCode());
		result = prime * result
				+ ((randomNumber == null) ? 0 : randomNumber.hashCode());
		result = prime * result
				+ ((softSetUpType == null) ? 0 : softSetUpType.hashCode());
		result = prime * result + status;
		result = prime * result
				+ ((subSystemType == null) ? 0 : subSystemType.hashCode());
		result = prime * result + sysID;
		result = prime * result
				+ ((sysRegCode == null) ? 0 : sysRegCode.hashCode());
		result = prime
				* result
				+ ((systemGrantNumber == null) ? 0 : systemGrantNumber
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustGrantInfo2 other = (CustGrantInfo2) obj;
		if (ALlGrantInfo == null) {
			if (other.ALlGrantInfo != null)
				return false;
		} else if (!ALlGrantInfo.equals(other.ALlGrantInfo))
			return false;
		if (clientSysCode == null) {
			if (other.clientSysCode != null)
				return false;
		} else if (!clientSysCode.equals(other.clientSysCode))
			return false;
		if (custCountLimited != other.custCountLimited)
			return false;
		if (custGrantKey == null) {
			if (other.custGrantKey != null)
				return false;
		} else if (!custGrantKey.equals(other.custGrantKey))
			return false;
		if (custM1CardKey == null) {
			if (other.custM1CardKey != null)
				return false;
		} else if (!custM1CardKey.equals(other.custM1CardKey))
			return false;
		if (custReleaseCardLimited != other.custReleaseCardLimited)
			return false;
		if (custRepairCardLimited != other.custRepairCardLimited)
			return false;
		if (custbh == null) {
			if (other.custbh != null)
				return false;
		} else if (!custbh.equals(other.custbh))
			return false;
		if (customerCode == null) {
			if (other.customerCode != null)
				return false;
		} else if (!customerCode.equals(other.customerCode))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (grantCode == null) {
			if (other.grantCode != null)
				return false;
		} else if (!grantCode.equals(other.grantCode))
			return false;
		if (grantDt == null) {
			if (other.grantDt != null)
				return false;
		} else if (!grantDt.equals(other.grantDt))
			return false;
		if (grantInfoVer == null) {
			if (other.grantInfoVer != null)
				return false;
		} else if (!grantInfoVer.equals(other.grantInfoVer))
			return false;
		if (grantPwd == null) {
			if (other.grantPwd != null)
				return false;
		} else if (!grantPwd.equals(other.grantPwd))
			return false;
		if (grantRights == null) {
			if (other.grantRights != null)
				return false;
		} else if (!grantRights.equals(other.grantRights))
			return false;
		if (grantSerialNO != other.grantSerialNO)
			return false;
		if (invalidDt == null) {
			if (other.invalidDt != null)
				return false;
		} else if (!invalidDt.equals(other.invalidDt))
			return false;
		if (market == null) {
			if (other.market != null)
				return false;
		} else if (!market.equals(other.market))
			return false;
		if (pwdVer == null) {
			if (other.pwdVer != null)
				return false;
		} else if (!pwdVer.equals(other.pwdVer))
			return false;
		if (randomNumber == null) {
			if (other.randomNumber != null)
				return false;
		} else if (!randomNumber.equals(other.randomNumber))
			return false;
		if (softSetUpType == null) {
			if (other.softSetUpType != null)
				return false;
		} else if (!softSetUpType.equals(other.softSetUpType))
			return false;
		if (status != other.status)
			return false;
		if (subSystemType == null) {
			if (other.subSystemType != null)
				return false;
		} else if (!subSystemType.equals(other.subSystemType))
			return false;
		if (sysID != other.sysID)
			return false;
		if (sysRegCode == null) {
			if (other.sysRegCode != null)
				return false;
		} else if (!sysRegCode.equals(other.sysRegCode))
			return false;
		if (systemGrantNumber == null) {
			if (other.systemGrantNumber != null)
				return false;
		} else if (!systemGrantNumber.equals(other.systemGrantNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustGrantInfo2 [customerName=");
		builder.append(customerName);
		builder.append(", grantSerialNO=");
		builder.append(grantSerialNO);
		builder.append(", customerCode=");
		builder.append(customerCode);
		builder.append(", grantDt=");
		builder.append(grantDt);
		builder.append(", pwdVer=");
		builder.append(pwdVer);
		builder.append(", market=");
		builder.append(market);
		builder.append(", grantRights=");
		builder.append(grantRights);
		builder.append(", sysID=");
		builder.append(sysID);
		builder.append(", grantPwd=");
		builder.append(grantPwd);
		builder.append(", status=");
		builder.append(status);
		builder.append(", invalidDt=");
		builder.append(invalidDt);
		builder.append(", sysRegCode=");
		builder.append(sysRegCode);
		builder.append(", grantInfoVer=");
		builder.append(grantInfoVer);
		builder.append(", subSystemType=");
		builder.append(subSystemType);
		builder.append(", grantCode=");
		builder.append(grantCode);
		builder.append(", clientSysCode=");
		builder.append(clientSysCode);
		builder.append(", systemGrantNumber=");
		builder.append(systemGrantNumber);
		builder.append(", custbh=");
		builder.append(custbh);
		builder.append(", custCountLimited=");
		builder.append(custCountLimited);
		builder.append(", custReleaseCardLimited=");
		builder.append(custReleaseCardLimited);
		builder.append(", custRepairCardLimited=");
		builder.append(custRepairCardLimited);
		builder.append(", softSetUpType=");
		builder.append(softSetUpType);
		builder.append(", randomNumber=");
		builder.append(randomNumber);
		builder.append(", custGrantKey=");
		builder.append(custGrantKey);
		builder.append(", custM1CardKey=");
		builder.append(custM1CardKey);
		builder.append(", ALlGrantInfo=");
		builder.append(ALlGrantInfo);
		builder.append("]");
		return builder.toString();
	}

}
