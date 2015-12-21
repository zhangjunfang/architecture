package Rpc.exam.client;

import java.io.IOException;

import com.ocean.rpc.client.RpcHttpClient;

public class ClientExam5 {
    public static void main(String[] args) throws IOException {
        RpcHttpClient client = new RpcHttpClient();
        client.useService("http://localhost:8080/Methods");
        IExam1 exam1 = client.useService(IExam1.class, "ex1");
        IExam1 exam2 = client.useService(IExam1.class, "ex2");
        System.out.println(exam1.getId());
        System.out.println(exam2.getId());
    }
}
