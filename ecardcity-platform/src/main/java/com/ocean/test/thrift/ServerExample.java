package com.ocean.test.thrift;

// import org.apache.thrift... etc.;
/**
*
* @Description :
* @author : ocean
* @date : 2014-5-13 上午10:33:15
* @email : zhangjunfang0505@163.com
* @Copyright : newcapec zhengzhou
*/
//public class ServerExample implements Runnable {
//        private static final int PORT = 7911;
//
//        @Override
//        public void run() {
//                try {
//                        TServerSocket serverTransport = new TServerSocket(PORT);
//                        ServiceExample.Processor processor = new ServiceExample.Processor(new ServiceExampleImpl());
//                        TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
//                        System.out.println("Starting server on port " + PORT);
//                        server.serve();
//                } catch (TTransportException e) {
//                        e.printStackTrace();
//                }
//        }
//
//        public static void main(String[] args) {
//                new Thread(new ServerExample()).run();
//        }
// }