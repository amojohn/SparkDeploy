package com.hansight.deploy.core.daemon;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hansight.deploy.core.conf.Configured;
import com.hansight.deploy.core.monitor.Monitor;

public class MonitorDaemon extends Configured {
	private static final Logger LOG = LoggerFactory
			.getLogger(MonitorDaemon.class);
	private final Map<String, Monitor> monitorMap = new HashMap<String, Monitor>();

	public boolean parse(String name, String value) {
		Monitor monitor = monitorMap.get(name);
		if (monitor == null) {
			System.out.print("unknown command :" + name + "\n>");
			return false;
		}
		monitor.execute(value, conf);
		return true;
	}

	public void addMonitor(Monitor cmdObject) {
		LOG.debug("add monitor name:{}, class:{}", cmdObject.getName(),
				cmdObject.getClass().getName());
		this.monitorMap.put(cmdObject.getName(), cmdObject);
	}

}
