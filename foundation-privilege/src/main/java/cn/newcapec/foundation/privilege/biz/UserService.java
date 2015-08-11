package cn.newcapec.foundation.privilege.biz;

import cn.newcapec.foundation.privilege.model.User;
import cn.newcapec.framework.core.biz.BaseService;
import cn.newcapec.framework.core.utils.pagesUtils.Page;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 用户接口业务接口类
 * @author andy.li
 *
 */
@SuppressWarnings("all")
public interface UserService extends BaseService<User> {

	/**
	 * 获取用户列表
	 * @param params
	 * @param orderby
	 * @return
	 */

	public Page queryUsers(Map<String, Object> params, LinkedHashMap<String, String> orderby) ;
	
	/**
	 * 获取用户信息
	 * @param name
	 * @return
	 */
	public User findUserByName(String name);
	
	/**
	 * 添加用户级联信息
	 * @param user
	 * @param departmentids
	 */
	public void saveCascadeUser(User user,String[] departmentids);
	
	/**
	 * 删去用户级联信息
	 * @param user
	 * @param departmentids
	 */
	public void deleteCascadeUser(String[] userids);
	

	/**
	 * 给用户授权角色
	 * @param role
	 * @param userids
	 */
	public void setUsersAuthorize(String roleid , String[] userids);
	
	
	/**
	 * 给角色授权用户
	 * @param role
	 * @param userids
	 */
	public void setUsersAuthorize (String[] roleids,String userid);
	
	/**
	 * 获取用户
	 * @param accountName 用户账户号
	 * @param password  密码
	 * @return
	 */
	public User findUser(String accountName,String password);
	

	
}
