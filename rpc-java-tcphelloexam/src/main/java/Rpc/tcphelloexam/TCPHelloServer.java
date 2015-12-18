package Rpc.tcphelloexam;

import java.io.IOException;
import java.net.URISyntaxException;

import com.ocean.rpc.server.RpcTcpServer;

public class TCPHelloServer {
    public static String hello(String name) {
        return "Hello " + name + "!";
    }
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        RpcTcpServer server = new RpcTcpServer("tcp://localhost:4321");
        server.add("hello", TCPHelloServer.class);
        //server.setEnabledThreadPool(true);
        server.start();
        System.out.println("START");
        System.in.read();
        server.stop();
        System.out.println("STOP");
    }
}
