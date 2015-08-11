/**
 *
 */
package cn.newcapec.function.platform.biz;

import java.io.Serializable;
import java.util.List;

import cn.newcapec.function.platform.model.ID;

/**
 * @author ocean date : 2014-4-24 下午03:14:00 email : zhangjunfang0505@163.com
 *         Copyright : newcapec zhengzhou
 */
public interface IDService extends Serializable {




	public void saveOrUpdate(ID bean);


	public ID get(String id);

	public List<ID> findAll();


}
