/**
 * 
 */
package org.framework.core.spi.extension;

import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.framework.core.spi.extension.support.Handler;

import com.ocean.base.utils.ClassLoaderUtil;

/**
 * 描述：1.扫描jar中的文件
 * 
 * @author ocean 2015年8月17日 email：zhangjunfang0505@163.com
 */
public class IterateJar {
	
	private Handler handler;
	
	public IterateJar(Handler handler){
		super();
		this.handler=handler;
	}
	
	// 迭代jar中的资源
	//如果为空或者是以路径标识符开始 不执行  否则 迭代jar包中所有的资源
	public  void iterateJarFile(String indexPackage)
			throws Exception {
		try {
			// 按文件的形式去查找
			indexPackage = indexPackage.replaceAll("\\.", "/");
			Enumeration<URL> urls = ClassLoaderUtil.getURL(indexPackage);
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				if (url != null) {
					String protocol = url.getProtocol();
					if ("file".equalsIgnoreCase(protocol)) {// 本地自己可见的代码
						
					} else if ("jar".equalsIgnoreCase(protocol)) {// 引用第三方jar的代码
						JarURLConnection connection = (JarURLConnection) url
								.openConnection();
						getJarFile(connection);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void getJarFile(JarURLConnection connection) {
		try {
			JarFile jarFile = connection.getJarFile();
			Enumeration<JarEntry> enumeration = jarFile.entries();
			while (enumeration.hasMoreElements()) {
				JarEntry jarEntry = enumeration.nextElement();
				// 判断是目录还是文件
				if (!jarEntry.isDirectory()) {
					String jarName = jarEntry.getName();
					// 忽略目录以及文件过滤
					// 需要忽略META-INF/maven/开始的文件或者 是文件META-INF/MANIFEST.MF忽略
					// META-INF/license.txt,META-INF/notice.txt
					if (jarName.startsWith("META-INF/maven/")
							|| jarName.startsWith("META-INF/MANIFEST.MF")
							|| jarName.startsWith("META-INF/license.txt")
							|| jarName.startsWith("META-INF/notice.txt")) {
						continue;
					} else if (jarName.startsWith("META-INF/")) {// spi文件处理
						handler.handlerSPI(jarEntry);
					} else {
						String extensionName = jarName.substring(jarName
								.lastIndexOf("."));
						switch (extensionName) {
						case ".class":
							handler.handlerClass(jarEntry);
							break;
						case ".properties":
							handler.handleProperties(jarEntry);
							break;
						case ".java":
							handler.handlerJava(jarEntry);
							break;
						case ".xml":
							handler.handlerXML(jarEntry);
							break;
						default:
							handler.handlerOther(jarEntry);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
