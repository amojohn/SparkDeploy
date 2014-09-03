package com.hansight.deploy.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateUtils {
	private static final Logger LOG = LoggerFactory.getLogger(TemplateUtils.class);
	public static String parser(String command, Map<String, String> env) {
		if (command == null) {
			return null;
		}
		command = command.replaceAll("\r\n", "\n");
		String msg = command;
		LOG.debug("command:{}, env:{}", command, env);
		for (Map.Entry<String, String> entry : env.entrySet()) {
			msg = msg.replaceAll("%\\{" + entry.getKey() + "\\}",
					entry.getValue());
		}
		return msg;
	}
}
