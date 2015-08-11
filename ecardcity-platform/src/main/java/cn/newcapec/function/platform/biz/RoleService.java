package cn.newcapec.function.platform.biz;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import cn.newcapec.framework.core.biz.BaseService;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.function.platform.model.Role;

/**
 * 
 * @Description: 角色业务接口
 * @author gaochongfei
 * @date 2014-4-11 下午04:43:25
 * @version V1.0
 */
@SuppressWarnings("all")
public interface RoleService extends BaseService<Role> {

	/**
	 * 获取角色列表返回的为list
	 * 
	 * @param params
	 * @param orderby
	 * @return
	 */

	public List<Map<String, Object>> queryRoles(Map<String, Object> params,
			LinkedHashMap<String, String> orderby);

	/**
	 * 获取角色信息
	 * 
	 * @param name
	 * @return
	 */
	public Role findRoleByName(String name);

	/**
	 * 删去角色
	 * 
	 * @param ids
	 *            id集合数组
	 */
	public void deleteRole(String[] ids);

	/**
	 * 
	 * 获取客户下的角色信息
	 * 
	 * @param roleid
	 * @return
	 */
	public Page queryRoleByCustomerCode(Map<String, Object> params,
			LinkedHashMap<String, String> orderby);

	/**
	 * 根据角色组 获取用户角色
	 * 
	 * @param usreid
	 * @return
	 */
	public List<Map<String, Object>> queryByRoleGroupidId(String usreid);

	/**
	 * @Description: 新增角色 及其关联的菜单权限
	 * @param @param role
	 * @param @param jsonObject   
	 * @return void    
	 * @throws
	 */ 
	public void addRole(Role role, Map<String, Object> params);

	/**
	 * @Description: 更新角色 及其关联的菜单权限
	 * @param @param role
	 * @param @param jsonObject   
	 * @return void    
	 * @throws
	 */ 
	public void editRole(Role role, Map<String, Object> params);

	/**
	 * @Description: TODO
	 * @param @param uuid
	 * @param @param object
	 * @param @return   
	 * @return Object    
	 * @throws
	 */ 
	public Object queryMenuByRoleId(String uuid, LinkedHashMap<String, String> orderby);

}
