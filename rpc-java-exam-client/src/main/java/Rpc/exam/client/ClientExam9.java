package Rpc.exam.client;

import java.io.IOException;

import com.ocean.rpc.client.RpcHttpClient;
import com.ocean.rpc.common.RpcCallback1;

public class ClientExam9 {
    public static void main(String[] args) throws IOException {
        RpcHttpClient client = new RpcHttpClient();
        client.useService("http://localhost:8080/Methods");
        client.invoke("ex1_getId", new RpcCallback1<String>() {
            public void handler(String result) {
                System.out.println(result);
            }
        }, String.class);
        client.invoke("ex2_getId", new RpcCallback1<String>() {
            public void handler(String result) {
                System.out.println(result);
            }
        }, String.class);
        client.close();
    }
}
