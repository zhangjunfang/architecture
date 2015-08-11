/**
 *
 */
package cn.newcapec.function.platform.biz;

import java.util.List;

import cn.newcapec.function.platform.model.BusinessAppredit;
import cn.newcapec.function.platform.model.Module;

/**
 * @Description :
 * @author : ocean
 * @date : 2014-4-28 上午10:12:01
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
public interface ModuleService {

	/**
	 * @param businessAppredits
	 */
	List<Module> findByBusinessAppredit(List<BusinessAppredit> businessAppredits);
	List<Module> findByBusinessAppredit(String  id);

}
