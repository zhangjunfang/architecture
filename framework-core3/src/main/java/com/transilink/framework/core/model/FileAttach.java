package com.transilink.framework.core.model;

import java.io.InputStream;
import java.io.Serializable;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class FileAttach implements Serializable {
	private static final long serialVersionUID = -6048829856792388813L;

	private String fileName;
	private String fileType;
	private long fileSize;
	private String proName;
	private InputStream inputStream;

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getProName() {
		return this.proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public InputStream getInputStream() {
		return this.inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}