/**
 * 
 */
package cn.newcapec.foundation.portal.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.newcapec.foundation.portal.dao.base.BaseStudentDao;
import cn.newcapec.framework.core.exception.BaseException;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.framework.core.utils.pagesUtils.SystemContext;

/**
 * 学生数据操作类
 * 
 * @author andy
 * 
 */
@Repository
@SuppressWarnings("all")
public class StudentDao extends BaseStudentDao {

	/**
	 * 获取学生信息
	 * 
	 * @param paramMap
	 * @return
	 */
	public Page querys(Map<String, Object> paramMap) {
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"select * from t_student t where 1=1");
		if (null != paramMap) {
			if (paramMap.containsKey("stuname")
					&& StringUtils.isNotBlank(paramMap.get("stuname")
							.toString())) {
				sql.append(" and t.name=:stuname");
				param.put("stuname", paramMap.get("stuname"));
			}
		}
		return this.sqlqueryForpage(sql.toString(), param, null);
	}

	/**
	 * 批量删去学生信息
	 * 
	 * @param ids
	 */
	public void delete(String[] ids) {
		if (ids != null && ids.length > 0) {
			Query query = getSession().createQuery(
					"delete from Student s where s.id in (:ids)");
			query.setParameterList("ids", ids);
			query.executeUpdate();
		}
	}

}
