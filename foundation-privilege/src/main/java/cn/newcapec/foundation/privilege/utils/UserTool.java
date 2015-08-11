package cn.newcapec.foundation.privilege.utils;

import cn.newcapec.foundation.privilege.model.User;
import cn.newcapec.framework.core.model.BaseAccount;

/**
 * 用户工具类
 * @author andy.li
 * 
 */
public class UserTool{


	
	public static BaseAccount Uer2LoginUser(User user) {
		BaseAccount loginUser =null;
		if(null!=user){
			loginUser=  new LoginAccount();
			loginUser.setOperatorId(user.getId());
			loginUser.setName(user.getAccountName());
			loginUser.setPassword(user.getPassword());
		}
		return loginUser;
	}

}
