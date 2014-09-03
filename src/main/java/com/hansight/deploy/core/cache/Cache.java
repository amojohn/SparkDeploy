package com.hansight.deploy.core.cache;

import java.util.concurrent.locks.ReadWriteLock;

public class Cache {
	private Object value;
	// -1 no refresh, 0 always refresh, > 0 refresh interval
	private long policy;

	private long lastRefreh;

	private Refresh refresh;

	private ReadWriteLock lock;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public long getPolicy() {
		return policy;
	}

	public void setPolicy(long policy) {
		this.policy = policy;
	}

	public Refresh getRefresh() {
		return refresh;
	}

	public void setRefresh(Refresh refresh) {
		this.refresh = refresh;
	}

	public ReadWriteLock getLock() {
		return lock;
	}

	public void setLock(ReadWriteLock lock) {
		this.lock = lock;
	}

	public long getLastRefreh() {
		return lastRefreh;
	}

	public void setLastRefreh(long lastRefreh) {
		this.lastRefreh = lastRefreh;
	}

}
