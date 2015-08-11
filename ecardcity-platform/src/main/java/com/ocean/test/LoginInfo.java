package com.ocean.test;

import java.io.Serializable;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
/**
*
* @Description :
* @author : ocean
* @date : 2014-5-13 上午10:33:15
* @email : zhangjunfang0505@163.com
* @Copyright : newcapec zhengzhou
*/
public class LoginInfo implements Serializable {

	private static final long serialVersionUID = -1L;

	/**
	 * 各项参数总长度
	 */
	public static final int SP_NODE_ID_LEN = 4;

	public static final int GW_IP_LEN = 16;

	public static final int GW_PORT_LEN = 4;

	public static final int LOGIN_GW_USER_LEN = 17;

	public static final int LOGIN_GW_PWD_LEN = 17;

	public static final int APP_IP_LEN = 16;

	public static final int APP_PORT_LEN = 4;

	public static final int GW_LINK_USER_LEN = 17;

	public static final int GW_LINK_PWD_LEN = 17;

	public static final int SP_ALL_LEN = 112;

	/**
	 * 各项参数起始长度
	 */
	public static final int START_SP_NODE_ID = 0;

	public static final int START_GW_IP = 4;

	public static final int START_GW_PORT = 20;

	public static final int START_LOGIN_GW_USER = 24;

	public static final int START_LOGIN_GW_PWD = 41;

	public static final int START_APP_IP = 57;

	public static final int START_APP_PORT = 61;

	public static final int START_GW_LINK_USER = 78;

	public static final int START_GW_LINK_PWD = 95;

	private int spNodeId; // sp的设备代码，应该由网关为其分配

	private String gwIp; // 网关的侦听IP地址

	private int gwPort; // 网关的侦听端口

	private String loginGwUser; // sp登陆网关时所用的用户名

	private String loginGwPwd; // sp登陆网关时所用的密码

	private String appIp; // sp的侦听IP地址

	private int appPort; // sp的侦听端口

	private String gwLinkUser; // 网关登陆sp时所用的用户名

	private String gwLinkPwd; // 网关登陆sp时所用的密码
	private Pointer pointer;

	public void createPointer() throws NativeException {
		this.pointer = new Pointer(
				MemoryBlockFactory.createMemoryBlock(getSizeOf()));
	}

	public int getSizeOf() {
		return SP_ALL_LEN;
	}

	public void setSpNodeId(int spNodeId) {
		this.spNodeId = spNodeId;
	}

	public void setGwIp(String gwIp) throws NativeException {
		if (gwIp == null || gwIp.getBytes().length > GW_IP_LEN) {
			throw new NativeException("传入网关的侦听IP地址  参数错误");
		}
		this.gwIp = gwIp;
	}

	public void setGwPort(int gwPort) {
		this.gwPort = gwPort;
	}

	public void setLoginGwUser(String loginGwUser) throws NativeException {
		if (loginGwUser == null
				|| loginGwUser.getBytes().length > LOGIN_GW_USER_LEN) {
			throw new NativeException("sp登陆网关时所用的用户名  参数错误");
		}
		this.loginGwUser = loginGwUser;
	}

	public void setLoginGwPwd(String loginGwPwd) throws NativeException {
		if (loginGwPwd == null
				|| loginGwPwd.getBytes().length > LOGIN_GW_PWD_LEN) {
			throw new NativeException("sp登陆网关时所用的密码   参数错误");
		}
		this.loginGwPwd = loginGwPwd;
	}

	public void setAppIp(String appIp) throws NativeException {
		if (appIp == null || appIp.getBytes().length > APP_IP_LEN) {
			throw new NativeException("sp的侦听IP地址   参数错误");
		}
		this.appIp = appIp;
	}

	public void setAppPort(int appPort) {
		this.appPort = appPort;
	}

	public void setGwLinkUser(String gwLinkUser) throws NativeException {
		if (gwLinkUser == null
				|| gwLinkUser.getBytes().length > GW_LINK_USER_LEN) {
			throw new NativeException("网关登陆sp时所用的用户名   参数错误");
		}
		this.gwLinkUser = gwLinkUser;
	}

	public void setGwLinkPwd(String gwLinkPwd) throws NativeException {
		if (gwLinkPwd == null || gwLinkPwd.getBytes().length > GW_LINK_PWD_LEN) {
			throw new NativeException("网关登陆sp时所用的密码   参数错误");
		}
		this.gwLinkPwd = gwLinkUser;
	}

	public Pointer getPointer() throws NativeException {
		if (this.pointer == null) {
			this.createPointer();
		}

		// 设置sp的设备代码
		this.pointer.setIntAt(START_SP_NODE_ID, this.spNodeId);
		// 设置网关的侦听IP地址
		this.pointer.setStringAt(START_GW_IP, this.gwIp);
		// 设置网关的侦听端口
		this.pointer.setIntAt(START_GW_PORT, this.gwPort);
		// 设置sp登陆网关时所用的用户名
		this.pointer.setStringAt(START_LOGIN_GW_USER, this.loginGwUser);
		// 设置sp登陆网关时所用的密码
		this.pointer.setStringAt(START_LOGIN_GW_PWD, this.loginGwPwd);
		// 设置sp的侦听IP地址
		this.pointer.setStringAt(START_APP_IP, this.appIp);
		// 设置sp的侦听端口
		this.pointer.setIntAt(START_APP_PORT, this.appPort);
		// 设置网关登陆sp时所用的用户名
		this.pointer.setStringAt(START_GW_LINK_USER, this.gwLinkUser);
		// 设置网关登陆sp时所用的密码
		this.pointer.setStringAt(START_GW_LINK_PWD, this.gwLinkPwd);

		return pointer;
	}

	public static void main(String[] args) throws Exception {
		JNative jNative = new JNative("UniAPIDll", "InitSgipAPI");

		// 编辑配置文件路径
		String initPath = "D:\\sgipapi.ini";
		Pointer pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(256));
		pointer.setStringAt(0, initPath);

		// 编辑登录信息
		LoginInfo loginInfo = new LoginInfo();

		loginInfo.setSpNodeId(300231211); // sp的设备代码
		loginInfo.setGwIp("10.130.72.181"); // 网关的侦听IP地址
		loginInfo.setGwPort(9001); // 网关的侦听端口
		loginInfo.setLoginGwUser("sp client1"); // sp登陆网关时所用的用户名
		loginInfo.setLoginGwPwd("0chaR456"); // sp登陆网关时所用的密码
		loginInfo.setAppIp("10.130.83.170"); // sp的侦听IP地址
		loginInfo.setAppPort(8002); // sp的侦听端口
		loginInfo.setGwLinkUser("sp server1"); // 网关登陆sp时所用的用户名
		loginInfo.setGwLinkPwd("11234Opt"); // 网关登陆sp时所用的密码

		// 设定返回值类型
		jNative.setRetVal(Type.INT);

		// 设定请求参数
		jNative.setParameter(0, pointer);
		jNative.setParameter(1, loginInfo.getPointer());

		// 调用方法
		jNative.invoke();
	}
}
