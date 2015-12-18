package org.springframework.remoting.Rpc;

import java.io.IOException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.RemoteExporter;

import com.ocean.rpc.common.RpcFilter;
import com.ocean.rpc.io.RpcMode;
import com.ocean.rpc.server.RpcServiceEvent;
import com.ocean.rpc.server.RpcTcpServer;

public class RpcTcpServiceExporter extends RemoteExporter implements InitializingBean {
    private RpcTcpServer tcpServer;
    private String host;
    private int port = 0;
    private boolean debug = true;
    private RpcServiceEvent event = null;
    private RpcMode mode = RpcMode.MemberMode;
    private RpcFilter filter = null;

    @SuppressWarnings("rawtypes")
	@Override
    public void afterPropertiesSet() throws Exception {
        checkService();
        checkServiceInterface();
        Object service = getService();
        Class cls = getServiceInterface();
        tcpServer = new RpcTcpServer(host, port);
        tcpServer.add(service, cls);
        tcpServer.setDebugEnabled(debug);
        tcpServer.setEvent(event);
        tcpServer.setMode(mode);
        tcpServer.setFilter(filter);
    }

    public void setDebugEnabled(boolean value) {
        debug = value;
    }

    public void setEvent(RpcServiceEvent value) {
        event = value;
    }

    public void setMode(RpcMode value) {
        mode = value;
    }

    public void setFilter(RpcFilter value) {
        filter = value;
    }

    public void setHost(String value) {
        host = value;
    }

    public void setPort(int value) {
        port = value;
    }

    public void start() throws IOException {
        tcpServer.start();
    }

    public void stop() {
        tcpServer.stop();
    }
}
