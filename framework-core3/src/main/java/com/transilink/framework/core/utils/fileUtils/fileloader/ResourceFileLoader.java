package com.transilink.framework.core.utils.fileUtils.fileloader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * 类资源加载器
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class ResourceFileLoader extends FileLoader {
	private URL url;

	private String path;

	public ResourceFileLoader() {
		super(null);
	}

	public ResourceFileLoader(String root) {
		super(root);
	}

	protected void doSetFile(String filepath) {
		if (this.root == null)
			this.path = filepath;
		else
			this.path = this.root + filepath;
		this.url = null;
	}

	protected URL doGetURL() throws FileNotFoundException, IOException {
		if (this.url == null) {
			this.url = getClass().getResource(this.path);
			if (this.url == null) {
				for (ClassLoader localClassLoader = getClass().getClassLoader(); localClassLoader != null; localClassLoader = localClassLoader
						.getParent()) {
					this.url = localClassLoader.getResource(this.path);
					if (this.url != null)
						break;
				}
				if (this.url == null)
					this.url = ClassLoader.getSystemResource(this.path);
			}
			if (this.url == null)
				throw new FileNotFoundException(this.path);
		}
		return this.url;
	}

	/**
	 * 判断资源是否存在
	 */
	public boolean exists() {
		try {
			return (doGetURL() != null);
		} catch (Exception localException) {
		}
		return false;
	}

	/**
	 * 不支持操作
	 *
	 * @deprecated
	 */
	public long getLastModified() throws IOException {
		throw new UnsupportedOperationException();
	}

	public InputStream getInputStream() throws IOException {
		return doGetURL().openStream();
	}

	/**
	 * 不支持操作
	 *
	 * @deprecated
	 */
	public OutputStream getOutputStream() throws IOException {
		throw new UnsupportedOperationException();
	}

	public String getRealPath() {
		return this.path;
	}

	public URL getURL() throws FileNotFoundException, IOException {
		return doGetURL();
	}
}