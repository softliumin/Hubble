package cc.sharper.sword.rpc.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by lizhitao on 16-1-4.
 * jdk代理对象
 */
public class JdkProxy<T> {

    /**
     * 获得代理对象
     * @param invoker
     * @param interfaces
     * @param <T>
     * @return
     */
	public <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new InvokerInvocationHandler(invoker));
    }
}
