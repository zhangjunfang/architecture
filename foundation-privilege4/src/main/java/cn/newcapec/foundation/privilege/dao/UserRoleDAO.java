package cn.newcapec.foundation.privilege.dao;

import cn.newcapec.foundation.privilege.dao.base.BaseUserRoleDAO;
import cn.newcapec.foundation.privilege.model.UserRole;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户角色接口类
 * 
 * @author andy.li
 * 
 */
@SuppressWarnings("all")
@Repository("userRoleDAO")
public class UserRoleDAO extends BaseUserRoleDAO{
	
//	/**
//	 * 查询角色和用户的关联信息
//	 * @param params
//	 * @param orderby
//	 * @return
//	 */
//	public Page queryUserRoles(Map<String, Object> params,LinkedHashMap<String, String> orderby);
//	
//	/**
//	 * 删去用户关联的角色信息
//	 * @param userid
//	 */
//	public void deleteUserRolesByUserId(String[] userid);
	public Page<UserRole> queryUserRoles(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		/* 参数集合类 */
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer("select * from t_user_role t  where 1=1");
		if (params.containsKey("userid")
				&& StringUtils.isNotBlank(params.get("userid").toString())) {
			sb.append(" and t.user_id = ? ");
			param.add(params.get("userid"));
		}
		orderby.put("t.role_id", "desc");
		return this.sqlQueryForPage(sb.toString(), param.toArray(), orderby);
	}

	public void deleteUserRolesByUserId(String[] userids) {
		if(null!=userids && userids.length>0){
			StringBuffer sb = new StringBuffer("delete from t_user_role where  1=1");
			 StringBuffer userid = new StringBuffer();
			  for(int i = 0; i < userids.length; i ++) {
				  userid.append(userids[i]).append(",");
				  
			  }
			  userid.deleteCharAt(userid.length()-1); 
			sb.append(" and user_id in ("+userid.toString()+")");
			getSession().createSQLQuery(sb.toString()).executeUpdate();
		}
	}
}
