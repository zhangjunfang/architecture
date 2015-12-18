package com.ocean.rpc.server;

import java.net.Socket;
import java.nio.channels.SocketChannel;

import com.ocean.rpc.common.RpcContext;

/**
 * 
 * @author： ocean 创建时间：2015年12月18日 mail：zhangjunfang0505@163.com 描述：
 */
public class TcpContext extends RpcContext {
	private final SocketChannel socketChannel;

	public TcpContext(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}

	public SocketChannel getSocketChannel() {
		return socketChannel;
	}

	public Socket getSocket() {
		return socketChannel.socket();
	}
}