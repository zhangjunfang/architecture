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
public class Response implements Serializable {
   
    private static final long serialVersionUID = 1119529854059097418L;
    private String            localAddr;
    private Request           request;
    private Object            responseData;
    private long              time;

   
    public Request getRequest() {
        return request;
    }

  
    public void setRequest(Request request) {
        this.request = request;
    }

    
    public Object getResponseData() {
        return responseData;
    }

   
    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }

   
    public long getTime() {
        return time;
    }

    
    public void setTime(long time) {
        this.time = time;
    }

    public String getLocalAddr() {
        return localAddr;
    }

  
    public void setLocalAddr(String localAddr) {
        this.localAddr = localAddr;
    }

}
