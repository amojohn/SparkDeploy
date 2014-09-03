package com.hansight.deploy.core.command;

import com.hansight.deploy.core.conf.Configuration;

public class GetConfCmd extends AbstraceCmd implements Cmd {

	@Override
	public String getName() {
		return "getConf";
	}

	@Override
	public String execute(String line, Configuration conf) {
		int index = line.indexOf(' ');
		if (index > 0) {
			String name = line.substring(index);
			return conf.get(name);
		}
		return "unsupport empty name";
	}

}
