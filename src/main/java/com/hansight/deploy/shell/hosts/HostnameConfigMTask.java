package com.hansight.deploy.shell.hosts;

import com.hansight.deploy.shell.ShellUtils;
import com.hansight.deploy.shell.base.MultiThreadTask;
import com.hansight.deploy.shell.base.ShellTask;
import com.hansight.deploy.shell.install.YumInstallMTask;
import com.hansight.deploy.utils.JSchUtil;
import com.hansight.deploy.utils.TemplateUtils;
import com.hansight.deploy.vo.Status;

public class HostnameConfigMTask extends MultiThreadTask implements ShellTask {

	@Override
	public Status call() throws Exception {
		return execute();
	}

	@Override
	public Status execute() {

		YumInstallMTask yum = new YumInstallMTask("openssh-clients");
		yum.setEnv(env);
		yum.setHosts(hosts);
		yum.setHost(host);
		Status status = yum.execute();
		if (!status.isSuccess()) {
			return status;
		}
		String command = "hostname %{host_name}; echo \"NETWORKING=yes\nHOSTNAME=%{host_name}\" > /etc/sysconfig/network";
		command = ShellUtils.parser(env, host, command);
		return JSchUtil.exec(host.getSession(), TemplateUtils.parser(command,  env));
	}

	@Override
	protected MultiThreadTask clone() {
		return new HostnameConfigMTask();
	}
}
