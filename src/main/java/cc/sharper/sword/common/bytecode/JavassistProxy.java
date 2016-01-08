package cc.sharper.sword.common.bytecode;

import cc.sharper.sword.common.util.ClassHelper;
import cc.sharper.sword.common.util.ReflectUtil;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by lizhitao on 16-1-8.
 * 使用javassist动态代理对象
 */
public abstract class JavassistProxy {
    private static final AtomicLong PROXY_CLASS_COUNTER = new AtomicLong(0);
    private static final Map<ClassLoader, Map<String, Object>> proxyCacheMap = new WeakHashMap<ClassLoader, Map<String, Object>>();
    private static final Object PendingGenerationMarker = new Object();
    private static final String PACKAGE_NAME = JavassistProxy.class.getPackage().getName();
    public static final InvocationHandler THROW_UNSUPPORTED_INVOKER = new InvocationHandler() {
        public Object invoke(Object proxy, Method method, Object[] args) {
            throw new UnsupportedOperationException("Method [" + ReflectUtil.getMethodName(method) + "] unimplemented.");
        }
    };

    /**
     * 获取实例
     *
     * @param interfaces 接口列表
     * @return
     */
    public static JavassistProxy getProxy(Class<?>... interfaces) {
        return getProxy(ClassHelper.getCallerClassLoader(JavassistProxy.class), interfaces);
    }

    /**
     * 获取 JavassistProxy 实例
     *
     * @param classLoader
     * @param interfaces
     * @return
     */
    public static JavassistProxy getProxy(ClassLoader classLoader, Class<?>... interfaces) {
        if (null == interfaces || interfaces.length == 0)
            throw new IllegalArgumentException("interfaces can not be null and length must great than 0");

        if (interfaces.length > 65535)
            throw new IllegalArgumentException("illegal interfaces size,must less than 65535");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < interfaces.length; i++) {
            String inter = interfaces[i].getName();// 获取接口名称
            if (!interfaces[i].isInterface())
                throw new IllegalArgumentException(inter + " is not a interface");

            Class<?> tmp = null;
            try {
                tmp = Class.forName(inter, false, classLoader);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (tmp != interfaces[i])
                throw new IllegalArgumentException(interfaces[i] + " is not visible from classLoader");

            sb.append(inter).append(";");
        }

        String key = sb.toString();
        Map<String, Object> cache;
        synchronized (proxyCacheMap) {
            cache = proxyCacheMap.get(classLoader);
            if (null == cache) {
                cache = new HashMap<String, Object>();
                proxyCacheMap.put(classLoader, cache);
            }
        }

        JavassistProxy proxy = null;

        synchronized (cache) {
            do {
                Object value = cache.get(key);
                if (value instanceof Reference<?>) {
                    proxy = (JavassistProxy) ((Reference<?>) value).get();
                    if (proxy != null)
                        return proxy;
                }

                if (value == PendingGenerationMarker) {
                    try {
                        cache.wait();
                    } catch (InterruptedException e) {
                    }
                } else {
                    cache.put(key, PendingGenerationMarker);
                    break;
                }
            } while (true);
        }

        long id = PROXY_CLASS_COUNTER.getAndIncrement();
        String pkg = null;
        ClassGenerator ccp = null, ccm = null;
        try {
            ccp = ClassGenerator.newInstance(classLoader);

            Set<String> worked = new HashSet<String>();
            List<Method> methods = new ArrayList<Method>();

            for (int i = 0; i < interfaces.length; i++) {
                if (!Modifier.isPublic(interfaces[i].getModifiers())) {
                    String npkg = interfaces[i].getPackage().getName();
                    if (pkg == null) {
                        pkg = npkg;
                    } else {
                        if (!pkg.equals(npkg))
                            throw new IllegalArgumentException("non-public interfaces from different packages");
                    }
                }
                ccp.addInterface(interfaces[i]);

                for (Method method : interfaces[i].getMethods()) {
                    String desc = ReflectUtil.getDesc(method);
                    if (worked.contains(desc))
                        continue;
                    worked.add(desc);

                    int ix = methods.size();
                    Class<?> rt = method.getReturnType();
                    Class<?>[] pts = method.getParameterTypes();

                    StringBuilder code = new StringBuilder("Object[] args = new Object[").append(pts.length).append("];");
                    for (int j = 0; j < pts.length; j++)
                        code.append(" args[").append(j).append("] = ($w)$").append(j + 1).append(";");
                    code.append(" Object ret = handler.invoke(this, methods[" + ix + "], args);");
                    if (!Void.TYPE.equals(rt))
                        code.append(" return ").append(asArgument(rt, "ret")).append(";");

                    methods.add(method);
                    ccp.addMethod(method.getName(), method.getModifiers(), rt, pts, method.getExceptionTypes(), code.toString());
                }
            }

            if (pkg == null)
                pkg = PACKAGE_NAME;

            // create ProxyInstance class.
            String pcn = pkg + ".proxy" + id;
            ccp.setClassName(pcn);
            ccp.addField("public static java.lang.reflect.Method[] methods;");
            ccp.addField("private " + InvocationHandler.class.getName() + " handler;");
            ccp.addConstructor(Modifier.PUBLIC, new Class<?>[]{InvocationHandler.class}, new Class<?>[0], "handler=$1;");
            ccp.addDefaultConstructor();
            Class<?> clazz = ccp.toClass();
            clazz.getField("methods").set(null, methods.toArray(new Method[0]));

            // create Proxy class.
            String fcn = JavassistProxy.class.getName() + id;
            ccm = ClassGenerator.newInstance(classLoader);
            ccm.setClassName(fcn);
            ccm.addDefaultConstructor();
            ccm.setSuperClassName(JavassistProxy.class);
            ccm.addMethod("public Object newInstance(" + InvocationHandler.class.getName() + " h){ return new " + pcn + "($1); }");
            Class<?> pc = ccm.toClass();
            proxy = (JavassistProxy) pc.newInstance();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            // release ClassGenerator
            if (ccp != null)
                ccp.release();
            if (ccm != null)
                ccm.release();
            synchronized (cache) {
                if (proxy == null)
                    cache.remove(key);
                else
                    cache.put(key, new WeakReference<JavassistProxy>(proxy));
                cache.notifyAll();
            }
        }
        return proxy;
    }

    /**
     * get instance with default handler.
     *
     * @return instance.
     */
    public Object newInstance() {
        return newInstance(THROW_UNSUPPORTED_INVOKER);
    }

    /**
     * get instance with special handler.
     *
     * @return instance.
     */
    abstract public Object newInstance(InvocationHandler handler);

    protected JavassistProxy() {
    }

    private static String asArgument(Class<?> cl, String name) {
        if (cl.isPrimitive()) {
            if (Boolean.TYPE == cl)
                return name + "==null?false:((Boolean)" + name + ").booleanValue()";
            if (Byte.TYPE == cl)
                return name + "==null?(byte)0:((Byte)" + name + ").byteValue()";
            if (Character.TYPE == cl)
                return name + "==null?(char)0:((Character)" + name + ").charValue()";
            if (Double.TYPE == cl)
                return name + "==null?(double)0:((Double)" + name + ").doubleValue()";
            if (Float.TYPE == cl)
                return name + "==null?(float)0:((Float)" + name + ").floatValue()";
            if (Integer.TYPE == cl)
                return name + "==null?(int)0:((Integer)" + name + ").intValue()";
            if (Long.TYPE == cl)
                return name + "==null?(long)0:((Long)" + name + ").longValue()";
            if (Short.TYPE == cl)
                return name + "==null?(short)0:((Short)" + name + ").shortValue()";
            throw new RuntimeException(name + " is unknown primitive type.");
        }
        return "(" + ReflectUtil.getClassName(cl) + ")" + name;
    }
}
