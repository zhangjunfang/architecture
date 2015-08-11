/**
 * 
 */
package cn.newcapec.function.platform.biz.impl;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.newcapec.function.platform.biz.NameStrategy;

/**
 * @author ocean date : 2014-4-8 上午10:59:08 email : zhangjunfang0505@163.com
 *         Copyright : newcapec zhengzhou
 */
@Service("nameStrategy")
@Scope
public class NameStrategyImpl implements NameStrategy, Serializable {

	private static final long serialVersionUID = -7732380541191491583L;

	@Override
	public String getFileName(String filename) {

		return filename.substring(0,filename.lastIndexOf("."));
	}

	@Override
	public String backupsFileName(String filename) {

		return  filename.substring(0,filename.lastIndexOf("."))+"_"+UUID.randomUUID().toString().replaceAll("-", "");
	}

}
