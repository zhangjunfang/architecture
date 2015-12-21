/**
 * 
 */
package org.springframework.ocean.remoting.hessian.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 
 * @author： ocean
 * 创建时间：2015年12月21日
 * mail：zhangjunfang0505@163.com
 * 描述：
 */
public class Utils {
    public static String getNetworkAddress() {
        Enumeration<NetworkInterface> netInterfaces;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = addresses.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                        return ip.getHostAddress();
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static String[] createParamSignature(Class<?>[] args) {
        if (args == null || args.length == 0) {
            return new String[] {};
        }
        String[] paramSig = new String[args.length];
        for (int x = 0; x < args.length; x++) {
            paramSig[x] = args[x].getName();
        }
        return paramSig;
    }

    public static Class<?>[] createParameterTypes(String[] args) throws ClassNotFoundException {
        if (args == null || args.length == 0) {
            return new Class<?>[] {};
        }
        Class<?>[] parameterTypes = new Class<?>[args.length];
        for (int x = 0; x < args.length; x++) {
            parameterTypes[x] = Class.forName(args[x]);
        }
        return parameterTypes;
    }

}
