package com.hansight.deploy.core.monitor;

import com.hansight.deploy.core.conf.Configuration;
import com.hansight.deploy.core.conf.Constants;
import com.hansight.deploy.core.daemon.RESTDaemon;

public class RESTServerImplMonitor implements Monitor {
	private RESTDaemon rest;

	public RESTServerImplMonitor(RESTDaemon rest) {
		super();
		this.rest = rest;
	}

	@Override
	public String getName() {
		return Constants.RESTFUL_SERVER_IMPL;
	}

	@Override
	public String execute(String value, Configuration conf) {
		return rest.restart().toString();
	}

}
