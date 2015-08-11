package cn.newcapec.function.platform.biz;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.newcapec.framework.core.biz.BaseService;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.function.platform.model.User;

/**
 * 用户接口业务接口类
 *
 * @author chongfeigao
 *
 */
@SuppressWarnings("all")
public interface UserService extends BaseService<User> {

	/**
	 * 获取用户列表
	 *
	 * @param params
	 * @param orderby
	 * @return
	 */


	public Page queryUsers(Map<String, Object> params,
			LinkedHashMap<String, String> orderby);

	/**
	 * 获取用户信息
	 *
	 * @param name
	 * @return
	 */
	public User findUserByName(String name);

	/**
	 * 获取用户
	 *
	 * @param accountName
	 *            用户账户号
	 * @param password
	 *            密码
	 * @return
	 */
	public User findUser(String accountName, String password);

	/**
	 * 根据roleid获取用户
	 *
	 * @param roleid
	 * @return
	 */
	public Page queryUserByRoleId(String id,
			LinkedHashMap<String, String> orderby);

	/**
	 * @Description: 根据groupid 查询用户
	 * @param @param uuid
	 * @param @param object
	 * @param @return
	 * @return Object
	 * @throws
	 */
	public Page queryUserByRoleGroupId(String id,
			LinkedHashMap<String, String> orderby);

	/**
	 * @Title: updateUser
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param user 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void updateUser(User user);

	/**
	 * @param empid
	 * @Title: removeUser
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param userid
	 * @param @param customerunitcode 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void removeUser(String userid);
}
