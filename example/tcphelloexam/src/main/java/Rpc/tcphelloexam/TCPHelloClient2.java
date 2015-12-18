package Rpc.tcphelloexam;

import java.io.IOException;
import java.net.URISyntaxException;

import com.ocean.rpc.client.RpcClient;
import com.ocean.rpc.common.RpcCallback1;
import com.ocean.rpc.common.SimpleMode;

public class TCPHelloClient2 {
    public interface IStub {
        @SimpleMode(true)
        String Hello(String name);
        @SimpleMode(true)
        void Hello(String name, RpcCallback1<String> callback);
    }
    public static void main(String[] args) throws IOException, URISyntaxException {
        RpcClient client = RpcClient.create("tcp://127.0.0.1:4321/");
        IStub stub = client.useService(IStub.class);
        stub.Hello("Async World", new RpcCallback1<String>() {
            public void handler(String result) {
                System.out.println(result);
            }
        });
        System.out.println(stub.Hello("World"));
        client.close();
    }
}
