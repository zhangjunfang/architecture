/**
 * 
 */
package cn.newcapec.foundation.portal.biz.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.foundation.portal.biz.StudentService;
import cn.newcapec.foundation.portal.dao.StudentDao;
import cn.newcapec.foundation.portal.model.Student;
import cn.newcapec.framework.core.utils.pagesUtils.Page;

/**
 * 学生业务接口实现类
 * @author Administrator
 *
 */
@Service("studentService")
@Transactional
@SuppressWarnings("all")
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentDao studentDao;
	
	@Override
	public void removeUnused(String id) {
		studentDao.delete(id);
	}

	@Override
	@Transactional(readOnly=true,propagation = Propagation.NOT_SUPPORTED)
	public Student get(String id) {
		return studentDao.get(id);
	}

	@Override
	public void saveOrUpdate(Student paramT) {
		studentDao.saveOrUpdate(paramT);
	}

	@Override
	@Transactional(readOnly=true,propagation = Propagation.NOT_SUPPORTED)
	public Page querys(Map<String, Object> paramMap) {
		Page page=studentDao.querys(paramMap);
		return page;
	}

	@Override
	@Transactional(readOnly=true,propagation = Propagation.NOT_SUPPORTED)
	public Student findById(String id) {
		return studentDao.get(id);
	}

	@Override
	public void delete(String[] ids) {
		 studentDao.delete(ids);
	}

}
