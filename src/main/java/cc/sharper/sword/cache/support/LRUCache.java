package cc.sharper.sword.cache.support;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import cc.sharper.sword.cache.Cache;

public class LRUCache implements Cache {
	// 默认缓存的大小
	private static final int DEFAULT_CACHE_SIZE = 1024;
	private Map<Object, Object> cache;

	public LRUCache() {
		this(DEFAULT_CACHE_SIZE);// 初始化默认大小的缓存
	}

	public LRUCache(final int cacheSize) {
		if (cacheSize <= 0)
			throw new IllegalArgumentException("illegal cache size "
					+ cacheSize + ", cache size must great than 0");

		cache = new LinkedHashMap<Object, Object>() {
			private static final long serialVersionUID = 179464405250644243L;

			@Override
			protected boolean removeEldestEntry(Entry<Object, Object> eldest) {
				return size() > cacheSize;
			}
		};
	}

	@Override
	public void put(Object key, Object value) {
		synchronized (cache) {// LinkedHashMap 是非线程安全的
			cache.put(key, value);
		}
	}

	@Override
	public Object get(Object key) {
		synchronized (cache) {// LinkedHashMap 是非线程安全的
			return cache.get(key);
		}
	}

}
