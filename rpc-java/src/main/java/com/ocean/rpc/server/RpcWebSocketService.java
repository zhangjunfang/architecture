package com.ocean.rpc.server;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import com.ocean.rpc.common.RpcContext;
import com.ocean.rpc.common.RpcMethods;
import com.ocean.rpc.io.ByteBufferStream;

/**
 * 
 * @author： ocean 创建时间：2015年12月18日 mail：zhangjunfang0505@163.com 描述：
 */
public class RpcWebSocketService extends RpcService {
	private final static ThreadLocal<WebSocketContext> currentContext = new ThreadLocal<WebSocketContext>();
	private EndpointConfig config = null;

	public static WebSocketContext getCurrentContext() {
		return currentContext.get();
	}

	@Override
	public RpcMethods getGlobalMethods() {
		if (globalMethods == null) {
			globalMethods = new RpcWebSocketMethods();
		}
		return globalMethods;
	}

	@Override
	public void setGlobalMethods(RpcMethods methods) {
		if (methods instanceof RpcWebSocketMethods) {
			this.globalMethods = methods;
		} else {
			throw new ClassCastException(
					"methods must be a HproseWebSocketMethods instance");
		}
	}

	@Override
	protected Object[] fixArguments(Type[] argumentTypes, Object[] arguments,
			RpcContext context) {
		int count = arguments.length;
		WebSocketContext wsContext = (WebSocketContext) context;
		if (argumentTypes.length != count) {
			Object[] args = new Object[argumentTypes.length];
			System.arraycopy(arguments, 0, args, 0, count);
			Class<?> argType = (Class<?>) argumentTypes[count];
			if (argType.equals(RpcContext.class)) {
				args[count] = context;
			} else if (argType.equals(WebSocketContext.class)) {
				args[count] = wsContext;
			} else if (argType.equals(EndpointConfig.class)) {
				args[count] = wsContext.getConfig();
			} else if (argType.equals(Session.class)) {
				args[count] = wsContext.getSession();
			}
			return args;
		}
		return arguments;
	}

	public void setConfig(EndpointConfig config) {
		this.config = config;
	}

	public void handle(ByteBuffer buf, Session session) throws IOException {
		WebSocketContext context = new WebSocketContext(session, config);
		ByteBufferStream istream = null;
		ByteBufferStream ostream = null;
		try {
			int id = buf.getInt();
			currentContext.set(context);
			istream = new ByteBufferStream(buf.slice());
			ostream = handle(istream, context);
			buf = ByteBuffer.allocate(ostream.available() + 4);
			buf.putInt(id);
			buf.put(ostream.buffer);
			buf.flip();
			session.getBasicRemote().sendBinary(buf);
		} finally {
			currentContext.remove();
			if (istream != null) {
				istream.close();
			}
			if (ostream != null) {
				ostream.close();
			}
		}
	}

	public void handleError(Session session, Throwable error) {
		WebSocketContext context = new WebSocketContext(session, config);
		fireErrorEvent(error, context);
	}
}
