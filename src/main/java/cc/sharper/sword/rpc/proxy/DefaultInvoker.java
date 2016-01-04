package cc.sharper.sword.rpc.proxy;

import cc.sharper.sword.rpc.Invocation;
import cc.sharper.sword.rpc.Result;
import cc.sharper.sword.rpc.RpcResult;

/**
 * Created by lizhitao on 16-1-4.
 */
public class DefaultInvoker<T> implements Invoker<T> {
	@Override
	public Result invoke(Invocation invocation) {
		Object[] args = invocation.getArguments();
		Result result = new RpcResult("hello:" + args[0]);
		return result;
	}
}
