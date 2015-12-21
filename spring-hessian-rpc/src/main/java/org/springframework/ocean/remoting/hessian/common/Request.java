/**
 * 
 */
package org.springframework.ocean.remoting.hessian.common;

import java.io.Serializable;

/**
 * 
 * @author： ocean
 * 创建时间：2015年12月21日
 * mail：zhangjunfang0505@163.com
 * 描述：
 */
public class Request implements Serializable {
    /**  */
    private static final long serialVersionUID = 85739713234093800L;
    private String            requestId;
    private String            url;
    private String            localAddr;
    private String            serviceName;
    private String            methodName;
    private String[]          methodArgSigs;
    private Object[]          methodArgs;
    private String            interfaceName;
    private long              time;

  
    public long getTime() {
        return time;
    }

  
    public void setTime(long time) {
        this.time = time;
    }

  
    public String getRequestId() {
        return requestId;
    }

   
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

  
    public String getServiceName() {
        return serviceName;
    }

   
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

   
    public String getUrl() {
        return url;
    }

   
    public void setUrl(String url) {
        this.url = url;
    }

   
    public String getInterfaceName() {
        return interfaceName;
    }

   
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

   
    public String getLocalAddr() {
        return localAddr;
    }

   
    public void setLocalAddr(String localAddr) {
        this.localAddr = localAddr;
    }

   
    public String[] getMethodArgSigs() {
        return methodArgSigs;
    }

   
    public void setMethodArgSigs(String[] methodArgSigs) {
        this.methodArgSigs = methodArgSigs;
    }

    public Object[] getMethodArgs() {
        return methodArgs;
    }

   
    public void setMethodArgs(Object[] methodArgs) {
        this.methodArgs = methodArgs;
    }

}
