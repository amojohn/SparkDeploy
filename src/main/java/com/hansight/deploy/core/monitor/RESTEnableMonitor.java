package com.hansight.deploy.core.monitor;

import com.hansight.deploy.core.conf.Configuration;
import com.hansight.deploy.core.conf.Constants;
import com.hansight.deploy.core.daemon.RESTDaemon;

public class RESTEnableMonitor implements Monitor {
	private RESTDaemon rest;

	public RESTEnableMonitor(RESTDaemon rest) {
		super();
		this.rest = rest;
	}

	@Override
	public String getName() {
		return Constants.RESTFUL_ENABLE;
	}

	@Override
	public String execute(String value, Configuration conf) {
		if ("true".equalsIgnoreCase(value)) {
			return rest.start().toString();
		} else {
			return rest.stop().toString();
		}
	}

}
