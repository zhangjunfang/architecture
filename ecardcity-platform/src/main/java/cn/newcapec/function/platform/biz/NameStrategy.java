/**
 * 
 */
package cn.newcapec.function.platform.biz;

import java.io.Serializable;

/**
 * @author ocean date : 2014-4-8 上午09:59:53 email : zhangjunfang0505@163.com
 *         Copyright : newcapec zhengzhou
 */
public interface NameStrategy extends Serializable {
	/**
	 * 上传文件命名策略
	 * 
	 * @return 文件名字
	 */
	String getFileName(String filename);

	/**
	 * 备份文件命名策略
	 * 
	 * @return 文件名字
	 */
	String backupsFileName(String filename);
}
