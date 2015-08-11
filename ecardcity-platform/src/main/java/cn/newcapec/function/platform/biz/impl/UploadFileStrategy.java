/**
 * 
 */
package cn.newcapec.function.platform.biz.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.newcapec.function.platform.biz.NameStrategy;

/**
 * @author ocean date : 2014-4-8 上午09:43:45 email : zhangjunfang0505@163.com
 *         Copyright : newcapec zhengzhou
 */
@Service
@Scope
public class UploadFileStrategy implements Serializable {

	private static final long serialVersionUID = 2933739835694467356L;
	/**
	 * 初次文件上传存放的地址
	 */
	@Value(value = "#{app.newFilePath}")
	private String newFilePath = "";
	/**
	 * 文件转移上传存放的地址
	 */
	@Value(value = "#{app.oldFilePath}")
	private String oldFilePath = "";
	/**
	 * 允许上传文件类型 多个文件类型使用英文逗号分隔
	 */
	@Value(value = "#{app.fileType}")
	private String fileType = null;
	/**
	 * 允许上传文件的大小，这里指的是单个文件的大小 单位 ：字节 进制：十进制
	 */
	@Value(value = "#{app.maxFileSizeInteger}")
	private String maxFileSizeInteger = "";
	/**
	 * 文件命名策略
	 */
	@Autowired
	private NameStrategy strategy = null;

	public String getNewFilePath() {
		return newFilePath;
	}

	public void setNewFilePath(String newFilePath) {
		this.newFilePath = newFilePath;
	}

	public String getOldFilePath() {
		return oldFilePath;
	}

	public void setOldFilePath(String oldFilePath) {
		this.oldFilePath = oldFilePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getMaxFileSizeInteger() {
		return maxFileSizeInteger;
	}

	public void setMaxFileSizeInteger(String maxFileSizeInteger) {
		this.maxFileSizeInteger = maxFileSizeInteger;
	}

	public NameStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(NameStrategy strategy) {
		this.strategy = strategy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileType == null) ? 0 : fileType.hashCode());
		result = prime
				* result
				+ ((maxFileSizeInteger == null) ? 0 : maxFileSizeInteger
						.hashCode());
		result = prime * result
				+ ((newFilePath == null) ? 0 : newFilePath.hashCode());
		result = prime * result
				+ ((oldFilePath == null) ? 0 : oldFilePath.hashCode());
		result = prime * result
				+ ((strategy == null) ? 0 : strategy.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UploadFileStrategy other = (UploadFileStrategy) obj;
		if (fileType == null) {
			if (other.fileType != null)
				return false;
		} else if (!fileType.equals(other.fileType))
			return false;
		if (maxFileSizeInteger == null) {
			if (other.maxFileSizeInteger != null)
				return false;
		} else if (!maxFileSizeInteger.equals(other.maxFileSizeInteger))
			return false;
		if (newFilePath == null) {
			if (other.newFilePath != null)
				return false;
		} else if (!newFilePath.equals(other.newFilePath))
			return false;
		if (oldFilePath == null) {
			if (other.oldFilePath != null)
				return false;
		} else if (!oldFilePath.equals(other.oldFilePath))
			return false;
		if (strategy == null) {
			if (other.strategy != null)
				return false;
		} else if (!strategy.equals(other.strategy))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(400);
		builder.append("UploadFileStrategy [newFilePath=");
		builder.append(newFilePath);
		builder.append(", oldFilePath=");
		builder.append(oldFilePath);
		builder.append(", fileType=");
		builder.append(fileType);
		builder.append(", maxFileSizeInteger=");
		builder.append(maxFileSizeInteger);
		builder.append(", strategy=");
		builder.append(strategy);
		builder.append("]");
		return builder.toString();
	}

}
