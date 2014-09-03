package com.hansight.deploy.shell.user;

import com.hansight.deploy.shell.base.MultiThreadTask;
import com.hansight.deploy.shell.base.ShellTask;
import com.hansight.deploy.utils.JSchUtil;
import com.hansight.deploy.utils.TemplateUtils;
import com.hansight.deploy.vo.Status;

public class UserAddMTask extends MultiThreadTask implements ShellTask {

	@Override
	public Status execute() {
		String command = TemplateUtils.parser("useradd %{user} -g %{group}",
				env);
		String message = TemplateUtils.parser(
				"useradd: user '%{user}' already exists", env);
		return JSchUtil.exec(host.getSession(), command, message);
	}

	@Override
	public Status call() throws Exception {
		return execute();
	}

	@Override
	protected UserAddMTask clone() {
		return new UserAddMTask();
	}
}
