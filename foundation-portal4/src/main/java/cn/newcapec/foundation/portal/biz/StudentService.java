/**
 * 
 */
package cn.newcapec.foundation.portal.biz;

import java.util.Map;

import cn.newcapec.foundation.portal.model.Student;
import cn.newcapec.framework.core.biz.BaseService;
import cn.newcapec.framework.core.utils.pagesUtils.Page;

/**
 * 学生业务接口类
 * @author andy
 *
 */
public interface StudentService extends BaseService<Student> {

	/**
	 * 获取查询列表
	 * @param paramMap
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	public Page querys(Map<String, Object> paramMap);

	/**
	 * 获取学生实体类
	 * @param id
	 * @return
	 */
	public Student findById(String id);
	
	/**
	 * 删去学生
	 * @param ids
	 */
	public void delete(String[] ids);
}
