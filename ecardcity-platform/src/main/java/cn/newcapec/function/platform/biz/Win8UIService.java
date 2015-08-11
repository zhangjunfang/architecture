/**
 *
 */
package cn.newcapec.function.platform.biz;

import java.util.List;

import cn.newcapec.function.platform.model.BusinessAppredit;
import cn.newcapec.function.platform.model.Menu;
import cn.newcapec.function.platform.model.Module;
import cn.newcapec.function.platform.model.User;
import cn.newcapec.function.platform.tree.model.Tree;

/**
 *
 * @Description :
 * @author : ocean
 * @date : 2014-5-15 上午09:49:18
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
public interface Win8UIService {

	List<Module> findAllModule(User user);

	List<Menu> findAllMenu();

	List<Object> showTree();

	List<Tree> showCusTree();

	List<BusinessAppredit> findStandardBusinessAppredit();

	List<Menu> findAllMenu(User user);
}
