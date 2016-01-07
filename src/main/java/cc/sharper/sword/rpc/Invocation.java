package cc.sharper.sword.rpc;

/**
 * Created by lizhitao on 16-1-4.
 */
public interface Invocation {
    String getClassName();
    String getMethodName();
    Class<?>[] getArgumentsTypes();
    Object[] getArguments();
}
