/**
 *
 */
package cn.newcapec.function.platform.biz;

import java.util.List;

import cn.newcapec.framework.core.biz.BaseService;
import cn.newcapec.function.platform.model.RoleMenu;

/**
 * @author ocean
 * date : 2014-4-17 上午11:51:40
 * email : zhangjunfang0505@163.com
 * Copyright : newcapec zhengzhou
 */
public interface RoleMenService extends  BaseService<RoleMenu>  {

   List<RoleMenu>	findRoleMenuById(String roleId);

}
