/**
 * 
 */
package org.framework.core.spi.test.impl;

import java.nio.file.Path;
import java.util.jar.JarEntry;

import org.framework.core.spi.extension.support.Handler;

/**
 * 描述：
 * 
 * @author ocean
 * 2015年8月20日
 *  email：zhangjunfang0505@163.com
 */
public class MyHandler implements Handler {

	/* (non-Javadoc)
	 * @see org.framework.core.spi.extension.support.Handler#handlerOther(java.util.jar.JarEntry)
	 */
	@Override
	public void handlerOther(JarEntry jarEntry) {
		System.err.println("handlerOther::"+jarEntry.getName());

	}

	/* (non-Javadoc)
	 * @see org.framework.core.spi.extension.support.Handler#handlerSPI(java.util.jar.JarEntry)
	 */
	@Override
	public void handlerSPI(JarEntry jarEntry) {
		System.err.println("handlerSPI::"+jarEntry.getName());

	}

	/* (non-Javadoc)
	 * @see org.framework.core.spi.extension.support.Handler#handleProperties(java.util.jar.JarEntry)
	 */
	@Override
	public void handleProperties(JarEntry jarEntry) {
		System.err.println("handleProperties::"+jarEntry.getName());

	}

	/* (non-Javadoc)
	 * @see org.framework.core.spi.extension.support.Handler#handlerJava(java.util.jar.JarEntry)
	 */
	@Override
	public void handlerJava(JarEntry jarEntry) {
		System.err.println("handlerJava::"+jarEntry.getName());

	}

	/* (non-Javadoc)
	 * @see org.framework.core.spi.extension.support.Handler#handlerXML(java.util.jar.JarEntry)
	 */
	@Override
	public void handlerXML(JarEntry jarEntry) {
		System.err.println("handlerXML::"+jarEntry.getName());

	}

	/* (non-Javadoc)
	 * @see org.framework.core.spi.extension.support.Handler#handlerClass(java.util.jar.JarEntry)
	 */
	@Override
	public void handlerClass(JarEntry jarEntry) {
		System.err.println("handlerClass::"+jarEntry.getName());

	}

	/* (non-Javadoc)
	 * @see org.framework.core.spi.extension.support.Handler#handlerLocalFile(java.nio.file.Path)
	 */
	@Override
	public void handlerLocalFile(Path path) {
		System.err.println("handlerLocalFile::"+path.getFileName());
	}

}
