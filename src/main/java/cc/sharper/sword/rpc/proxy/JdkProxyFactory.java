package cc.sharper.sword.rpc.proxy;

import cc.sharper.sword.rpc.Invoker;

import java.lang.reflect.Proxy;

/**
 * Created by lizhitao on 16-1-4.
 * jdk代理对象
 */
public class JdkProxyFactory extends AbstractProxyFactory{

    /**
     * 获得代理对象
     *
     * @param invoker
     * @param interfaces
     * @param <T>
     * @return
     */
    @Override
    public <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new InvokerInvocationHandler(invoker));
    }
}
