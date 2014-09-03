package com.hansight.deploy.shell.user;

import java.io.IOException;

import com.hansight.deploy.shell.base.MultiThreadTask;
import com.hansight.deploy.shell.base.ShellTask;
import com.hansight.deploy.shell.install.YumInstallMTask;
import com.hansight.deploy.utils.FileUtils;
import com.hansight.deploy.utils.JSchUtil;
import com.hansight.deploy.utils.TemplateUtils;
import com.hansight.deploy.vo.Status;

public class PasswordMTask extends MultiThreadTask implements ShellTask {

	@Override
	public Status execute() {
		Status status = new YumInstallMTask("expect").execute();
		if (!status.isSuccess()) {
			return status;
		}
		String command;
		try {
			command = TemplateUtils.parser(
					FileUtils.readShell("shell/setpwd.sh", "\n"), env);
		} catch (IOException e) {
			e.printStackTrace();
			return Status.failure(e);
		}
		String message = "";
		return JSchUtil.exec(host.getSession(), command, message);
	}

	@Override
	public Status call() throws Exception {
		return execute();
	}

	@Override
	protected PasswordMTask clone() {
		return new PasswordMTask();
	}
}
