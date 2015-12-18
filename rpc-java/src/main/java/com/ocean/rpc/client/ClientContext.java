package com.ocean.rpc.client;

import com.ocean.rpc.common.RpcContext;

/**
 * 
 * @author： ocean 创建时间：2015年12月17日 mail：zhangjunfang0505@163.com 描述：
 */
public class ClientContext extends RpcContext {
	private final RpcClient client;

	public ClientContext(RpcClient client) {
		this.client = client;
	}

	public RpcClient getClient() {
		return client;
	}
}