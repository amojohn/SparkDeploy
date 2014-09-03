package com.hansight.deploy.shell.deploy;

import java.util.List;

import com.hansight.deploy.shell.ShellUtils;
import com.hansight.deploy.shell.base.MTaskExecutor;
import com.hansight.deploy.shell.firewall.FirewallSecurityCloseMTask;
import com.hansight.deploy.shell.hosts.HostnameConfigMTask;
import com.hansight.deploy.shell.hosts.HostsMappingMTask;
import com.hansight.deploy.shell.ssh.SSHNoPasswordLoginMTask;
import com.hansight.deploy.shell.user.GroupAddMTask;
import com.hansight.deploy.shell.user.PasswordMTask;
import com.hansight.deploy.shell.user.UserAddMTask;
import com.hansight.deploy.shell.user.UserDelMTask;
import com.hansight.deploy.vo.Status;

public class DeployBase {
	public static List<Status> base(MTaskExecutor executor) {
		// iptables
		List<Status> statusList = executor
				.launch(new FirewallSecurityCloseMTask());
		if (!ShellUtils.isOK(statusList)) {
			return statusList;
		}
		// hosts
		statusList = executor.launch(new HostsMappingMTask());
		if (!ShellUtils.isOK(statusList)) {
			return statusList;
		}
		// hostname
		statusList = executor.launch(new HostnameConfigMTask());
		if (!ShellUtils.isOK(statusList)) {
			return statusList;
		}
		if (!"root".equals(executor.getEnv().get("user"))) {
			// 1. add user spark
			// user del
			statusList = executor.launch(new UserDelMTask());
			if (!ShellUtils.isOK(statusList)) {
				return statusList;
			}
			// group add
			statusList = executor.launch(new GroupAddMTask());
			if (!ShellUtils.isOK(statusList)) {
				return statusList;
			}
			// user add
			// TODO add home
			statusList = executor.launch(new UserAddMTask());
			if (!ShellUtils.isOK(statusList)) {
				return statusList;
			}
			// install expect && set passwd
			statusList = executor.launch(new PasswordMTask());
			if (!ShellUtils.isOK(statusList)) {
				return statusList;
			}
		}
		// ssh
		statusList = executor.launch(new SSHNoPasswordLoginMTask());
		if (!ShellUtils.isOK(statusList)) {
			return statusList;
		}
		return statusList;
	}
}
