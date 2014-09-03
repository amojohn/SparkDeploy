package com.hansight.deploy.shell.firewall;

import java.util.Map;

import com.hansight.deploy.entity.Host;
import com.hansight.deploy.shell.ShellUtils;
import com.hansight.deploy.shell.base.FileReplace;
import com.hansight.deploy.shell.base.ShellTask;
import com.hansight.deploy.utils.JSchUtil;
import com.hansight.deploy.vo.Status;

public class FirewallSecurityClose extends FileReplace implements ShellTask {
	private Host host;
	private final Map<String, String> env;

	public FirewallSecurityClose(Host host, final Map<String, String> env) {
		this.env = env;
		this.host = host;
	}

	@Override
	public String replace(String line) {
		if (line.startsWith("SELINUX=")) {
			return "SELINUX=disabled";
		} else {
			return line;
		}
	}

	@Override
	public Status execute() {
		try {
			String selinuxFile = "/etc/selinux/config";
			String command = "service iptables stop; chkconfig iptables off; setenforce 0;";
			String message = "setenforce: SELinux is disabled";
			command = ShellUtils.parser(env, host, command);
			Status status = JSchUtil.exec(host.getSession(), command, message);
			if (!status.isSuccess()) {
				return status;
			}
			return execute(env, host, selinuxFile);
		} catch (Exception e) {
			e.printStackTrace();
			return Status.failure(e);
		}
	}
}
