package cc.sharper.sword.rpc;

import java.io.Serializable;

/**
 * Created by lizhitao on 16-1-5.
 * rpc执行结果
 */
public class RpcResult implements Result, Serializable {
    private Object result;

    public RpcResult(Object result) {
        this.result = result;
    }

    public Object getValue() {
        return result;
    }
}
