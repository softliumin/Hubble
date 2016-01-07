package cc.sharper.sword.cache;

/**
 * @author lizhtiao cache接口
 */
public interface Cache {
	void put(Object key, Object value);

	Object get(Object key);
}
