/**
 * 
 */
package cn.newcapec.function.platform.biz;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

import cn.newcapec.framework.core.model.FileAttach;
import cn.newcapec.framework.core.rest.Msg;

/**
 * @author ocean date : 2014-4-8 上午10:07:07 email : zhangjunfang0505@163.com
 *         Copyright : newcapec zhengzhou
 */
public interface UploadFile extends Serializable {
	/**
	 * 上传文件并存储
	 * 
	 * @param nameStrategy
	 * @param inputStream
	 * @return
	 */
	Msg uploadFile(List<FileAttach> fileList, String sign);

	/**
	 * 上传文件并存储
	 * 
	 * @param nameStrategy
	 * @param inputStream
	 * @return
	 */
	Msg uploadFile(List<FileAttach> fileList, String sign, URL url);

	/**
	 * 备份文件并存储
	 * 
	 * @return
	 */
	Msg backupsOldFile(String fileName);

	/**
	 * 备份文件并存储
	 * 
	 * @return
	 */
	Msg backupsOldFile(URL url);
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	boolean isExist(String fileName);
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	boolean isExist(File file);
	
	
	boolean validateFiletype(String extName);
}
