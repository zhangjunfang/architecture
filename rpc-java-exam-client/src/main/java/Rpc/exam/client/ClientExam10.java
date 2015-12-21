package Rpc.exam.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ocean.rpc.client.RpcHttpClient;
import com.ocean.rpc.common.ByRef;
import com.ocean.rpc.common.MethodName;
import com.ocean.rpc.common.RpcCallback;
import com.ocean.rpc.common.RpcCallback1;
import com.ocean.rpc.io.RpcClassManager;

interface ITest {
	void swapKeyAndValue(Map<String, String> strmap,
			RpcCallback<Map<String, String>> callback);

	@MethodName("swapKeyAndValue")
	@ByRef(true)
	void swap(Map<String, String> strmap,
			RpcCallback<Map<String, String>> callback);

	void getUserList(RpcCallback1<List<User>> callback);

	@MethodName("getUserList")
	void getUserArray(RpcCallback1<User[]> callback);

	Future<List<User>> getUserList();
}

public class ClientExam10 {
	public static void main(String[] args) throws IOException {
		RpcClassManager.register(User.class, "User");
		RpcHttpClient client = new RpcHttpClient();
		client.useService("http://localhost:8080/Methods");
		final ITest test = client.useService(ITest.class, "ex2");
		Map<String, String> map = new HashMap<String, String>();
		map.put("January", "Jan");
		map.put("February", "Feb");
		map.put("March", "Mar");
		map.put("April", "Apr");
		map.put("May", "May");
		map.put("June", "Jun");
		map.put("July", "Jul");
		map.put("August", "Aug");
		map.put("September", "Sep");
		map.put("October", "Oct");
		map.put("November", "Nov");
		map.put("December", "Dec");
		test.swapKeyAndValue(map, new RpcCallback<Map<String, String>>() {
			@SuppressWarnings("unchecked")
			public void handler(Map<String, String> result, Object[] args) {
				synchronized (test) {
					Map<String, String> map = (Map<String, String>) args[0];
					Map<String, String> map2 = result;
					System.out.println("byVal:");
					System.out.println(map);
					System.out.println(map2);
					System.out.println();
				}
			}
		});
		test.swap(map, new RpcCallback<Map<String, String>>() {
			@SuppressWarnings("unchecked")
			public void handler(Map<String, String> result, Object[] args) {
				synchronized (test) {
					Map<String, String> map = (Map<String, String>) args[0];
					Map<String, String> map2 = result;
					System.out.println("byRef:");
					System.out.println(map);
					System.out.println(map2);
					System.out.println();
				}
			}
		});
		test.getUserList(new RpcCallback1<List<User>>() {
			public void handler(List<User> users) {
				synchronized (test) {
					System.out.println("getUserList:");
					for (User user : users) {
						System.out.printf("name: %s, ", user.getName());
						System.out.printf("age: %d, ", user.getAge());
						System.out.printf("sex: %s, ", user.getSex());
						System.out.printf("birthday: %s, ", user.getBirthday());
						System.out.printf("married: %s.", user.isMarried());
						System.out.println();
					}
					System.out.println();
				}
			}
		});
		test.getUserArray(new RpcCallback1<User[]>() {
			public void handler(User[] users) {
				synchronized (test) {
					System.out.println("getUserArray:");
					for (User user : users) {
						System.out.printf("name: %s, ", user.getName());
						System.out.printf("age: %d, ", user.getAge());
						System.out.printf("sex: %s, ", user.getSex());
						System.out.printf("birthday: %s, ", user.getBirthday());
						System.out.printf("married: %s.", user.isMarried());
						System.out.println();
					}
					System.out.println();
				}
			}
		});
		Future<List<User>> result = test.getUserList();
		List<User> users;
		try {
			users = result.get();
			synchronized (test) {
				System.out.println("Future<List<User>> getUserList:");
				for (User user : users) {
					System.out.printf("name: %s, ", user.getName());
					System.out.printf("age: %d, ", user.getAge());
					System.out.printf("sex: %s, ", user.getSex());
					System.out.printf("birthday: %s, ", user.getBirthday());
					System.out.printf("married: %s.", user.isMarried());
					System.out.println();
				}
				System.out.println();
			}
		} catch (Exception ex) {
			Logger.getLogger(ClientExam10.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		client.close();
	}
}
