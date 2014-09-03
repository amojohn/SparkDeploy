package com.hansight.deploy.core.command;

import com.hansight.deploy.core.conf.Configuration;

public class SetConfCmd extends AbstraceCmd implements Cmd {

	@Override
	public String getName() {
		return "setConf";
	}

	@Override
	public String execute(String line, Configuration conf) {
		int index = line.indexOf(' ');
		int sec = line.indexOf('=');
		if (index > 0 && sec > getName().length() + 2) {
			String name = line.substring(index, sec);
			String value = line.substring(sec + 1);
			
			return "" + conf.set(name, value);
		}
		return Boolean.toString(true);
	}

}
