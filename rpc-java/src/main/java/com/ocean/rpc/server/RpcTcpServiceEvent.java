package com.ocean.rpc.server;

/**
 * 
 * @author： ocean 创建时间：2015年12月18日 mail：zhangjunfang0505@163.com 描述：
 */
public interface RpcTcpServiceEvent extends RpcServiceEvent {
	void onAccept(TcpContext tcpContext);

	void onClose(TcpContext tcpContext);
}
