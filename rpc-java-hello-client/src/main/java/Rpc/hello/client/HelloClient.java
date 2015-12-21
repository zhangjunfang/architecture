package Rpc.hello.client;

import java.io.IOException;

import com.ocean.rpc.client.RpcHttpClient;

public class HelloClient {
    public static void main(String[] args) throws IOException {
        RpcHttpClient client = new RpcHttpClient();
        client.useService("http://localhost:8080/Hello");
        String result = (String) client.invoke("sayHello", new Object[] { "Rpc" });
        System.out.println(result);
        result = (String) client.invoke("sayHello", new Object[] { "中国" });
        System.out.println(result);
    }
}
