package Rpc.exam.server;

import com.ocean.rpc.common.RpcMethods;
import com.ocean.rpc.server.RpcServlet;
/**
 * 
 * @author： ocean
 * 创建时间：2015年12月18日
 * mail：zhangjunfang0505@163.com
 * 描述：
 */
public class MyRpcServlet extends RpcServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6328509267163568323L;

	@Override
    protected void setGlobalMethods(RpcMethods methods) {
        Exam2 exam2 = new Exam2();
        methods.addMethod("getID", exam2);
    }
}