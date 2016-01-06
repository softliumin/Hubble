package cc.sharper.sword.rpc.proxy;

import cc.sharper.sword.rpc.Invoker;

/**
 * Created by lizhitao on 16-1-5.
 */
public abstract class AbstractProxyFactory implements ProxyFactory{
    public <T> T getProxy(Invoker<T> invoker) {
        Class<?>[] interfaces = new Class<?>[1];
        interfaces[0] = invoker.getInterface();
        return getProxy(invoker, interfaces);
    }

    protected abstract <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces);
}
