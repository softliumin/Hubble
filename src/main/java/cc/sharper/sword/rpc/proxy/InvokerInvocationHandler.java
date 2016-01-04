package cc.sharper.sword.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import cc.sharper.sword.rpc.RpcInvocation;

/**
 * Created by lizhitao on 16-1-4. 代理对象handler
 */
public class InvokerInvocationHandler implements InvocationHandler {
	private Invoker<?> invoker;

	public InvokerInvocationHandler(Invoker<?> invoker) {
		this.invoker = invoker;
	}

	public Object invoke(Object proxy, Method method, Object[] arguments)
			throws Throwable {
		String methodName = method.getName();
		Class<?>[] parameterTypes = method.getParameterTypes();

		if (Object.class.equals(method.getDeclaringClass()))
			return method.invoke(invoker, arguments);

		if ("toString".equals(methodName) && parameterTypes.length == 0)
			return invoker.toString();

		if ("hashCode".equals(methodName) && parameterTypes.length == 0)
			return invoker.hashCode();

		if ("equals".equals(methodName) && parameterTypes.length == 1)
			return invoker.equals(arguments[0]);

		return invoker.invoke(new RpcInvocation(method, arguments)).getValue();
	}
}
