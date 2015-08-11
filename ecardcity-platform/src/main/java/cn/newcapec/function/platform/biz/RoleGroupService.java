package cn.newcapec.function.platform.biz;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.newcapec.framework.core.biz.BaseService;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.function.platform.model.RoleGroup;

/**
 * 角色组业务接口类
 *
 * @Description: TODO
 * @author gaochongfei
 * @date 2014-4-8 上午10:07:20
 * @version V1.0
 */
@SuppressWarnings("all")
public interface RoleGroupService extends BaseService<RoleGroup> {

	/**
	 * 删去角色
	 *
	 * @param ids
	 *            id集合数组
	 */
	public void deleteRole(String[] ids);

	/**
	 * 获取客户下的角色组信息
	 *
	 * @param roleid
	 * @return
	 */
	public Page queryRoleGroupByCustomerCode(Map<String, Object> params,
			LinkedHashMap<String, String> orderby);

	/**
	 * @Description: 获取客户下的角色组信息
	 * @param @param jsonObject
	 * @param @param object
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>> queryRoleGroups(
			Map<String, Object> params, LinkedHashMap<String, String> orderby);

}
