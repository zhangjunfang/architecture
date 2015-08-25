/**
 * 
 */
package org.framework.core.spi.extension.support;

import java.nio.file.Path;
import java.util.jar.JarEntry;

/**
 * 描述：针对jar文件包含的资源：做出相应的处理
 * 
 * @author ocean 2015年8月20日 email：zhangjunfang0505@163.com
 */
public  abstract interface Handler {

	/**
	 * 
	 * @param jarEntry
	 */
	void handlerOther(JarEntry jarEntry);

	/**
	 * @param jarEntry
	 */
	void handlerSPI(JarEntry jarEntry);

	/**
	 * @param jarEntry
	 */
	void handleProperties(JarEntry jarEntry);

	/**
	 * @param jarEntry
	 */
	void handlerJava(JarEntry jarEntry);

	/**
	 * @param jarEntry
	 */
	void handlerXML(JarEntry jarEntry);

	/**
	 * @param jarEntry
	 */
	void handlerClass(JarEntry jarEntry);

	/**
	 * @param path
	 */
	void handlerLocalFile(Path path);
   
}
