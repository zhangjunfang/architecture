import java.time.Clock;


/**
 * 
 */

/**
 * 描述：
 * 
 * @author ocean 2015年8月18日 email：zhangjunfang0505@163.com
 */
public class B {
	public static void main(String[] args) throws Exception {
         
		long  start=System.currentTimeMillis();
		for (int i = 0; i < 1000000000; i++) {
			System.currentTimeMillis();
		}
		 System.out.println(System.currentTimeMillis()-start);
		long  middle=System.currentTimeMillis();
		Clock clock=Clock.systemUTC();
		for (int i = 0; i < 1000000000; i++) {
			clock.millis();
		}
		 System.out.println(System.currentTimeMillis()-middle);
		


	}
}
