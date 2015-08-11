/**  
 * @Title: LegalPersonService.java
 * @Package cn.newcapec.function.platform.biz
 * @Description: TODO(用一句话描述该文件做什么)
 * @author gaochongfei 
 * @date 2014-3-31 上午10:24:18
 * @version V1.0  
 */
package cn.newcapec.function.platform.biz;

import cn.newcapec.framework.core.biz.BaseService;
import cn.newcapec.function.platform.model.LegalPerson;

/**
 * @author chongfeigao
 * 
 */
public interface LegalPersonService extends BaseService<LegalPerson> {
	/**
	 * 获取用户列表
	 * 
	 * @param params
	 * @param orderby
	 * @return
	 */

	public LegalPerson queryByCustomerunitcode(String code);

}
