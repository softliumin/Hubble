package cc.sharper.sword.rpc.proxy;

import cc.sharper.sword.common.bytecode.JavassistProxy;
import cc.sharper.sword.rpc.Invoker;

/**
 * Created by lizhitao on 16-1-8.
 */
public class JavassistProxyFacoty extends AbstractProxyFactory {
    @Override
    protected <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces) {
        return (T) JavassistProxy.getProxy(interfaces).newInstance(new InvokerInvocationHandler(invoker));
    }
}
