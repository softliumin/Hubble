package cc.sharper.sword.rpc;

/**
 * Created by lizhitao on 16-1-5.
 * rpc执行结果
 */
public class RpcResult implements Result{
    private Object result;

    public RpcResult(Object result){
        this.result = result;
    }

    public Object getValue() {
        return result;
    }
}
