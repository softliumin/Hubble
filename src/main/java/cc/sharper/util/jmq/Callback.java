package cc.sharper.util.jmq;

/**
 * 回调函数的接口
 * Created by liumin3 on 2016/1/12.
 */
public interface Callback {
    public void onMessage(String message);
}