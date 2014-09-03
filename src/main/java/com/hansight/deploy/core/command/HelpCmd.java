package com.hansight.deploy.core.command;

import com.hansight.deploy.core.conf.Configuration;
import com.hansight.deploy.core.daemon.CmdDaemon;

public class HelpCmd extends AbstraceCmd implements Cmd {
	private CmdDaemon daemon;

	public HelpCmd(CmdDaemon cmd) {
		super();
		this.daemon = cmd;
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String execute(String line, Configuration conf) {
		String name = null;
		if (line.length() > getName().length()) {
			name = line.substring(getName().length()).trim();
		}
		if (name != null && name.length() != 0) {
			daemon.help(name);
		}
		return help();
	}

	@Override
	public String help() {
		StringBuffer sb = new StringBuffer();
		sb.append("Usage: help [cmd]\n");
		sb.append("cmd:\n");
		for (String cmd : daemon.getAllCmd()) {
			sb.append("\t").append(cmd);
		}
		return sb.toString();
	}
}
