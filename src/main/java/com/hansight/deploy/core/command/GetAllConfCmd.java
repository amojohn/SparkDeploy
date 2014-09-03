package com.hansight.deploy.core.command;

import java.util.Map;

import com.hansight.deploy.core.conf.Configuration;

public class GetAllConfCmd extends AbstraceCmd implements Cmd {

	@Override
	public String getName() {
		return "getAllConf";
	}

	@Override
	public String execute(String line, Configuration conf) {
		Map<String, String> map = conf.getAll();
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue());
		}
		return sb.toString();
	}

}
