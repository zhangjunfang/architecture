/**
 *
 */
package cn.newcapec.function.platform.biz;

import java.io.Serializable;
import java.util.List;

import net.sf.json.JSONObject;
import cn.newcapec.framework.core.dao.db.Record;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.function.platform.model.BusinessAppredit;

/**
 * @author ocean date : 2014-4-24 下午03:14:00 email : zhangjunfang0505@163.com
 *         Copyright : newcapec zhengzhou
 */
public interface BusinessAppreditService extends Serializable {

	public Page<Record> getBusinessAppredit3();

	public List<Record> getBusinessAppredit2();

	public List<BusinessAppredit> getBusinessAppredit();

	public List<BusinessAppredit> getCusAppredit();

	/**
	 * @param string
	 * @return
	 */
	public BusinessAppredit queryCustomApp(String id);

	/**
	 * @param bean
	 */
	public void saveOrUpdate(BusinessAppredit bean);

	/**
	 * @param bean
	 */
	public void delete(String id);

	public BusinessAppredit get(String id);

	boolean SaveOrUpdate(JSONObject jsonObject);

}
