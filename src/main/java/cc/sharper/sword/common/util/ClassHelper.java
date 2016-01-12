package cc.sharper.sword.common.util;

public class ClassHelper {
	
	/**
	 * 获取class对象的类加载器
	 * @param clazz
	 * @return ClassLoader
	 */
	public static ClassLoader getCallerClassLoader(Class<?> clazz) {
		return clazz.getClassLoader();
	}
}
