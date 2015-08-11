/**
 * 
 */
package cn.newcapec.function.platform.biz.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.newcapec.framework.core.model.FileAttach;
import cn.newcapec.framework.core.rest.Msg;
import cn.newcapec.framework.core.utils.fileUtils.IoUtil;
import cn.newcapec.framework.core.utils.httpUtils.WebUtils;
import cn.newcapec.function.platform.biz.UploadFile;

/**
 * @author ocean date : 2014-4-8 上午10:18:14 email : zhangjunfang0505@163.com
 *         Copyright : newcapec zhengzhou
 */
@Service("uploadFile")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadFileImpl implements UploadFile, Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private UploadFileStrategy nameStrategy;

	public static  final 	String WORKDIR=System.getProperty("user.dir");
	
	
	@Override
	public Msg uploadFile(List<FileAttach> fileList, String sign) {

		Msg msg = new Msg();
		String newfilename = null;
		if(fileList != null && fileList.size() > 0) {
			FileAttach fileItem = fileList.get(0);
			if (sign.equals(fileItem.getProName())) {
				String fname = fileItem.getFileName();
				Long ll=fileItem.getFileSize();
				String  extName= WebUtils.getExt(fname);
				if (ll>Long.parseLong(nameStrategy.getMaxFileSizeInteger())||!validateFiletype(extName)) {
					msg.setMsg("上传文件不合法");
					msg.setSuccess(false);
					return msg;
				}
				if (isExist(fname)) {
					 backupsOldFile(fname);
				}
				StringBuffer  buffer=new  StringBuffer(100);
				buffer.append( this.nameStrategy.getStrategy().getFileName(
						fname));
				buffer.append(".");
				buffer.append(extName);
				newfilename=buffer.toString();
				buffer=null;
				StringBuffer  desc=new  StringBuffer(100);
				desc.append(WORKDIR);
				desc.append(File.separator);
				desc.append(nameStrategy.getNewFilePath());
				File descfile = new File( desc.toString() );
				desc=null;
				IoUtil.createFile(new File(descfile, newfilename),
						fileItem.getInputStream());
				msg.setMsg("保存成功！");
				msg.setSuccess(true);
			}
		}

		return msg;
	}

	@Override
	public Msg backupsOldFile(String filename) {
		String bf = this.nameStrategy.getOldFilePath();
		StringBuffer buffer=new StringBuffer(600);
		buffer.append(WORKDIR);
		buffer.append(File.separator);
		buffer.append(bf);
		buffer.append(File.separator);
		buffer.append(nameStrategy
				.getStrategy().backupsFileName(filename));
		buffer.append(".");
		buffer.append(WebUtils.getExt(filename));
		File descfile = new File(buffer.toString());
		buffer=null;
		StringBuffer newfile=new StringBuffer(600);
		newfile.append(WORKDIR);
		newfile.append(File.separator);
		newfile.append( nameStrategy.getNewFilePath());
		newfile.append(File.separator);
		newfile.append(filename);
		File newfilename = new File(newfile.toString());
		newfile=null;
		Msg msg = new Msg();
		try {
			descfile.createNewFile();
			IoUtil.createFile(descfile,new FileInputStream(newfilename));
			//FileUtils.copyFile(descfile, newfilename);
			newfilename.deleteOnExit();
			msg.setMsg("文件备份或者转移成功！");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("文件备份或者转移失败！");
			msg.setSuccess(false);
			//msg.setData(e);
			//e.printStackTrace();
		}
		return msg;
	}

	@Override
	public Msg uploadFile(List<FileAttach> fileList, String sign, URL url) {

		return null;
	}

	@Override
	public Msg backupsOldFile(URL url) {
		return null;
	}

	@Override
	public boolean isExist(String fileName) {
		File descfile = new File(System.getProperty("user.dir")
				+ File.separator + nameStrategy.getNewFilePath()
				+ File.separator + fileName);

		return descfile.exists();
	}

	@Override
	public boolean isExist(File file) {

		return file.exists();
	}

	
	@Override
	public boolean validateFiletype(String extName) {
		
		String allowType[] =nameStrategy.getFileType().split(",");
		
		for (int i = 0; i < allowType.length; i++) {
			if (extName.equalsIgnoreCase(allowType[i])) {
				return true;
			}
		}
		return false;
	}
	
	
	

}
