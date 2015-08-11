package com.ocean.test.thrift;

// imports... see last example for correct imports
/**
*
* @Description :
* @author : ocean
* @date : 2014-5-13 上午10:33:15
* @email : zhangjunfang0505@163.com
* @Copyright : newcapec zhengzhou
*/
//public class TestExample {
//        private static final int PORT = 7911;
//
//        @BeforeClass
//        @SuppressWarnings({ "static-access" })
//        public static void startServer() throws URISyntaxException, IOException {
//                // Start thrift server in a seperate thread
//                new Thread(new ServerExample()).start();
//                try {
//                        // wait for the server start up
//                        Thread.sleep(100);
//                } catch (InterruptedException e) {
//                        e.printStackTrace();
//                }
//        }
//
//        @Test
//        public void testExample() throws TTransportException, TException {
//                TTransport transport = new TSocket("localhost", PORT);
//                TProtocol protocol = new TBinaryProtocol(transport);
//                ServiceExample.Client client = new ServiceExample.Client(protocol);
//                transport.open();
//                BeanExample bean = client.getBean(1, "string");
//                transport.close();
//                Assert.assertEquals("OK", bean.getStringObject());
//        }
// }