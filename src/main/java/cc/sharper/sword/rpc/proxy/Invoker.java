package cc.sharper.sword.rpc.proxy;

import cc.sharper.sword.rpc.Invocation;
import cc.sharper.sword.rpc.Result;

/**
 * Created by lizhitao on 16-1-4.
 */
public interface Invoker<T> {
	Result invoke(Invocation invocation);
}
