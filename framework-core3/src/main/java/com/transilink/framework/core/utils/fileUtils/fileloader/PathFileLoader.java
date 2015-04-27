package com.transilink.framework.core.utils.fileUtils.fileloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 本地文件读取器.
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class PathFileLoader extends FileLoader {
	private File file;

	private String filePath;

	public PathFileLoader() {
		super(null);
	}

	public PathFileLoader(String root) {
		super(root);
	}

	protected void doSetFile(String filepath) {
		if (this.root == null)
			this.filePath = filepath;
		else
			this.filePath = this.root + filepath;
		this.file = new File(this.filePath);
	}

	/**
	 * 判断文件是否存在
	 */
	public boolean exists() {
		return this.file.exists();
	}

	/**
	 * 获取最近修改时间
	 */
	public long getLastModified() throws IOException {
		return this.file.lastModified();
	}

	/**
	 * 获取输入流
	 */
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(this.file);
	}

	/**
	 * 获取输出流
	 */
	public OutputStream getOutputStream() throws IOException {
		return new FileOutputStream(this.file);
	}

	/**
	 * 获取文件路径
	 */
	public String getRealPath() {
		return this.filePath;
	}

	/**
	 * 获取资源url路径
	 */
	public URL getURL() {
		try {
			return new URL("file:///" + this.filePath);
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}