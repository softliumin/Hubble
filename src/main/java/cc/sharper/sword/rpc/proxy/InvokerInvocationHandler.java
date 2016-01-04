package cc.sharper.sword.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by lizhitao on 16-1-4.
 * 代理对象handler
 */
public class InvokerInvocationHandler implements InvocationHandler {
    private Class<?> invoker;

    public InvokerInvocationHandler(Class<?> invoker) {
        this.invoker = invoker;
    }

    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
//        System.out.println(proxy);
        System.out.println(method.getDeclaringClass());
        System.out.println("代理开始");
        if (invoker.getMethod("invoke", new Class[]{}) != null)
            System.out.println("aaaa");
        return null;
    }
}
