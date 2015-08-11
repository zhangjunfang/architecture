/**
 *
 */
package com.ocean.test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 *
 * @Description :
 * @author : ocean
 * @date : 2014-5-13 上午10:35:37
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
public class CustGrantInfo extends AbstractBasicData<CustGrantInfo> {

	/**
	 * 各项参数总长度
	 */
	public static final int CUSTOMERNAME = 65;

	public static final int GRANTSERIALNO = 4;

	public static final int CUSTOMERCODE = 7;

	public static final int GRANTDT = 13;

	public static final int PWDVER = 2;

	public static final int MARKET = 5;

	public static final int GRANTRIGHTS = 7;

	public static final int SYSID = 4;

	public static final int GRANTPWD = 33;

	public static final int STATUS = 1;

	public static final int INVALIDDT = 9;

	public static final int SYSREGCODE = 33;

	public static final int GRANTINFOVER = 7;

	public static final int SUBSYSTEMTYPE = 7;

	public static final int GRANTCODE = 129;

	public static final int CLIENTSYSCODE = 33;

	public static final int SYSTEMGRANTNUMBER = 9;

	public static final int CUSTBH = 17;

	public static final int CUSTCOUNTLIMITED = 4;

	public static final int CUSTRELEASECARDLIMITED = 4;

	public static final int CUSTREPAIRCARDLIMITED = 4;

	public static final int SOFTSETUPTYPE = 3;

	public static final int RANDOMNUMBER = 9;

	public static final int CUSTGRANTKEY = 49;

	public static final int CUSTM1CARDKEY = 49;

	public static final int ALLGRANTINFO = 2049;;

	/**
	 * 各项参数起始长度
	 */
	public static final int START_CUSTOMERNAME = 0;

	public static final int START_GRANTSERIALNO = CUSTOMERNAME;

	public static final int START_CUSTOMERCODE = START_GRANTSERIALNO
			+ GRANTSERIALNO;

	public static final int START_GRANTDT = START_CUSTOMERCODE + CUSTOMERCODE;

	public static final int START_PWDVER = START_GRANTDT + GRANTDT;

	public static final int START_MARKET = START_PWDVER + PWDVER;

	public static final int START_GRANTRIGHTS = START_MARKET + MARKET;

	public static final int START_SYSID = START_GRANTRIGHTS + GRANTRIGHTS;

	public static final int START_GRANTPWD = START_SYSID + SYSID;

	public static final int START_STATUS = START_GRANTPWD + GRANTPWD;

	public static final int START_INVALIDDT = START_STATUS + STATUS;

	public static final int START_SYSREGCODE = START_INVALIDDT + INVALIDDT;

	public static final int START_GRANTINFOVER = START_SYSREGCODE + SYSREGCODE;

	public static final int START_SUBSYSTEMTYPE = START_GRANTINFOVER
			+ GRANTINFOVER;

	public static final int START_GRANTCODE = START_SUBSYSTEMTYPE
			+ SUBSYSTEMTYPE;

	public static final int START_CLIENTSYSCODE = START_GRANTCODE + GRANTCODE;

	public static final int START_SYSTEMGRANTNUMBER = START_CLIENTSYSCODE
			+ CLIENTSYSCODE;

	public static final int START_CUSTBH = START_SYSTEMGRANTNUMBER
			+ SYSTEMGRANTNUMBER;

	public static final int START_CUSTCOUNTLIMITED = START_CUSTBH + CUSTBH;

	public static final int START_CUSTRELEASECARDLIMITED = START_CUSTCOUNTLIMITED
			+ CUSTCOUNTLIMITED;

	public static final int START_CUSTREPAIRCARDLIMITED = START_CUSTRELEASECARDLIMITED
			+ CUSTRELEASECARDLIMITED;

	public static final int START_SOFTSETUPTYPE = START_CUSTREPAIRCARDLIMITED
			+ CUSTREPAIRCARDLIMITED;

	public static final int START_RANDOMNUMBER = START_SOFTSETUPTYPE
			+ SOFTSETUPTYPE;

	public static final int START_CUSTGRANTKEY = START_RANDOMNUMBER
			+ RANDOMNUMBER;

	public static final int START_CUSTM1CARDKEY = START_CUSTGRANTKEY
			+ CUSTGRANTKEY;

	public static final int START_ALLGRANTINFO = START_CUSTM1CARDKEY
			+ CUSTM1CARDKEY;

	// 07原有的 共12个
	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 65)]
	// 客户名称
	public String customerName;
	// 授权序列号（C4）
	public int grantSerialNO;

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 7)]
	public String CustomerCode; // 授权客户代码（C6）

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 13)]
	public String GrantDt; // 授权时间(C12,YYYYMMDDHH)

	// [MarshalAs(UnmanagedType.ByValArray, SizeConst = 2)]
	public String PwdVer; // 密钥版本（C2, B1.B2）

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 5)]
	public String Market; // 市场区域（C4）

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 7)]
	public String GrantRights; // 使用权限（C6）

	public int SysID; // 系统ID

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 33)]
	public String GrantPwd; // 系统的库内密码

	public byte Status; // 授权状态（C1）

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 9)]
	public String InvalidDt; // 有效期（C8,YYYYMMDD）

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 33)]
	public String SysRegCode; // 系统注册码（C6）

	// 新增加的 共15个
	// ======================================================================
	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 7)]
	public String GrantInfoVer;// 授权信息版本=系统发布类型(01 02)+系统使用级别(01 02)+客户类型(01
								// 02)，共6中组合, 取值范围(010101 010102 010201 020101
								// 020102 020201)

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 7)]
	public String SubSystemType;// 子系统类型 01 02 03 04 05

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 129)]
	public String GrantCode;// 授权码128位

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 33)]
	public String ClientSysCode;// 特征码32位

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 9)]
	public String SystemGrantNumber;// 系统授权流水号

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 17)]
	public String Custbh;// 客户编号

	public int CustCountLimited; // 客户数量限制

	public int CustReleaseCardLimited; // 客户发卡数量限制

	public int CustRepairCardLimited; // 客户修卡数量限制

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 3)]
	public String SoftSetUpType;// 授权软件安装方式 00 01 CS或BS模式

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 9)]
	public String RandomNumber; // 随机数

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 49)]
	public String CustGrantKey; // 客户授权根密钥

	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 49)]
	public String CustM1CardKey; // 客户M1卡根密钥

	// 特殊的
	// [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 2049)]
	public String ALlGrantInfo; // 授权文件的授权信息密文

	public CustGrantInfo() throws NativeException {
		super(null);
		createPointer();
		mValue = this;
	}

	public static void main(String[] args) throws NativeException {

		// CustGrantInfo cgi = new CustGrantInfo();
	}

	@Override
	public Pointer getPointer() {
		return this.pointer;
	}

	public void setPointer(Pointer pointer) {
		this.pointer = pointer;
	}

	@Override
	public Pointer createPointer() throws NativeException {
		pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
		return pointer;

	}

	@Override
	public int getSizeOf() {

		return 2564;
	}

	@Override
	public CustGrantInfo getValueFromPointer() {
		/**
		 * 给结构体传值
		 */
		// this.pointer.setStringAt(START_CUSTOMERNAME, this.customerName);
		//
		// this.pointer.setIntAt(START_GRANTSERIALNO, this.grantSerialNO);
		// System.out.println("customerName==="+grantSerialNO);
		// this.pointer.setStringAt(START_CUSTOMERCODE, this.CustomerCode);
		//
		// this.pointer.setStringAt(START_GRANTDT, this.GrantDt);
		//
		// this.pointer.setStringAt(START_PWDVER, this.PwdVer);
		//
		// this.pointer.setStringAt(START_MARKET, this.Market);
		//
		// this.pointer.setStringAt(START_GRANTRIGHTS, this.GrantRights);
		//
		// this.pointer.setIntAt(START_SYSID, this.SysID);
		//
		// this.pointer.setStringAt(START_GRANTPWD, this.GrantPwd);
		//
		// this.pointer.setIntAt(START_STATUS, this.Status);
		//
		// this.pointer.setStringAt(START_INVALIDDT, this.InvalidDt);
		//
		// this.pointer.setStringAt(START_SYSREGCODE, this.SysRegCode);
		//
		// this.pointer.setStringAt(START_GRANTINFOVER, this.GrantInfoVer);
		//
		// this.pointer.setStringAt(START_SUBSYSTEMTYPE, this.SubSystemType);
		//
		// this.pointer.setStringAt(START_GRANTCODE, this.GrantCode);
		//
		// this.pointer.setStringAt(START_CLIENTSYSCODE, this.ClientSysCode);
		//
		// this.pointer.setStringAt(START_SYSTEMGRANTNUMBER,
		// this.SystemGrantNumber);
		//
		// this.pointer.setStringAt(START_CUSTBH, this.Custbh);
		//
		// this.pointer.setIntAt(START_CUSTCOUNTLIMITED, this.CustCountLimited);
		//
		// this.pointer.setIntAt(START_CUSTRELEASECARDLIMITED,
		// this.CustReleaseCardLimited);
		//
		// this.pointer.setIntAt(START_CUSTREPAIRCARDLIMITED,
		// this.CustRepairCardLimited);
		//
		// this.pointer.setStringAt(START_SOFTSETUPTYPE, this.SoftSetUpType);
		//
		// this.pointer.setStringAt(START_RANDOMNUMBER, this.RandomNumber);
		//
		// this.pointer.setStringAt(START_CUSTGRANTKEY, this.CustGrantKey);
		//
		// this.pointer.setStringAt(START_CUSTM1CARDKEY, this.CustM1CardKey);
		//
		// this.pointer.setStringAt(START_ALLGRANTINFO, this.ALlGrantInfo);
		/**
		 * 获取结构体的值
		 */

		// byte[] bb = st_cust.getMemory();
		// CustGrantInfo2 custGrantInfo2 = new CustGrantInfo2();
		try {
			int offset = 0;
			Class<Integer> intClass = int.class;
			Map<String, String> result = getAsStringSerial(offset, 65, pointer);
			this.customerName = result.get("serial");

			// int 数据偏移量开始位置
			int pointerOffset = Integer.parseInt(result.get("offset")) + 1;
			// 获取int数据值
			int grantSerialNO = pointer.getAsInt(pointerOffset);
			// 给结构体赋值
			this.grantSerialNO = grantSerialNO;
			// 计算下一个数据偏移量
			offset = pointerOffset + getIntPointerSize(intClass);

			result = getAsStringSerial(offset, 7, pointer);
			this.CustomerCode = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));
			// 字符串遇到数字0标示字符串结束，所以需要加上数字 1
			offset = pointerOffset + 1;
			result = getAsStringSerial(offset, 13, pointer);
			this.GrantDt = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));
			// 字符串遇到数字0标示字符串结束，所以需要加上数字 1
			offset = pointerOffset + 1;
			result = getAsStringSerial(offset, 2, pointer);

			this.PwdVer = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));
			// 下一个数据的偏移量
			offset = pointerOffset + 1;
			// market
			result = getAsStringSerial(offset, 5, pointer);
			this.Market = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));

			// 下一个数据的偏移量
			offset = pointerOffset + 1;
			// grantRights
			result = getAsStringSerial(offset, 7, pointer);
			this.GrantRights = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));

			// 下一个数据的偏移量
			offset = pointerOffset + 1;
			// sysID
			int sysID = pointer.getAsInt(offset);
			this.SysID = sysID;
			// 下一个数据的偏移量
			offset = offset + getIntPointerSize(intClass);
			// grantPwd
			result = getAsStringSerial(offset, 33, pointer);
			this.GrantPwd = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));
			// 下一个数据的偏移量
			offset = pointerOffset + 1;
			// status
			this.Status = pointer.getAsByte(offset);

			// 下一个数据的偏移量
			offset = offset + 1;
			// invalidDt
			result = getAsStringSerial(offset, 9, pointer);
			this.InvalidDt = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));
			// 下一个数据的偏移量
			offset = pointerOffset + 1;
			// SysRegCode
			result = getAsStringSerial(offset, 33, pointer);
			this.SysRegCode = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));

			// 下一个数据的偏移量
			offset = pointerOffset + 1;
			// GrantInfoVer
			result = getAsStringSerial(offset, 7, pointer);
			this.GrantInfoVer = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));

			// 下一个数据的偏移量
			offset = pointerOffset + 1;
			// SubSystemType
			result = getAsStringSerial(offset, 7, pointer);
			this.SubSystemType = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));

			// 下一个数据的偏移量
			offset = pointerOffset + 1;
			// GrantCode
			result = getAsStringSerial(offset, 129, pointer);
			this.GrantCode = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));

			// 下一个数据的偏移量
			offset = pointerOffset + 1;
			// ClientSysCode
			result = getAsStringSerial16(offset, 33, pointer);
			this.ClientSysCode = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));

			// 下一个数据的偏移量
			offset = pointerOffset + 1;
			// SystemGrantNumber
			result = getAsStringSerial(offset, 9, pointer);
			this.SystemGrantNumber = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));

			// 下一个数据的偏移量
			offset = pointerOffset + 1;
			// Custbh
			result = getAsStringSerial(offset, 17, pointer);
			this.Custbh = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));
			// 下一个数据的偏移量
			offset = pointerOffset + 1;
			// CustCountLimited
			this.CustCountLimited = pointer.getAsInt(offset);

			// 下一个数据的偏移量
			offset = offset + getIntPointerSize(intClass);
			// CustReleaseCardLimited
			this.CustReleaseCardLimited = pointer.getAsInt(offset);

			// 下一个数据的偏移量
			offset = offset + getIntPointerSize(intClass);
			// CustRepairCardLimited
			this.CustRepairCardLimited = pointer.getAsInt(offset);

			// 下一个数据的偏移量
			offset = offset + getIntPointerSize(intClass);
			// SoftSetUpType
			result = getAsStringSerial(offset, 3, pointer);
			this.SoftSetUpType = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));

			// 下一个数据的偏移量
			offset = pointerOffset + 1;
			// RandomNumber
			result = getAsStringSerial(offset, 9, pointer);
			this.RandomNumber = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));

			// 下一个数据的偏移量
			offset = pointerOffset + 1;
			// CustGrantKey
			result = getAsStringSerial(offset, 49, pointer);
			this.CustGrantKey = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));

			// 下一个数据的偏移量
			offset = pointerOffset + 1;
			// CustM1CardKey
			result = getAsStringSerial(offset, 49, pointer);
			this.CustM1CardKey = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));

			// 下一个数据的偏移量
			offset = pointerOffset + 1;
			// ALlGrantInfo
			result = getAsStringSerial(offset, 2049, pointer);
			this.ALlGrantInfo = result.get("serial");
			pointerOffset = Integer.parseInt(result.get("offset"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NativeException e) {
			e.printStackTrace();
		}

		return this;
	}

	public static Map<String, String> getAsStringSerial(int offset,
			int byteSize, Pointer pointer) {
		try {
			byte[] bb = pointer.getMemory();
			byte[] bs = new byte[byteSize];
			int byteSizetemp = 0;
			int length = bb.length;
			for (int j = offset; j < length; j++) {
				if (bb[j] == 0) {
					offset = j;
					break;
				} else {
					bs[byteSizetemp] = bb[j];
				}
				byteSizetemp++;
			}
			Map<String, String> map = new HashMap<String, String>(2);
			map.put("offset", offset + "");
			map.put("serial", new String(bs, 0, byteSizetemp, "gbk"));
			return map;
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (NativeException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, String> getAsStringSerial16(int offset,
			int byteSize, Pointer pointer) {

		try {
			byte[] bb = pointer.getMemory();

			byte[] bs = new byte[byteSize];
			int byteSizetemp = 0;
			int length = bb.length;
			for (int j = offset; j < length; j++) {
				byte b = Byte.parseByte(bb[j] + "", 16);
				if (b == 0) {
					offset = j;
					break;
				} else {
					bs[byteSizetemp] = b;
				}
				byteSizetemp++;
			}
			Map<String, String> map = new HashMap<String, String>(2);
			map.put("offset", offset + "");
			map.put("serial", new String(bs, 0, byteSizetemp, "gbk"));
			return map;
		} catch (NumberFormatException e) {

			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (NativeException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static int getIntPointerSize(Class<Integer> intClass) {

		return 4;
	}

	public static int getBytePointerSize(Class<Byte> byteClass) {

		return 1;
	}

	public static int getShortPointerSize(Class<Short> shortClass) {

		return 2;
	}

	public static int getFloatPointerSize(Class<Short> shortClass) {

		return 4;
	}

	public static int getDoublePointerSize(Class<Short> shortClass) {

		return 8;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustGrantInfo [CustomerName=");
		builder.append(customerName);
		builder.append(", GrantSerialNO=");
		builder.append(grantSerialNO);
		builder.append(", CustomerCode=");
		builder.append(CustomerCode);
		builder.append(", GrantDt=");
		builder.append(GrantDt);
		builder.append(", PwdVer=");
		builder.append(PwdVer);
		builder.append(", Market=");
		builder.append(Market);
		builder.append(", GrantRights=");
		builder.append(GrantRights);
		builder.append(", SysID=");
		builder.append(SysID);
		builder.append(", GrantPwd=");
		builder.append(GrantPwd);
		builder.append(", Status=");
		builder.append(Status);
		builder.append(", InvalidDt=");
		builder.append(InvalidDt);
		builder.append(", SysRegCode=");
		builder.append(SysRegCode);
		builder.append(", GrantInfoVer=");
		builder.append(GrantInfoVer);
		builder.append(", SubSystemType=");
		builder.append(SubSystemType);
		builder.append(", GrantCode=");
		builder.append(GrantCode);
		builder.append(", ClientSysCode=");
		builder.append(ClientSysCode);
		builder.append(", SystemGrantNumber=");
		builder.append(SystemGrantNumber);
		builder.append(", Custbh=");
		builder.append(Custbh);
		builder.append(", CustCountLimited=");
		builder.append(CustCountLimited);
		builder.append(", CustReleaseCardLimited=");
		builder.append(CustReleaseCardLimited);
		builder.append(", CustRepairCardLimited=");
		builder.append(CustRepairCardLimited);
		builder.append(", SoftSetUpType=");
		builder.append(SoftSetUpType);
		builder.append(", RandomNumber=");
		builder.append(RandomNumber);
		builder.append(", CustGrantKey=");
		builder.append(CustGrantKey);
		builder.append(", CustM1CardKey=");
		builder.append(CustM1CardKey);
		builder.append(", ALlGrantInfo=");
		builder.append(ALlGrantInfo);
		builder.append("]");
		return builder.toString();
	}

}
