package cc.sharper.sword.rpc;

import cc.sharper.sword.remoting.SimpleClient;

import java.io.Serializable;

/**
 * Created by lizhitao on 16-1-4.
 */
public class DefaultInvoker<T> implements Invoker<T>, Serializable {
    private Class<T> type;

    public DefaultInvoker(Class<T> type) {
        this.type = type;
    }

    public Class<T> getInterface() {
        return type;
    }


    public Result invoke(Invocation invocation) {
        SimpleClient client = new SimpleClient();
        Result remoteResult = client.remoteCall(invocation);
        return remoteResult;
    }
}
