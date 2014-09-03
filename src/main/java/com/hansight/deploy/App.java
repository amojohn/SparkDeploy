package com.hansight.deploy;

import java.io.IOException;

import com.hansight.deploy.core.command.ExitCmd;
import com.hansight.deploy.core.command.GetAllConfCmd;
import com.hansight.deploy.core.command.GetConfCmd;
import com.hansight.deploy.core.command.HelpCmd;
import com.hansight.deploy.core.command.SetConfCmd;
import com.hansight.deploy.core.conf.Configuration;
import com.hansight.deploy.core.conf.Constants;
import com.hansight.deploy.core.daemon.CmdDaemon;
import com.hansight.deploy.core.daemon.MonitorDaemon;
import com.hansight.deploy.core.daemon.RESTDaemon;
import com.hansight.deploy.core.monitor.CmdConsoleEnableMonitor;
import com.hansight.deploy.core.monitor.CmdRESTfulEnableMonitor;
import com.hansight.deploy.core.monitor.RESTEnableMonitor;
import com.hansight.deploy.core.monitor.RESTPortMonitor;
import com.hansight.deploy.core.monitor.RESTServerImplMonitor;

public class App {
	public static MonitorDaemon monitor;
	public static CmdDaemon cmd;
	public static RESTDaemon rest;

	public static void main(String[] args) {
		monitor = new MonitorDaemon();
		// conf
		Configuration conf = new Configuration();
		conf.set(Constants.RESTFUL_SERVER_IMPL,
				"com.hansight.deploy.core.server.JettyWebRESTServer");
		conf.setMonitorDaemon(monitor);
		monitor.setConf(conf);
		// rest
		rest = new RESTDaemon(conf);
		// cmd
		cmd = new CmdDaemon(conf);
		// init
		initMonitor(monitor, rest, cmd);
		initCmd(monitor, rest, cmd);
		rest.start();
		try {
			cmd.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void initMonitor(MonitorDaemon monitor, RESTDaemon rest,
			CmdDaemon cmd) {
		monitor.addMonitor(new RESTEnableMonitor(rest));
		monitor.addMonitor(new RESTPortMonitor(rest));
		monitor.addMonitor(new RESTServerImplMonitor(rest));
		monitor.addMonitor(new CmdRESTfulEnableMonitor(rest));
		monitor.addMonitor(new CmdConsoleEnableMonitor(cmd));
	}

	private static void initCmd(MonitorDaemon monitor, RESTDaemon rest,
			CmdDaemon cmd) {
		cmd.addCmd(new ExitCmd(cmd, rest));
		cmd.addCmd(new GetAllConfCmd());
		cmd.addCmd(new GetConfCmd());
		cmd.addCmd(new HelpCmd(cmd));
		cmd.addCmd(new SetConfCmd());
	}

}