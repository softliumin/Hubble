package cc.sharper.sword.cache.support;

import java.util.HashMap;
import java.util.Map;

import cc.sharper.sword.cache.Cache;

/**
 * 
 * @author lizhitao 线程私有的缓存,没有并发问题
 */
public class ThreadLocalCache implements Cache {
	private final ThreadLocal<Map<Object, Object>> cache;

	public ThreadLocalCache() {
		cache = new ThreadLocal<Map<Object, Object>>() {
			@Override
			protected Map<Object, Object> initialValue() {
				return new HashMap<Object, Object>();
			}
		};
	}

	public void put(Object key, Object value) {
		cache.get().put(key, value);
	}

	public Object get(Object key) {
		return cache.get().get(key);
	}

}
