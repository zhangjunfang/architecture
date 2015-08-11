package cn.newcapec.foundation.privilege.biz;

import cn.newcapec.foundation.privilege.model.Resource;
import cn.newcapec.framework.core.biz.BaseService;
import cn.newcapec.framework.core.utils.pagesUtils.Page;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源业务接口类
 *
 * @author andy.li
 *
 */
@SuppressWarnings("all")
public interface ResourceService extends BaseService<Resource> {

	/**
	 * 获取资源列表
	 *
	 * @param params
	 * @param orderby
	 * @return
	 */

	public Page queryResource(Map<String, Object> params, LinkedHashMap<String, String> orderby);

	/**
	 * 查询资源单条记录By主键
	 *
	 * @param id
	 * @return
	 */
	public Resource queryResourceById(String id);

	/**
	 * 获得角色下的所有功能
	 * @param menuid
	 * @return
	 */
	public List<Map<String,Object >>  queryResourcesByRoleId(String role);

	/**
	 * 删除角色
	 * @param ids id集合数组
	 */
	public void deleteResource(String[] ids);
	
	/**
	 * 获取用户的所有资源信息
	 * @param userid
	 * @return
	 */
	public List queryResorucesByUserid(String userid);

	
	/**
	 * 保存资源列表
	 * @param resources
	 */
	public void saves(List<Resource> resources);

}
