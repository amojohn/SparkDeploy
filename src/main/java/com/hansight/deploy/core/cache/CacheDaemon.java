package com.hansight.deploy.core.cache;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.hansight.deploy.core.cache.refresh.ClassPathFileRefresh;

public class CacheDaemon {
	private Map<String, Cache> map = new WeakHashMap<String, Cache>();
	private static final CacheDaemon instance = new CacheDaemon();
	private static final int NO_REFRESH = -1;
	private static final int ALWAYS_REFRESH = 0;

	private CacheDaemon() {

	}

	static {
		init();
	}

	private static void init() {
		try {
			register("cmd/index.html", -1, new ClassPathFileRefresh(
					"cmd/index.html"));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static CacheDaemon getInstance() {
		return instance;
	}

	public static Object get(String name) {
		Cache cache = null;
		synchronized (CacheDaemon.class) {
			cache = getInstance().map.get(name);
			if (cache == null) {
				return null;
			}
		}
		// is need refresh
		boolean needRefresh = false;
		if (cache.getPolicy() == NO_REFRESH) {
			needRefresh = false;
		}
		if (cache.getPolicy() == ALWAYS_REFRESH) {
			needRefresh = true;
		} else if (cache.getPolicy() > 0) {// timeout
			if (System.currentTimeMillis() - cache.getLastRefreh() > cache
					.getPolicy()) {
				needRefresh = true;
			}
		}
		// get value
		if (!needRefresh) {
			try {
				cache.getLock().readLock().lock();
				return cache.getValue();
			} finally {
				cache.getLock().readLock().unlock();
			}
		} else {
			try {
				cache.getLock().writeLock().lock();
				Method method = cache.getRefresh().getClass()
						.getDeclaredMethod("refresh");
				Object value = method.invoke(cache.getRefresh());
				cache.setValue(value);
				return value;
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} finally {
				cache.getLock().writeLock().unlock();
			}
			return null;
		}
	}

	/**
	 * 
	 * @param name
	 * @param policy
	 *            -1 no refresh, 0 always refresh, > 0 refresh interval
	 * @param refresh
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public synchronized static void register(String name, long policy,
			Refresh refresh) throws InstantiationException,
			IllegalAccessException {
		Cache cache = new Cache();
		cache.setLock(new ReentrantReadWriteLock());
		cache.setPolicy(policy);
		cache.setRefresh(refresh);
		cache.setValue(refresh.refresh());
		getInstance().map.put(name, cache);
	}

	public static void registerNoRefresh(String name, Object value) {
		Cache cache = new Cache();
		cache.setLock(new ReentrantReadWriteLock());
		cache.setPolicy(NO_REFRESH);
		cache.setValue(value);
		getInstance().map.put(name, cache);
	}
}
