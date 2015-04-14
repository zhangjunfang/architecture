package com.transilink.framework.core.utils.fileUtils.fileloader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * 默认文件加载器
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class DefaultFileLoader extends FileLoader {
	public static final String CLASSPATH_PREFIX = "classpath:";

	public static final String FILE_PREFIX1 = "file:";

	public static final String FILE_PREFIX2 = "file://";

	private FileLoader resourceFileLoader;

	private FileLoader pathFileLoader;
	/**
	 * 当前加载器
	 */
	private FileLoader currFileLoader;

	private FileLoader getResourceFileLoader() {
		if (this.resourceFileLoader == null)
			this.resourceFileLoader = new ResourceFileLoader();
		return this.resourceFileLoader;
	}

	private FileLoader getPathFileLoader() {
		if (this.pathFileLoader == null)
			this.pathFileLoader = new PathFileLoader();
		return this.pathFileLoader;
	}

	public DefaultFileLoader() {
		super(null);
	}

	public DefaultFileLoader(String paramString) {
		super(paramString);
	}

	protected void doSetFile(String filePath) {
		String url;
		if (this.root != null)
			filePath = this.root + filePath;

		if (filePath.startsWith("classpath:")) {
			this.currFileLoader = getResourceFileLoader();
			url = filePath.substring("classpath:".length());
		} else if (filePath.startsWith("file:")) {
			this.currFileLoader = getPathFileLoader();
			url = filePath.substring("file:".length());
		} else if (filePath.startsWith("file://")) {
			this.currFileLoader = getPathFileLoader();
			url = filePath.substring("file://".length());
		} else {
			this.currFileLoader = getResourceFileLoader();
			url = filePath;
		}
		this.currFileLoader.setFile(url);
	}

	public boolean exists() {
		return this.currFileLoader.exists();
	}

	public long getLastModified() throws IOException {
		return this.currFileLoader.getLastModified();
	}

	public InputStream getInputStream() throws IOException {
		return this.currFileLoader.getInputStream();
	}

	public OutputStream getOutputStream() throws IOException {
		return this.currFileLoader.getOutputStream();
	}

	public String getRealPath() {
		return this.currFileLoader.getRealPath();
	}

	public URL getURL() throws FileNotFoundException, IOException {
		return this.currFileLoader.getURL();
	}
}