package Rpc.exam.client;

import java.io.IOException;

import com.ocean.rpc.client.RpcHttpClient;
import com.ocean.rpc.io.RpcClassManager;

public class ClientExam8 {
    public static void main(String[] args) throws IOException {
        RpcClassManager.register(User.class, "User");
        RpcHttpClient client = new RpcHttpClient();
        client.useService("http://localhost:8084/examserver/Methods");
        IExam2 exam2 = client.useService(IExam2.class, "ex2");
        User[] users = exam2.getUserList();
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