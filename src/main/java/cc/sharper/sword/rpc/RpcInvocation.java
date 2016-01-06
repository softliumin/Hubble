package cc.sharper.sword.rpc;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by lizhitao on 16-1-4.
 * 远程方法执行签名封装类
 */
public class RpcInvocation implements Invocation, Serializable {
    /**
     * 执行的方法的名称
     */
    private String methodName;
    /**
     * 执行的方法的参数类型
     */
    private Class<?>[] argumentsTypes;
    /**
     * 执行的方法的参数
     */
    private Object[] arguments;

    private Invoker<?> invoker;

    public RpcInvocation(Method method, Object[] arguments) {
        this(method.getName(), method.getParameterTypes(), arguments);
    }

    public RpcInvocation(Method method, Object[] arguments, Invoker invoker) {
        this(method.getName(), method.getParameterTypes(), arguments);
        this.invoker = invoker;
    }

    public RpcInvocation(String methodName, Class<?>[] argumentsTypes, Object[] arguments) {
        this.methodName = methodName;
        this.argumentsTypes = argumentsTypes;
        this.arguments = arguments;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getArgumentsTypes() {
        return argumentsTypes;
    }

    public void setArgumentsTypes(Class<?>[] argumentsTypes) {
        this.argumentsTypes = argumentsTypes;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public Invoker<?> getInvoker() {
        return invoker;
    }
}
