package com.hansight.deploy.core.monitor;

import com.hansight.deploy.core.conf.Configuration;
import com.hansight.deploy.core.conf.Constants;
import com.hansight.deploy.core.daemon.RESTDaemon;

public class RESTPortMonitor implements Monitor {
	private RESTDaemon rest;

	public RESTPortMonitor(RESTDaemon rest) {
		super();
		this.rest = rest;
	}
	@Override
	public String getName() {
		return Constants.RESTFUL_SERVER_PORT;
	}


	@Override
	public String execute(String value, Configuration conf) {
		return rest.restart().toString();
	}

}
