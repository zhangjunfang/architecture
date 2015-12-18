package com.ocean.rpc.server;

import com.ocean.rpc.common.RpcContext;

/**
 * 
 * @author： ocean 创建时间：2015年12月18日 mail：zhangjunfang0505@163.com 描述：
 */
public interface RpcServiceEvent {
	void onBeforeInvoke(String name, Object[] args, boolean byRef,
			RpcContext context);

	void onAfterInvoke(String name, Object[] args, boolean byRef,
			Object result, RpcContext context);

	void onSendError(Throwable e, RpcContext context);
}
