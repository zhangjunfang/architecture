/**
 *
 */
package cn.newcapec.function.platform.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.function.platform.biz.IDService;
import cn.newcapec.function.platform.dao.impl.IDDAO;
import cn.newcapec.function.platform.model.ID;

/**
 * @author ocean
 * @date : 2014-4-24 下午03:16:05
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
@Service()
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IDServiceImpl implements IDService {

	private static final long serialVersionUID = -4588580116982414464L;


	@Autowired
	private IDDAO  iddao;



	@Override
	public void saveOrUpdate(ID entity) {
		iddao.saveOrUpdate(entity);
	}

	@Override
	public ID get(String id) {
		return iddao.get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ID> findAll() {

		return iddao.findAll();
	}


}
