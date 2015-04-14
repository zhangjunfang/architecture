package com.transilink.framework.core.utils.fileUtils.fileloader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * 文件加载器的抽象类
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public abstract class FileLoader {
	/**
	 * 根路径.所有该FileLoader所能读取的文件都位于该路径之下.
	 */
	protected String root;
	/**
	 * 文件路径
	 */
	private String filePath;

	public FileLoader(String root) {
		if (root == null)
			root = "";
		else if (!(root.endsWith("/")))
			root = root + '/';
		this.root = root;
	}

	/**
	 * 设置要打开的文件。
	 *
	 * @param filepath
	 */
	protected abstract void doSetFile(String filePath);

	/**
	 * 设置要打开的文件。
	 *
	 * @param filepath
	 */
	public void setFile(String filePath) {
		this.filePath = filePath;
		doSetFile(filePath);
	}

	public abstract boolean exists();

	public abstract long getLastModified() throws IOException;

	/**
	 * 获取文件的输入流
	 *
	 * @return
	 * @throws IOException
	 */
	public abstract InputStream getInputStream() throws IOException;

	/**
	 * 获取文件的输出流.
	 *
	 * @return
	 * @throws IOException
	 */
	public abstract OutputStream getOutputStream() throws IOException;

	/**
	 * 取得根路径
	 *
	 * @return
	 */
	public String getRoot() {
		return this.root;
	}

	/**
	 * 文件位置的描述。
	 *
	 * @return
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * 取得文件的实际位置。
	 *
	 * @return
	 */
	public abstract String getRealPath();

	/**
	 * 取得文件的URL连接器。
	 *
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public abstract URL getURL() throws FileNotFoundException, IOException;
}