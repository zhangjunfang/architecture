package com.ocean.test.thrift;
/**
*
* @Description :
* @author : ocean
* @date : 2014-5-13 上午10:33:15
* @email : zhangjunfang0505@163.com
* @Copyright : newcapec zhengzhou
*/
//public class ClientExample {
//        private static final int PORT = 7911;
//
//        public static void main(String[] args) {
//                try {
//                        TTransport transport = new TSocket("localhost", PORT);
//                        Protocol protocol = new TBinaryProtocol(transport);
//                        ServiceExample.Client client = new ServiceExample.Client(protocol);
//                        transport.open();
//                        BeanExample bean = client.getBean(1, "string");
//                        transport.close();
//                        System.out.println(bean.getStringObject());
//                } catch (TTransportException e) {
//                        e.printStackTrace();
//                } catch (TException e) {
//                        e.printStackTrace();
//                }
//        }
// }