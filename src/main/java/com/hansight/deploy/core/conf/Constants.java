package com.hansight.deploy.core.conf;

public class Constants {
	public static final String CONF_RESET_FILE = "conf.reset.file";
	public static final String CONF_RESET_JUST_ONCE = "conf.reset.just.once";
	public static final String CONF_INCLUDE_FILES = "conf.include.files";
	

	public static final String CMD_CONSOLE_ENABLE = "cmd.console.enable";
	public static final String CMD_CLASS_BASE = "cmd.class.base";
	public static final String HARD_CMD_COMMAND_CLASS_BASE = "org.yuqieshi.frame.cmd.command.ExitCmd,"
			+ "org.yuqieshi.frame.cmd.command.SetConfCmd,"
			+ "org.yuqieshi.frame.cmd.command.GetConfCmd,"
			+ "org.yuqieshi.frame.cmd.command.GetAllConfCmd,"
			+ "org.yuqieshi.frame.cmd.command.HelpCmd";
	public static final String CMD_COMMAND_CLASS_EXT = "cmd.class.ext";

	public static final String RESTFUL_ENABLE = "restful.enable";
	public static final String RESTFUL_SERVER_PORT = "restful.server.port";
	public static final String RESTFUL_SERVER_IMPL = "restful.server.impl";
	public static final String RESTFUL_SERVER_DOMAIN = "restful.server.domain";
	public static final String RESTFUL_RESOURCE_CLASS_BASE = "restful.resource.class.base";
	public static final String RESTFUL_RESOURCE_CLASS_EXT = "restful.resource.class.ext";
	public static final String HARD_RESTFUL_RESOURCES_CLASS_BASE = "org.yuqieshi.frame.conf.resources.ConfResource,"
			+ "org.yuqieshi.frame.cmd.resources.CmdResource,";
	public static final String RESTFUL_CONTEXT_PATH = "restful.context.path";
	public static final String RESTFUL_WELCOM_FILES = "restful.welcom.pages";
	
	// monitor
	public static final String CONF_MONITOR_CLASS_BASE = "conf.monitor.class.base";
	public static final String HARD_CONF_MONITOR_CLASS_BASE = "org.yuqieshi.frame.conf.monitor.RESTEnableMonitor,"
			+ "org.yuqieshi.frame.conf.monitor.RESTPortMonitor,"
			+ "org.yuqieshi.frame.conf.monitor.RESTServerImplMonitor";
	public static final String CONF_MONITOR_CLASS_EXT = "conf.monitor.class.ext";
	
	public static final String CMD_MONITOR_CLASS_BASE = null;
	public static final String CMD_MONITOR_CLASS_EXT = null;
	public static final String HARD_CMD_MONITOR_CLASS_BASE = null;

}
