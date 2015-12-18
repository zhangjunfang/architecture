package Rpc.hello.server;

import hprose.hello.server.Hello;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.websocket.EndpointConfig;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.ocean.rpc.server.RpcWebSocketService;

@ServerEndpoint("/wshello")
public class WebSocketServer {
    private RpcWebSocketService service = new RpcWebSocketService();
    public WebSocketServer() {
        service.add(new Hello());
    }
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        service.setConfig(config);
    }
    @OnMessage
    public void onMessage(ByteBuffer buf, Session session) throws IOException {
        service.handle(buf, session);
    }
    @OnError
    public void onError(Session session, Throwable error) {
        service.handleError(session, error);
    }
}
