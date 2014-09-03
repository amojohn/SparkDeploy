package com.hansight.deploy.core.monitor;

import com.hansight.deploy.core.conf.Configuration;
import com.hansight.deploy.core.conf.Constants;
import com.hansight.deploy.core.daemon.RESTDaemon;

public class CmdRESTfulEnableMonitor implements Monitor {
	private RESTDaemon rest;

	public CmdRESTfulEnableMonitor(RESTDaemon rest) {
		super();
		this.rest = rest;
	}

	@Override
	public String getName() {
		return Constants.CMD_CONSOLE_ENABLE;
	}

	@Override
	public String execute(String value, Configuration conf) {
		return rest.restart().toString();
	}

}
