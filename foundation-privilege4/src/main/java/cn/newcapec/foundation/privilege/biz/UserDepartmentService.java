package cn.newcapec.foundation.privilege.biz;

import cn.newcapec.foundation.privilege.model.DepartmentUser;
import cn.newcapec.framework.core.biz.BaseService;


/**
 * 用户部门接口业务接口类
 * @author andy.li
 *
 */
@SuppressWarnings("all")
public interface UserDepartmentService extends BaseService<DepartmentUser> {

/**
 * 查询当前部门下 是否有 人员
 * 如果存在返回 true 否则 返回 false
 * **/
 public boolean findDepartmentsbyIdsExist(String[] idss);

	
	
}
