package cc.sharper.sword.common.util;

import javassist.NotFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by lizhitao on 16-1-8.
 * 反射工具类
 */
public class ReflectUtil {
    /**
     * void(V).
     */
    public static final char JVM_VOID = 'V';

    /**
     * boolean(Z).
     */
    public static final char JVM_BOOLEAN = 'Z';

    /**
     * byte(B).
     */
    public static final char JVM_BYTE = 'B';

    /**
     * char(C).
     */
    public static final char JVM_CHAR = 'C';

    /**
     * double(D).
     */
    public static final char JVM_DOUBLE = 'D';

    /**
     * float(F).
     */
    public static final char JVM_FLOAT = 'F';

    /**
     * int(I).
     */
    public static final char JVM_INT = 'I';

    /**
     * long(J).
     */
    public static final char JVM_LONG = 'J';

    /**
     * short(S).
     */
    public static final char JVM_SHORT = 'S';

    /**
     * get method desc.
     * "(I)I", "()V", "(Ljava/lang/String;Z)V"
     *
     * @param m method.
     * @return desc.
     */
    public static String getDescWithoutMethodName(Method m) {
        StringBuilder ret = new StringBuilder();
        ret.append('(');
        Class<?>[] parameterTypes = m.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++)
            ret.append(getDesc(parameterTypes[i]));
        ret.append(')').append(getDesc(m.getReturnType()));
        return ret.toString();
    }

    /**
     * get class desc.
     * boolean[].class => "[Z"
     * Object.class => "Ljava/lang/Object;"
     *
     * @param c class.
     * @return desc.
     * @throws NotFoundException
     */
    public static String getDesc(Class<?> c) {
        StringBuilder ret = new StringBuilder();

        while (c.isArray()) {
            ret.append('[');
            c = c.getComponentType();
        }

        if (c.isPrimitive()) {
            String t = c.getName();
            if ("void".equals(t)) ret.append(JVM_VOID);
            else if ("boolean".equals(t)) ret.append(JVM_BOOLEAN);
            else if ("byte".equals(t)) ret.append(JVM_BYTE);
            else if ("char".equals(t)) ret.append(JVM_CHAR);
            else if ("double".equals(t)) ret.append(JVM_DOUBLE);
            else if ("float".equals(t)) ret.append(JVM_FLOAT);
            else if ("int".equals(t)) ret.append(JVM_INT);
            else if ("long".equals(t)) ret.append(JVM_LONG);
            else if ("short".equals(t)) ret.append(JVM_SHORT);
        } else {
            ret.append('L');
            ret.append(c.getName().replace('.', '/'));
            ret.append(';');
        }
        return ret.toString();
    }

    /**
     * 获取Class的名称
     *
     * @param clazz
     * @return
     */
    public static String getClassName(Class<?> clazz) {
        if (clazz.isArray()) {
            StringBuilder sb = new StringBuilder();
            do {
                sb.append("[]");
                clazz = clazz.getComponentType();
            }
            while (clazz.isArray());

            return clazz.getName() + sb.toString();
        }
        return clazz.getName();
    }

    /**
     * get constructor desc.
     * "()V", "(Ljava/lang/String;I)V"
     *
     * @param c constructor.
     * @return desc
     */
    public static String getDesc(final Constructor<?> c) {
        StringBuilder ret = new StringBuilder("(");
        Class<?>[] parameterTypes = c.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++)
            ret.append(getDesc(parameterTypes[i]));
        ret.append(')').append('V');
        return ret.toString();
    }
}
