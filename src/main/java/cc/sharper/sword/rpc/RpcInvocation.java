package cc.sharper.sword.rpc;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by lizhitao on 16-1-4.
 * 远程方法执行签名封装类
 */
public class RpcInvocation implements Invocation, Serializable {
    /**
     * 执行的类名称
     */
    private String className;
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

    public RpcInvocation(String className, Method method, Object[] arguments) {
        this(className, method.getName(), method.getParameterTypes(), arguments);
    }

    public RpcInvocation(String className, String methodName, Class<?>[] argumentsTypes, Object[] arguments) {
        this.className = className;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
