package com.ocean.test.thrift;
/**
*
* @Description :
* @author : ocean
* @date : 2014-5-13 上午10:33:15
* @email : zhangjunfang0505@163.com
* @Copyright : newcapec zhengzhou
*/
//public class TestExample02 {
//
//        private static final int PORT = 7911;
//        private static final int PORT_SSL = 443;
//        private static Scheme https;
//        private static BasicHttpParams params;
//        private static ThreadSafeClientConnManager cm;
//
//        @BeforeClass
//        @SuppressWarnings({ "static-access" })
//        public static void startServer() throws Exception {
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
//        @BeforeClass
//        @SuppressWarnings("deprecation")
//        public static void initConnectionManager() throws Exception {
//                File keystoreFile = new File(" ... /keystore.jks");
//
//                char[] password = "yourPassword".toCharArray();
//                String keystoreType = KeyStore.getDefaultType(); // "jks"
//
//                KeyStore keyStore = KeyStore.getInstance(keystoreType);
//                FileInputStream instream = new FileInputStream(keystoreFile);
//
//                try {
//                        keyStore.load(instream, password);
//                } finally {
//                        try {
//                                instream.close();
//                        } catch (Exception ignore) {
//                        }
//                }
//
//                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//                kmf.init(keyStore, password);
//
//                SSLContext sslContext = SSLContext.getInstance("TLS");
//                sslContext.init(kmf.getKeyManagers(), new TrustManager[] { new AnyCertTrustManager() }, null);
//
//                SSLSocketFactory ssf = new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//                https = new Scheme("https", PORT_SSL, ssf);
//
//                SchemeRegistry schemeRegistry = new SchemeRegistry();
//                schemeRegistry.register(https);
//
//                // Set up Thrift HTTP client connection parameters
//                params = new BasicHttpParams();
//                params.setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
//                params.setParameter("http.protocol.content-charset", "UTF-8");
//                // Disable Expect-Continue
//                params.setParameter("http.protocol.expect-continue", false);
//                // Enable staleness check
//                params.setParameter("http.connection.stalecheck", true);
//                HttpConnectionParams.setSoTimeout(params, 10000); // 10 secondes
//                HttpConnectionParams.setConnectionTimeout(params, 10000); // 10 secondes
//                ConnManagerParams.setMaxTotalConnections(params, 20);
//                ConnPerRouteBean connPerRoute = new ConnPerRouteBean(20);
//                ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRoute);
//
//                cm = new ThreadSafeClientConnManager(params, schemeRegistry);
//        }
//
//        private static class AnyCertTrustManager implements X509TrustManager {
//                @Override
//                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                }
//
//                @Override
//                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                }
//
//                @Override
//                public X509Certificate[] getAcceptedIssuers() {
//                        return null;
//                }
//        }
//
//        @Test
//        public void testExample() throws TException {
//                TTransport transport = new TSocket("localhost", PORT);
//                TProtocol protocol = new TBinaryProtocol(transport);
//                ServiceExample.Client client = new ServiceExample.Client(protocol);
//                transport.open();
//                BeanExample bean = client.getBean(1, "string");
//                transport.close();
//                Assert.assertEquals("OK", bean.getStringObject());
//        }
//
//        @Test
//        public void testServlet() throws TException {
//                String servletUrl = "http://localhost:8080/ThriftExample/TServletExample";
//
//                THttpClient thc = new THttpClient(servletUrl);
//                TProtocol loPFactory = new TCompactProtocol(thc);
//                ServiceExample.Client client = new ServiceExample.Client(loPFactory);
//
//                BeanExample bean = client.getBean(1, "string");
//                Assert.assertEquals("OK", bean.getStringObject());
//        }
//
//        @Test
//        public void testSecureServlet() throws TException {
//                String servletUrl = "https://localhost:8080/ThriftExample/TServletExample";
//
//                THttpClient thc = new THttpClient(servletUrl, new DefaultHttpClient(cm, params));
//                TProtocol loPFactory = new TCompactProtocol(thc);
//                ServiceExample.Client client = new ServiceExample.Client(loPFactory);
//
//                BeanExample bean = client.getBean(1, "string");
//                Assert.assertEquals("OK", bean.getStringObject());
//        }
// }