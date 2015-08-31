import org.framework.core.spi.extension.IterateJar;
import org.framework.core.spi.test.impl.MyHandler;


/**
 * 
 */

/**
 * 描述：
 * 
 * @author ocean 2015年8月18日 email：zhangjunfang0505@163.com
 */
public class A {
	public static void main(String[] args) throws Exception {

		new IterateJar(new MyHandler()).iterateJarFile("org/framework/core/spi");
		
	}
}
