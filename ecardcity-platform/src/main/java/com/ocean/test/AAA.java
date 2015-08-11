/**
 * 
 */
package com.ocean.test;

import java.io.Serializable;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ocean
 * date : 2014-4-10 下午04:12:40
 * email : zhangjunfang0505@163.com
 * Copyright : newcapec zhengzhou
 */
@Service
@Component
//@Controller
@Repository
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AAA implements Serializable  {

	
	private static final long serialVersionUID = -8400074749709941288L;

	public AAA(){
		
		System.out.println("--------------------------------------------------------"+System.currentTimeMillis());
	}
	
	
}
