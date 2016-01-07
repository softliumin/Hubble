package cc.sharper.sword.common.bytecode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import cc.sharper.sword.common.util.ClassHelper;
import javassist.ClassPool;
import javassist.LoaderClassPath;

/**
 * 
 * @author lizhitao
 * 使用javassist动态生成Java类,javassist基于字节码操作，比反射性能更好
 */
public final class ClassGenetator {
	// 标签接口，标明是javassist动态生成的类
	private static interface DC {
	}

	private static final AtomicLong CLASS_NAME_COUNTER = new AtomicLong(0);
	private static final String SIMPLE_NAME_TAG = "<init>";
	// 根据不同的ClassLoader缓存不同的ClassPool，javassist通过ClassPool来动态修改类或创建类，
	private static final Map<ClassLoader, ClassPool> POOL_MAP = new ConcurrentHashMap<ClassLoader, ClassPool>();

	private ClassPool classPool;

	private ClassGenetator(ClassPool classPool) {
		this.classPool = classPool;
	}

	/**
	 * 判断一个类是否为javassist动态生成的类
	 * @param clazz
	 * @return boolean
	 */
	public static boolean isDynamicClass(Class<?> clazz) {
		return ClassGenetator.DC.class.isAssignableFrom(clazz);
	}

	public static ClassGenetator newInstance() {
		return new ClassGenetator(getClassPool(ClassHelper.getCallerClassLoader(ClassGenetator.class)));
	}

	public static ClassGenetator newInstance(ClassLoader classLoader) {
		return new ClassGenetator(getClassPool(classLoader));
	}

	/**
	 * 获取ClassPool对象，正常情况下使用默认的ClassLoader就可以创建，
	 * 但像tomcat这样的容器自定义了ClassLoader使用默认的ClassLoader创建类会抛出异常
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
}
