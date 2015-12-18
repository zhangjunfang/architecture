package org.springframework.remoting.Rpc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.web.HttpRequestHandler;

import com.ocean.rpc.common.RpcFilter;
import com.ocean.rpc.io.RpcMode;
import com.ocean.rpc.server.HttpContext;
import com.ocean.rpc.server.RpcHttpService;
import com.ocean.rpc.server.RpcServiceEvent;


public class RpcHttpServiceExporter extends RemoteExporter implements InitializingBean, HttpRequestHandler {
    private RpcHttpService httpService;
    private boolean crossDomain = true;
    private boolean get = true;
    private boolean p3p = true;
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
        httpService = new RpcHttpService();
        httpService.add(service, cls);
        httpService.setCrossDomainEnabled(crossDomain);
        httpService.setGetEnabled(get);
        httpService.setP3pEnabled(p3p);
        httpService.setDebugEnabled(debug);
        httpService.setEvent(event);
        httpService.setMode(mode);
        httpService.setFilter(filter);
    }

    public void setCrossDomainEnabled(boolean value) {
        crossDomain = value;
    }

    public void setGetEnabled(boolean value) {
        get = value;
    }

    public void setP3pEnabled(boolean value) {
        p3p = value;
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

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        httpService.handle(new HttpContext(request, response, null, null));
    }

}
