package com.hansight.deploy.shell.user;

import com.hansight.deploy.shell.base.MultiThreadTask;
import com.hansight.deploy.shell.base.ShellTask;
import com.hansight.deploy.utils.JSchUtil;
import com.hansight.deploy.utils.TemplateUtils;
import com.hansight.deploy.vo.Status;

public class GroupAddMTask extends MultiThreadTask implements ShellTask {

	@Override
	public Status execute() {
		String command = TemplateUtils.parser("groupadd %{group} ", env);
		String message = TemplateUtils.parser(
				"groupadd: group '%{group}' already exists", env);
		return JSchUtil.exec(host.getSession(), command, message);
	}

	@Override
	public Status call() throws Exception {
		return execute();
	}

	@Override
	protected GroupAddMTask clone() {
		return new GroupAddMTask();
	}
}
