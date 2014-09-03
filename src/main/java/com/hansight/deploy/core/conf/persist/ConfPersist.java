package com.hansight.deploy.core.conf.persist;

import java.util.Map;

public interface ConfPersist {
	void init();

	String get(String name);

	boolean set(String name, String value);

	boolean delete(String name);

	Map<String, String> getAll();
}
