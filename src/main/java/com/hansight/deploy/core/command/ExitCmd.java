package com.hansight.deploy.core.command;

import com.hansight.deploy.core.conf.Configuration;
import com.hansight.deploy.core.daemon.CmdDaemon;
import com.hansight.deploy.core.daemon.RESTDaemon;

public class ExitCmd extends AbstraceCmd implements Cmd {
	private CmdDaemon cmd;
	private RESTDaemon rest;

	public ExitCmd(CmdDaemon cmd, RESTDaemon rest) {
		super();
		this.cmd = cmd;
		this.rest = rest;
	}

	@Override
	public String getName() {
		return "exit";
	}

	@Override
	public String execute(String line, Configuration conf) {
		cmd.exit();
		rest.stop();
		return "exit success";
	}

}
