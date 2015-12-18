package com.ocean.rpc.server;

import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import com.ocean.rpc.common.RpcContext;

/**
 * 
 * @author： ocean 创建时间：2015年12月18日 mail：zhangjunfang0505@163.com 描述：
 */
public class WebSocketContext extends RpcContext {
	private final Session session;
	private final EndpointConfig config;

	public WebSocketContext(Session session, EndpointConfig config) {
		this.session = session;
		this.config = config;
	}

	public Session getSession() {
		return session;
	}

	public EndpointConfig getConfig() {
		return config;
	}
}