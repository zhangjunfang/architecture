package Rpc.exam.client;

import java.io.IOException;

import com.ocean.rpc.client.RpcHttpClient;

public class ClientExam1 {
    public static void main(String[] args) throws IOException {
        RpcHttpClient client = new RpcHttpClient();
        client.useService("http://localhost:8080/Methods");
        System.out.println(client.invoke("ex1_getId"));
        System.out.println(client.invoke("ex2_getId"));
    }
}
