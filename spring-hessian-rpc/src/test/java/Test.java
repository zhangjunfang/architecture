/**
 * 
 */
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ocean.remoting.hessian.test.TimeService;
/**
 * 
 * @author： ocean
 * 创建时间：2015年12月21日
 * mail：zhangjunfang0505@163.com
 * 描述：
 */
public class Test {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("application.xml");
        System.out.println(ac);

        //        ServiceExecutor executor = (ServiceExecutor) ac.getBean("exector");
        //        Request request = new Request();
        //        Object[] arguments = { "1234" };
        //        request.setArguments(arguments);
        //        request.setServiceName("abc");
        //        request.setRequestId(UUID.randomUUID().toString());
        //        System.out.println(executor.execute(request));

        TimeService t = (TimeService) ac.getBean("test");

        t.now();

    }
}
