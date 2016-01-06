package cc.sharper.sword.rpc.proxy;

import cc.sharper.sword.rpc.Invoker;

/**
 * Created by lizhitao on 16-1-5.
 * 创建代理类的抽象工厂
 */
public interface ProxyFactory {
    <T> T getProxy(Invoker<T> invoker);
}
