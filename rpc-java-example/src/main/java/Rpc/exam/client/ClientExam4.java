package Rpc.exam.client;

import java.io.IOException;
import java.util.List;

import com.ocean.rpc.client.RpcHttpClient;
import com.ocean.rpc.io.RpcClassManager;

public class ClientExam4 {
    @SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
        RpcClassManager.register(User.class, "User");
        RpcHttpClient client = new RpcHttpClient();
        client.useService("http://localhost:8084/examserver/Methods");
        List<User> userList = client.invoke("ex2_getUserList", List.class);
        for (User user : userList) {
            System.out.printf("name: %s, ", user.getName());
            System.out.printf("age: %d, ", user.getAge());
            System.out.printf("sex: %s, ", user.getSex());
            System.out.printf("birthday: %s, ", user.getBirthday());
            System.out.printf("married: %s.", user.isMarried());
            System.out.println();
        }
        System.out.println();
        User[] users = client.invoke("ex2_getUserList", User[].class);
        for (User user : users) {
            System.out.printf("name: %s, ", user.getName());
            System.out.printf("age: %d, ", user.getAge());
            System.out.printf("sex: %s, ", user.getSex());
            System.out.printf("birthday: %s, ", user.getBirthday());
            System.out.printf("married: %s.", user.isMarried());
            System.out.println();
        }
    }
}