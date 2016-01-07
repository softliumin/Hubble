package cc.sharper.sword.rpc;

import java.io.Serializable;

/**
 * Created by lizhitao on 16-1-5.
 * rpc执行结果
 */
public class RpcResult implements Result, Serializable {
    private Object result;
    private Throwable exception;

    public RpcResult(Object result) {
        this.result = result;
    }

    public RpcResult(Throwable exception) {
        this.exception = exception;
    }

    public Object getValue() {
        return result;
    }

    public Throwable getException() {
        return this.exception;
    }

    public boolean hasException() {
        return null != exception;
    }
}
