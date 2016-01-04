package cc.sharper.sword;

import cc.sharper.sword.rpc.proxy.Invoker;
import cc.sharper.sword.rpc.proxy.JdkProxy;

/**
 * Created by lizhitao on 16-1-4.
 * 提供 rpc 服务
 */
public class Test {
    public static void main(String[] args) {
        Invoker proxy = new JdkProxy<Invoker>().getProxy(Invoker.class, new Class[]{Invoker.class});
        proxy.invoke();
    }
}
