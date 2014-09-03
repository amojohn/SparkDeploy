package com.hansight.deploy.core.conf;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hansight.deploy.core.conf.persist.ConfPersist;
import com.hansight.deploy.core.conf.persist.SQLitePersist;
import com.hansight.deploy.core.daemon.MonitorDaemon;

public class Configuration {
	private static final Logger LOG = LoggerFactory
			.getLogger(Configuration.class);

	public static final String ARRAY_SPLIT = ",";
	public static final String MAP_SPLIT_OUTTER = ";";
	public static final String MAP_SPLIT_INNER = ";";

	protected ReadWriteLock locksMapLock = new ReentrantReadWriteLock();
	private Map<String, ReadWriteLock> locksMap;
	private ConfPersist persist;
	private MonitorDaemon monitorDaemon;

	public Configuration() {
		try {
			locksMap = new HashMap<String, ReadWriteLock>();
			File file = new File("conf");
			if (!file.exists()) {
				file.mkdirs();
			}
			locksMapLock.writeLock().lock();
			persist = new SQLitePersist();
			persist.init();
		} finally {
			locksMapLock.writeLock().unlock();
		}
	}

	public void setMonitorDaemon(MonitorDaemon monitor) {
		this.monitorDaemon = monitor;
	}

	/**
	 * 多线程同时写一个name时，顺序执行。写的时候不能读。
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public boolean set(String name, String value) {
		boolean res = false;
		ReadWriteLock fieldLock = getLock(name);
		try {
			fieldLock.writeLock().lock();
			String val = persist.get(name);
			boolean change = false;
			if (value == null) {
				if (val != null) {
					change = true;
				}
			} else {
				if (!value.equals(val)) {
					change = true;
				}
			}
			if (change) {
				// save
				persist.set(name, value);
				// event
				if (monitorDaemon != null) {
					monitorDaemon.parse(name, value);
				}
			}
		} catch (Exception e) {
			LOG.warn("set conf error", e);
		} finally {
			fieldLock.writeLock().unlock();
		}
		return res;
	}

	/**
	 * 多线程进行删除同一个属性时，顺序执行。 删除时不能读写。
	 * 
	 * @param name
	 * @return
	 */
	public boolean delete(String name) {
		boolean res = false;
		ReadWriteLock fieldLock = getLock(name);
		try {
			fieldLock.writeLock().lock();
			res = persist.delete(name);
		} catch (Exception e) {
			LOG.warn("set conf error", e);
		} finally {
			fieldLock.writeLock().unlock();
		}
		return res;
	}

	/**
	 * 多线程读取同一属性并发读取。读取时不能写和删。
	 * 
	 * @param name
	 * @return
	 */
	public String get(String name) {
		ReadWriteLock fieldLock = getLock(name);
		try {
			fieldLock.readLock().lock();
			return persist.get(name);
		} catch (Exception e) {
			LOG.warn("set conf error", e);
		} finally {
			fieldLock.readLock().unlock();
		}
		return null;
	}

	public int getInt(String name, int defaultVal) {
		String value = get(name);
		if (value != null) {
			return Integer.parseInt(value);
		}
		return defaultVal;
	}

	public int getInt(String name) {
		return getInt(name, 0);
	}

	public long getLong(String name, long defaultVal) {
		String value = get(name);
		if (value != null) {
			return Long.parseLong(value);
		}
		return defaultVal;
	}

	public long getLong(String name) {
		return getLong(name, 0);
	}

	public String[] getArray(String name, String defaultVal) {
		String value = get(name, defaultVal);
		if (value == null) {
			return null;
		}
		return value.split(ARRAY_SPLIT);
	}

	public Map<String, String> getMap(String name, String defaultVal) {
		String value = get(name, defaultVal);
		if (value == null) {
			return null;
		}
		String[] arr = value.split(MAP_SPLIT_OUTTER);
		if (arr == null) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		for (String str : arr) {
			if (str != null && (str = str.trim()).length() > 0) {
				String[] tmp = str.split(MAP_SPLIT_INNER);
				if (tmp != null) {
					map.put(tmp[0], tmp[1]);
				}
			}
		}
		return map;
	}

	public Map<String, String> getAll() {
		return persist.getAll();
	}

	public String get(String name, String defaultValue) {
		String value = get(name);
		if (value != null) {
			return value;
		}
		return defaultValue;
	}

	public boolean getBoolean(String name, boolean defalutValue) {
		String value = get(name);
		if (value != null) {
			return "true".equalsIgnoreCase(value);
		}
		return defalutValue;
	}

	public boolean getBoolean(String name) {
		return getBoolean(name, false);
	}

	public ReadWriteLock getLock(String name) {
		ReadWriteLock val = null;
		try {
			locksMapLock.writeLock().lock();
			val = locksMap.get(name);
			if (val == null) {
				val = new ReentrantReadWriteLock();
				locksMap.put(name, val);
			}
		} finally {
			locksMapLock.writeLock().unlock();
		}
		return val;
	}

	public static void main(String[] args) throws SQLException {
		new Configuration();
	}
}
