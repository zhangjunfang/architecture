/**
 *
 */
package cn.newcapec.function.platform.biz;

import java.util.List;

import cn.newcapec.framework.core.biz.BaseService;
import cn.newcapec.function.platform.model.Menu;
import cn.newcapec.function.platform.model.RoleMenu;

/**
 * @author ocean date : 2014-4-17 上午11:01:42 email : zhangjunfang0505@163.com
 *         Copyright : newcapec zhengzhou
 */
public interface MenuService extends BaseService<Menu> {

	List<Menu> findMenubyRole(List<RoleMenu>  roleMenus);

}
