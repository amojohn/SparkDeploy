package com.hansight.deploy.core.daemon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hansight.deploy.core.command.Cmd;
import com.hansight.deploy.core.conf.Configuration;
import com.hansight.deploy.core.conf.Configured;
import com.hansight.deploy.core.conf.Constants;

public class CmdDaemon extends Configured {
	private static final Logger LOG = LoggerFactory.getLogger(CmdDaemon.class);
	private Map<String, Cmd> cmdMap = new HashMap<String, Cmd>();
	private String CMD_SEPERATE = " ";
	private boolean exit;

	public CmdDaemon(Configuration conf) {
		this.conf = conf;
	}

	public static void main(String[] args) throws IOException {
		RESTDaemon rest = new RESTDaemon(new Configuration());
		rest.start();

		CmdDaemon cmd = new CmdDaemon(rest.getConf());
		cmd.start();
	}

	public void start() throws IOException {
		String line = null;
		String tip = ">";
		if (conf.getBoolean(Constants.CMD_CONSOLE_ENABLE, true)) {
			System.out.print(tip);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			String result = null;
			while (!exit && (line = in.readLine()) != null) {
				try {
					result = execute(line);
					if (result != null) {
						System.out.println(result);
						System.out.print(tip);
					}
				} catch (Exception e) {
					LOG.warn("error: execut cmd " + line, e);
				}
			}
		}
	}

	public String execute(String line) {
		String name = null;
		int index = 0;
		index = line.indexOf(CMD_SEPERATE);
		if (index > 1) {
			name = line.substring(0, index);
		} else {
			name = line;
		}
		if (name == null) {
			return "unsupport empty command";
		}
		name = name.trim();
		if (name.length() == 0) {
			return "unsupport empty command";
		}
		Cmd cmd = cmdMap.get(name);
		if (cmd == null) {
			return "unknown command :" + name + "\n";
		}
		return cmd.execute(line, conf);
	}

	public void addCmd(Cmd cmd) {
		LOG.info("register cmd {} implment to {}, old is {}", new Object[] {
				cmd.getName(), cmd, cmdMap.get(cmd.getName()) });
		cmdMap.put(cmd.getName(), cmd);
	}

	public String help(String cmdName) {
		Cmd cmd = cmdMap.get(cmdName);
		if (cmd == null) {
			return "unknown command :" + cmdName + "\n";
		}
		return cmd.help();
	}

	public Set<String> getAllCmd() {
		return cmdMap.keySet();
	}

	public String exit() {
		exit = true;
		return Boolean.toString(true);
	}

}
