package com.ocean.rpc.server;

import java.lang.reflect.Type;
import java.net.Socket;
import java.nio.channels.SocketChannel;

import com.ocean.rpc.common.RpcContext;
import com.ocean.rpc.common.RpcMethods;

/**
 * 
 * @author： ocean 创建时间：2015年12月18日 mail：zhangjunfang0505@163.com 描述：
 */
public class RpcTcpMethods extends RpcMethods {

	@Override
	protected int getCount(Type[] paramTypes) {
		int i = paramTypes.length;
		if ((i > 0) && (paramTypes[i - 1] instanceof Class<?>)) {
			Class<?> paramType = (Class<?>) paramTypes[i - 1];
			if (paramType.equals(RpcContext.class)
					|| paramType.equals(TcpContext.class)
					|| paramType.equals(SocketChannel.class)
					|| paramType.equals(Socket.class)) {
				--i;
			}
		}
		return i;
	}
}
