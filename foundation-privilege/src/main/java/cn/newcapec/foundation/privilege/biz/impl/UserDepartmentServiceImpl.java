package cn.newcapec.foundation.privilege.biz.impl;

import cn.newcapec.foundation.privilege.biz.UserDepartmentService;
import cn.newcapec.foundation.privilege.dao.UserDepartmentDAO;
import cn.newcapec.foundation.privilege.model.DepartmentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户部门业务接口实现类
 * 
 * @author andy.li
 * 
 */
@Service("userDepartmentService")
@Transactional
@SuppressWarnings("all")
public class UserDepartmentServiceImpl implements UserDepartmentService {

	@Autowired
	private UserDepartmentDAO userDepartmentDAO;

	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public DepartmentUser get(String id) {
		return userDepartmentDAO.get(id);
	}

	@Override
	public void removeUnused(String id) {
		userDepartmentDAO.delete(id);
	}

	@Override
	public void saveOrUpdate(DepartmentUser o) {
		userDepartmentDAO.saveOrUpdate(o);
	}

	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public boolean findDepartmentsbyIdsExist(String[] idss) {
		
		if(null==idss||idss.length==0){
			return false;
		}
		return userDepartmentDAO.findDepartmentsbyIdsExist(idss);
	}

}
