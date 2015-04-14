package com.transilink.framework.log.model;


/**
 * 操作员接口
 * @copyright: 郑州新开普电子股份有限公司
 * @jdk-version : jdk1.6.0_25
 */ 
public interface Operator extends java.io.Serializable {
	

	
	
	String getOperatorId();
	
	/**
	 *账号
	 * @return
	 */
	String getUsername();
	
	/**
	 * 真实姓名
	 * @return
	 */
	String getName();
	
	/**
	 * 加密密码
	 * @return
	 */
	String getPassword();
	
	/**
	 * 密码加密策略的salt
	 * @return
	 */
	String getPasswordSalt();

	/**
	 * 是否可用
	 * @return
	 */
	boolean isEnabled();
	
	/**
	 * 是否管理员
	 * @return
	 */
	boolean isAdministrator();
	
}
