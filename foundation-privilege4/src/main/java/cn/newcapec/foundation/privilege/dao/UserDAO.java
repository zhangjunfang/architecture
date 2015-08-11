package cn.newcapec.foundation.privilege.dao;

import cn.newcapec.foundation.privilege.dao.base.BaseUserDAO;
import cn.newcapec.foundation.privilege.model.User;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户接口类
 * 
 * @author andy.li
 * 
 */
@SuppressWarnings("all")
@Repository("userDAO")
public class UserDAO extends  BaseUserDAO {
	
	public Page queryUsers(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		/* 参数集合类 */
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(
				"select * from t_user u  "
						+ " where 1=1");
		if (params.containsKey("name")
				&& StringUtils.isNotBlank(params.get("name").toString())) {
			sb.append(" and u.username like ?");
			param.add("%"+params.get("name")+"%");
		}
		if(params.containsKey("userid") && StringUtils.isNotBlank(params.get("userid").toString())){
			sb.append(" and u.accountName = ? ");
			param.add(params.get("userid"));
		}
		if (params.containsKey("status")
				&& StringUtils.isNotBlank(params.get("status").toString())) {
			sb.append(" and u.status=? ");
			param.add(params.get("status"));
		}
		return this.sqlQueryForPage(sb.toString(), param.toArray(), orderby);
	}

	public User findUserByName(String name) {

		String hql = "select u from User u where  u.accountName =?";
		User user = (User) super.findForObject(hql.toString(),
				new Object[] { name });
		return user;


	}

	public void deleteUser(String[] userids) {
		if (null != userids && userids.length > 0) {
			StringBuffer sb = new StringBuffer("delete from t_user where  1=1");
			StringBuffer userid = new StringBuffer();
			for (int i = 0; i < userids.length; i++) {
				userid.append(userids[i]).append(",");

			}
			userid.deleteCharAt(userid.length() - 1);
			sb.append(" and id in (" + userid.toString() + ")");
			getSession().createSQLQuery(sb.toString()).executeUpdate();
		}
	}

	public User findUser(String accountName, String password) {
		String sql="select u.* from t_user u  where  u.accountName=? and u.password=?";
		User user = (User) this.findForObject(sql,new Object[] { accountName, password});
		return user;
	}

}
