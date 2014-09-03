package com.hansight.deploy.shell.user;

import com.hansight.deploy.shell.ShellUtils;
import com.hansight.deploy.shell.base.MultiThreadTask;
import com.hansight.deploy.shell.base.ShellTask;
import com.hansight.deploy.utils.JSchUtil;
import com.hansight.deploy.vo.Status;

public class UserDelMTask extends MultiThreadTask implements ShellTask {

	@Override
	public Status execute() {
		String command = ShellUtils.parser(env, host, "userdel -r %{user}");
		String message = ShellUtils.parser(env, host,
				"userdel: user '%{user}' does not exist");
		return JSchUtil.exec(host.getSession(), command, message);
	}

	@Override
	public Status call() throws Exception {
		return execute();
	}

	@Override
	protected UserDelMTask clone() {
		return new UserDelMTask();
	}
}
