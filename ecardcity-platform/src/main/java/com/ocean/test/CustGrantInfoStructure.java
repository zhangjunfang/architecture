/**
 *
 */
package com.ocean.test;

import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

/**
*
* @Description :
* @author : ocean
* @date : 2014-5-13 上午10:33:15
* @email : zhangjunfang0505@163.com
* @Copyright : newcapec zhengzhou
*/
public interface CustGrantInfoStructure extends StdCallLibrary {
//public interface CustGrantInfoStructure extends Library {
	public static class CUSTGRANTINFO extends Structure {

		//07原有的，共12个
		public  byte[] CustomerName=new byte[65]; //客户名称
		public  int GrantSerialNO;//授权序列号(C4)
		public  byte[] CustomerCode=new byte[7];//授权客户代码(C6)
		public  byte[] GrantDt=new byte[13];//授权时间(C12,YYYYMMDDHH)
		public  byte PwdVer;//密钥版本(C2,B1.B2)
		public  byte[] Market=new byte[5];//市场区域(C4)
		public  byte[] GrantRights=new byte[7];//使用权限(C6)
		public  int SysID;//系统ID
		public  byte[] GrantPwd=new byte[33];//系统的库内密码
		public  byte Status;//授权状态(C1)
		public  byte[] InvalidDt=new byte[9];//有效期(C8,YYYYMMDD)
		public  byte[] SysRegCode=new byte[33];//系统注册码(C6)

		//新增加的 共15个
		public  byte[] GrantInfoVer=new byte[7];//授权信息版本=系统发布类型(01 02)+系统使用级别(01 02)+客户类型(01 02)，共6中组合, 取值范围(010101 010102 010201 020101 020102 020201)
		public  byte[] SubSystemType=new byte[7];//子系统类型 01 02 03 04 05
		public  byte[] GrantCode=new byte[129];//授权码128位
		public  byte[] ClientSysCode=new byte[33];//特征码32位
		public  byte[] SystemGrantNumber=new byte[9];//系统授权流水号
		public  byte[] Custbh=new byte[17];//客户编号
		public  int CustCountLimited;//客户数量限制
		public  int CustReleaseCardLimited;//客户发卡数量限制
		public  int CustRepairCardLimited;//客户修卡数量限制
		public  byte[] SoftSetUpType=new byte[3];//授权软件安装方式 00 01 CS或BS模式
		public  byte[] RandomNumber=new byte[9];//随机数
		public  byte[] CustGrantKey=new byte[49];//客户授权根密钥
		public  byte[] CustM1CardKey=new byte[49];//客户M1卡根密钥

		//特殊的
		public  byte[] ALlGrantInfo=new byte[2049];//授权文件的授权信息密文

		public static class ByReference extends CUSTGRANTINFO implements
				Structure.ByReference {
		}

		public static class ByValue extends CUSTGRANTINFO implements
				Structure.ByValue {
		}

	}

	public int ParseGrantFile(String characterCode, String fileName,
			CUSTGRANTINFO info, int count);

	//public int GetCharacterCode(byte[] b);
	public int GetCharacterCode(String s);
	//public int GetCharacterCode(char[] s);
}
