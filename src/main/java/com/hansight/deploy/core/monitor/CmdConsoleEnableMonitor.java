package com.hansight.deploy.core.monitor;

import com.hansight.deploy.core.conf.Configuration;
import com.hansight.deploy.core.conf.Constants;
import com.hansight.deploy.core.daemon.CmdDaemon;

public class CmdConsoleEnableMonitor implements Monitor {
	private CmdDaemon cmd;

	public CmdConsoleEnableMonitor(CmdDaemon cmd) {
		super();
		this.cmd = cmd;
	}

	@Override
	public String getName() {
		return Constants.CMD_CONSOLE_ENABLE;
	}

	@Override
	public String execute(String value, Configuration conf) {
		if ("true".equals(value)) {
			return "unsupoort enable cmd, only support disable.";
		} else {
			return cmd.exit();
		}
	}

}
