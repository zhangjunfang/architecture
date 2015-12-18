package Rpc.tcphelloexam;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ocean.rpc.client.RpcTcpClient;

public class TCPHelloClient {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        System.out.println("START");
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[20];
        for (int i = 0; i < 20; i++) {
            threads[i] = new Thread() {
                @Override
                public void run() {
                    try {
                        RpcTcpClient client = new RpcTcpClient("tcp://localhost:4321");
                        for (int i = 0; i < 300000; i++) {
                            client.invoke("hello", new Object[] {"World"});
                        }
                        client.close();
                    } catch (IOException ex) {
                        Logger.getLogger(TCPHelloClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            threads[i].start();
        }
        for (int i = 0; i < 20; i++) {
            threads[i].join();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
//        start = System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++) {
//            client.invoke("hello", new Object[] {"World"}, new RpcCallback1() {
//                @Override
//                public void handler(Object result) {
//                }
//            });
//        }
//        end = System.currentTimeMillis();
//        System.out.println(end - start);
        System.out.println("END");
    }
}
