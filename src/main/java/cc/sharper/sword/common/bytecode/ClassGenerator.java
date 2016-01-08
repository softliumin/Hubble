package cc.sharper.sword.common.bytecode;

import cc.sharper.sword.common.util.ClassHelper;
import cc.sharper.sword.common.util.ReflectUtil;
import javassist.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lizhitao
 *         使用javassist动态生成Java类,javassist基于字节码操作，比反射性能更好
 */
public final class ClassGenerator {
    // 标签接口，标明是javassist动态生成的类
    public static interface DC {
    }

    private static final AtomicLong CLASS_NAME_COUNTER = new AtomicLong(0);
    private static final String SIMPLE_NAME_TAG = "<init>";
    // 根据不同的ClassLoader缓存不同的ClassPool，javassist通过ClassPool来动态修改类或创建类，
    private static final Map<ClassLoader, ClassPool> POOL_MAP = new ConcurrentHashMap<ClassLoader, ClassPool>();

    private ClassPool classPool;
    private String generatorClassName;
    private Set<String> interfaceNames;
    private String superClassName;
    private Set<String> fields;
    private List<String> methods;
    private Map<String, Method> copyMethods;
    private List<String> constructors;
    private Map<String, Constructor<?>> copyConstructors; // <constructor desc,constructor instance>
    private boolean defaultConstructor = false;
    private CtClass generatorClass;

    private ClassGenerator(ClassPool classPool) {
        this.classPool = classPool;
    }

    /**
     * 判断一个类是否为javassist动态生成的类
     *
     * @param clazz
     * @return boolean
     */
    public static boolean isDynamicClass(Class<?> clazz) {
        return ClassGenerator.DC.class.isAssignableFrom(clazz);
    }

    /**
     * 创建 ClassGenerator 对象
     * @return
     */
    public static ClassGenerator newInstance() {
        return new ClassGenerator(getClassPool(ClassHelper.getCallerClassLoader(ClassGenerator.class)));
    }

    /**
     * 创建 ClassGenerator 对象
     * @param classLoader
     * @return
     */
    public static ClassGenerator newInstance(ClassLoader classLoader) {
        return new ClassGenerator(getClassPool(classLoader));
    }

    /**
     * 获取ClassPool对象，正常情况下使用默认的ClassLoader就可以创建，
     * 但像tomcat这样的容器自定义了ClassLoader使用默认的ClassLoader创建类会抛出异常
     *
     * @param classLoader 类加载器
     * @return ClassPool
     */
    public static ClassPool getClassPool(ClassLoader classLoader) {
        if (null == classLoader)
            return ClassPool.getDefault();
        // 获取并缓存 ClassPool
        ClassPool pool = POOL_MAP.get(classLoader);
        if (pool == null) {
            pool = new ClassPool(true);
            pool.appendClassPath(new LoaderClassPath(classLoader));
            POOL_MAP.put(classLoader, pool);
        }
        return pool;
    }

    /**
     * 设置动态生成的类名称
     *
     * @param className 类的全限定名称
     * @return
     */
    public ClassGenerator setClassName(String className) {
        this.generatorClassName = className;
        return this;
    }

    /**
     * 为生成的类添加接口
     *
     * @param interfaceName 接口全限定名称
     * @return
     */
    public ClassGenerator addInterface(String interfaceName) {
        if (null == interfaceNames)
            interfaceNames = new HashSet<String>();

        interfaceNames.add(interfaceName);
        return this;
    }

    /**
     * 为生成的类添加接口
     *
     * @param clazz 接口Class对象
     * @return
     */
    public ClassGenerator addInterface(Class<?> clazz) {
        return addInterface(clazz.getName());
    }

    /**
     * 为生成的类继承父类
     *
     * @param superClassName 父类的全限定类名称
     * @return
     */
    public ClassGenerator setSuperClassName(String superClassName) {
        this.superClassName = superClassName;
        return this;
    }

    /**
     * 为生成的类继承父类
     *
     * @param superClass 父类的Class对象
     * @return
     */
    public ClassGenerator setSuperClassName(Class<?> superClass) {
        return setSuperClassName(superClass.getName());
    }

    /**
     * 为生成的类添加字段
     *
     * @param code 字段完整代码（例如：private String name;如果带默认值则：private String name=\"defaultValue\";）
     * @return
     */
    public ClassGenerator addField(String code) {
        if (null == fields)
            fields = new HashSet<String>();
        fields.add(code);
        return this;
    }

    /**
     * 为生成的类添加字段
     *
     * @param field     字段名称
     * @param mod       字段修饰符(可使用Modifier类获取修饰符，如：Modifier.PUBLIC)
     * @param fieldType 字段类型Class对象
     * @return
     */
    public ClassGenerator addField(String field, int mod, Class<?> fieldType) {
        return addField(field, mod, fieldType, null);
    }

    /**
     * 为生成的类添加字段
     *
     * @param field        字段名称
     * @param mod          字段修饰符(可使用Modifier类获取修饰符，如：Modifier.PUBLIC)
     * @param fieldType    字段类型Class对象
     * @param defaultValue 字段的默认值
     * @return
     */
    public ClassGenerator addField(String field, int mod, Class<?> fieldType, String defaultValue) {
        StringBuilder code = new StringBuilder();
        code.append(getModifier(mod));
        code.append(" " + ReflectUtil.getClassName(fieldType));
        code.append(" " + field);
        if (defaultValue != null && defaultValue.length() > 0) {
            code.append('=');
            code.append(defaultValue);
        }
        code.append(';');
        return addField(code.toString());
    }

    /**
     * 为生成的类添加方法
     *
     * @param code 方法代码（例如：public java.lang.String echo(java.lang.String msg){return \"echo message\";}）
     * @return
     */
    public ClassGenerator addMethod(String code) {
        if (methods == null)
            methods = new ArrayList<String>();
        methods.add(code);
        return this;
    }

    /**
     * 为生成的类添加方法
     * @param name 方法名称
     * @param mod 修饰符(可使用Modifier类获取修饰符，如：Modifier.PUBLIC)
     * @param returnType 方法返回值类型
     * @param parameterTypes 参数类型列表
     * @param body 方法执行体
     * @return
     */
    public ClassGenerator addMethod(String name, int mod, Class<?> returnType, Class<?>[] parameterTypes, String body) {
        return addMethod(name, mod, returnType, parameterTypes, null, body);
    }

    /**
     * 为生成的类添加方法
     * @param name 方法名称
     * @param mod 修饰符(可使用Modifier类获取修饰符，如：Modifier.PUBLIC)
     * @param returnType 方法返回值类型
     * @param exceptionTypes 方法异常类型
     * @param body 方法执行体
     * @return
     */
    public ClassGenerator addMethod(String name, int mod, Class<?> returnType, Class<?>[] parameterTypes, Class<?>[] exceptionTypes, String body) {
        StringBuilder sb = new StringBuilder();
        sb.append(getModifier(mod)).append(' ').append(ReflectUtil.getClassName(returnType)).append(' ').append(name);
        sb.append('(');
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0)
                sb.append(',');
            sb.append(ReflectUtil.getClassName(parameterTypes[i]));
            sb.append(" arg").append(i);
        }
        sb.append(')');
        if (exceptionTypes != null && exceptionTypes.length > 0) {
            sb.append(" throws ");
            for (int i = 0; i < exceptionTypes.length; i++) {
                if (i > 0)
                    sb.append(',');
                sb.append(ReflectUtil.getClassName(exceptionTypes[i]));
            }
        }
        sb.append('{').append(body).append('}');
        return addMethod(sb.toString());
    }

    /**
     * 为生成的类添加方法
     * @param m Method对象
     * @return
     */
    public ClassGenerator addMethod(Method m) {
        addMethod(m.getName(), m);
        return this;
    }

    public ClassGenerator addMethod(String name, Method m) {
        String desc = name + ReflectUtil.getDescWithoutMethodName(m);
        addMethod(':' + desc);
        if (copyMethods == null)
            copyMethods = new ConcurrentHashMap<String, Method>(8);
        copyMethods.put(desc, m);
        return this;
    }

    /**
     * 为生成的类添加构造方法
     * @param code 构造方法代码体
     * @return
     */
    public ClassGenerator addConstructor(String code) {
        if (constructors == null)
            constructors = new LinkedList<String>();
        constructors.add(code);
        return this;
    }

    public ClassGenerator addConstructor(int mod, Class<?>[] pts, String body) {
        return addConstructor(mod, pts, null, body);
    }

    public ClassGenerator addConstructor(int mod, Class<?>[] pts, Class<?>[] ets, String body) {
        StringBuilder sb = new StringBuilder();
        sb.append(getModifier(mod)).append(' ').append(SIMPLE_NAME_TAG);
        sb.append('(');
        for (int i = 0; i < pts.length; i++) {
            if (i > 0)
                sb.append(',');
            sb.append(ReflectUtil.getClassName(pts[i]));
            sb.append(" arg").append(i);
        }
        sb.append(')');
        if (ets != null && ets.length > 0) {
            sb.append(" throws ");
            for (int i = 0; i < ets.length; i++) {
                if (i > 0)
                    sb.append(',');
                sb.append(ReflectUtil.getClassName(ets[i]));
            }
        }
        sb.append('{').append(body).append('}');
        return addConstructor(sb.toString());
    }

    public ClassGenerator addConstructor(Constructor<?> c) {
        String desc = ReflectUtil.getDesc(c);
        addConstructor(":" + desc);
        if (copyConstructors == null)
            copyConstructors = new ConcurrentHashMap<String, Constructor<?>>(4);
        copyConstructors.put(desc, c);
        return this;
    }

    public ClassGenerator addDefaultConstructor() {
        defaultConstructor = true;
        return this;
    }

    /**
     * 生成类的Class对象
     * @return
     */
    public Class<?> toClass() {
        if (generatorClass != null)
            generatorClass.detach();
        long id = CLASS_NAME_COUNTER.getAndIncrement();// 默认生成类名称的自增字段
        try {
            CtClass ctcs = superClassName == null ? null : classPool.get(superClassName);// 获取待生成类的父类CtClass对象
            if (generatorClassName == null)
                generatorClassName = (superClassName == null || javassist.Modifier.isPublic(ctcs.getModifiers())
                        ? ClassGenerator.class.getName() : superClassName + "$sc") + id;// 设置待生成类的类名
            generatorClass = classPool.makeClass(generatorClassName);// 生成类的 CtClass 对象
            if (superClassName != null)
                generatorClass.setSuperclass(ctcs);// 设置父类
            generatorClass.addInterface(classPool.get(DC.class.getName())); // 添加动态生成类标签
            if (interfaceNames != null)
                for (String cl : interfaceNames) generatorClass.addInterface(classPool.get(cl));// 添加接口
            if (fields != null)
                for (String code : fields) generatorClass.addField(CtField.make(code, generatorClass));// 添加字段
            if (methods != null) {// 添加方法
                for (String code : methods) {
                    if (code.charAt(0) == ':')
                        generatorClass.addMethod(CtNewMethod.copy(getCtMethod(copyMethods.get(code.substring(1))), code.substring(1, code.indexOf('(')), generatorClass, null));
                    else
                        generatorClass.addMethod(CtNewMethod.make(code, generatorClass));
                }
            }
            if (defaultConstructor)// 添加默认构造器
                generatorClass.addConstructor(CtNewConstructor.defaultConstructor(generatorClass));
            if (constructors != null) {// 添加方法传入构造器
                for (String code : constructors) {
                    if (code.charAt(0) == ':') {
                        generatorClass.addConstructor(CtNewConstructor.copy(getCtConstructor(copyConstructors.get(code.substring(1))), generatorClass, null));
                    } else {
                        String[] sn = generatorClass.getSimpleName().split("\\$+"); // inner class name include $.
                        generatorClass.addConstructor(CtNewConstructor.make(code.replaceFirst(SIMPLE_NAME_TAG, sn[sn.length - 1]), generatorClass));
                    }
                }
            }
            return generatorClass.toClass(ClassHelper.getCallerClassLoader(getClass()), null);
        } catch (RuntimeException e) {
            throw e;
        } catch (NotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (CannotCompileException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        if (generatorClass != null) generatorClass.detach();
        if (interfaceNames != null) interfaceNames.clear();
        if (fields != null) fields.clear();
        if (methods != null) methods.clear();
        if (constructors != null) constructors.clear();
        if (copyMethods != null) copyMethods.clear();
        if (copyConstructors != null) copyConstructors.clear();
    }

    /**
     * 获取CtClass对象
     * @param c
     * @return
     * @throws NotFoundException
     */
    private CtClass getCtClass(Class<?> c) throws NotFoundException {
        return classPool.get(c.getName());
    }

    /**
     * 获取 CtMethod
     * @param m
     * @return
     * @throws NotFoundException
     */
    private CtMethod getCtMethod(Method m) throws NotFoundException {
        return getCtClass(m.getDeclaringClass()).getMethod(m.getName(), ReflectUtil.getDescWithoutMethodName(m));
    }

    private CtConstructor getCtConstructor(Constructor<?> c) throws NotFoundException {
        return getCtClass(c.getDeclaringClass()).getConstructor(ReflectUtil.getDesc(c));
    }

    /**
     * 获取修饰符字符串
     * @param mod 修饰符int标示
     * @return
     */
    private static String getModifier(int mod) {
        if (Modifier.isPrivate(mod))
            return "private";
        if (Modifier.isProtected(mod))
            return "protected";
        if (Modifier.isPublic(mod))
            return "public";
        return "";
    }

}
