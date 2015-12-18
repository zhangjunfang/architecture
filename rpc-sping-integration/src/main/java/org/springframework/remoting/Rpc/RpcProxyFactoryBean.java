package org.springframework.remoting.Rpc;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;

import com.ocean.rpc.client.RpcClient;
import com.ocean.rpc.client.RpcHttpClient;
import com.ocean.rpc.client.RpcTcpClient;
import com.ocean.rpc.common.RpcFilter;
import com.ocean.rpc.io.RpcMode;

public class RpcProxyFactoryBean extends UrlBasedRemoteAccessor implements FactoryBean {

    private RpcClient client = null;
    private Exception exception = null;
    private boolean keepAlive = true;
    private int keepAliveTimeout = 300;
    private int timeout = -1;
    private String proxyHost = null;
    private int proxyPort = 80;
    private String proxyUser = null;
    private String proxyPass = null;
    private RpcMode mode = RpcMode.MemberMode;
    private RpcFilter filter = null;

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        try {
            client = RpcClient.create(getServiceUrl(), mode);
        }
        catch (Exception ex) {
            exception = ex;
        }
        if (client instanceof RpcHttpClient) {
            RpcHttpClient httpClient = (RpcHttpClient)client;
            httpClient.setKeepAlive(keepAlive);
            httpClient.setKeepAliveTimeout(keepAliveTimeout);
            httpClient.setTimeout(timeout);
            httpClient.setProxyHost(proxyHost);
            httpClient.setProxyPort(proxyPort);
            httpClient.setProxyUser(proxyUser);
            httpClient.setProxyPass(proxyPass);
        }
        if (client instanceof RpcTcpClient) {
            RpcTcpClient tcpClient = (RpcTcpClient)client;
            tcpClient.setTimeout((long)timeout);
        }
        client.setFilter(filter);
    }

// for RpcHttpClient
    public void setKeepAlive(boolean value) {
        keepAlive = value;
    }

    public void setKeepAliveTimeout(int value) {
        keepAliveTimeout = value;
    }

    public void setProxyHost(String value) {
        proxyHost = value;
    }

    public void setProxyPort(int value) {
        proxyPort = value;
    }

    public void setProxyUser(String value) {
        proxyUser = value;
    }

    public void setProxyPass(String value) {
        proxyPass = value;
    }

// for RpcClient
    public void setTimeout(int value) {
        timeout = value;
    }

    public void setMode(RpcMode value) {
        mode = value;
    }

    public void setFilter(RpcFilter filter) {
        this.filter = filter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object getObject() throws Exception {
        if (exception != null) {
            throw exception;
        }
        return client.useService(getServiceInterface());
    }

    @SuppressWarnings("rawtypes")
	@Override
    public Class getObjectType() {
        return getServiceInterface();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}