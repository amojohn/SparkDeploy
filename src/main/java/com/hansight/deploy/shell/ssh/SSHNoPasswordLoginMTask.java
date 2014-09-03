package com.hansight.deploy.shell.ssh;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import com.hansight.deploy.entity.Host;
import com.hansight.deploy.shell.ShellUtils;
import com.hansight.deploy.shell.base.MultiThreadTask;
import com.hansight.deploy.shell.base.ShellTask;
import com.hansight.deploy.utils.JSchUtil;
import com.hansight.deploy.utils.TemplateUtils;
import com.hansight.deploy.vo.Status;
import com.jcraft.jsch.Session;

public class SSHNoPasswordLoginMTask extends MultiThreadTask implements ShellTask {

	@Override
	public Status call() throws Exception {
		return execute();
	}

	@Override
	public Status execute() {
		Status status = null;
		String pub = "%{home}/.ssh/id_rsa.pub";
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		status = JSchUtil.download(host.getSession(),
				ShellUtils.parser(env, host, pub), out);
		for (Map.Entry<Long, Host> other : hosts.entrySet()) {
			Session otherSession = other.getValue().getSession();
			String tmpPub = pub + host.getIp();
			status = JSchUtil.upload(otherSession,
					ShellUtils.parser(env, host, tmpPub),
					new ByteArrayInputStream(out.toByteArray()));
			if (!status.isSuccess()) {
				break;
			}
			String author = "cat " + tmpPub
					+ " >> %{home}/.ssh/authorized_keys;"
			// + "rm -rf " + tmpPub
			;
			status = JSchUtil.exec(otherSession,
					TemplateUtils.parser(author, env));
			if (!status.isSuccess()) {
				break;
			}
		}
		return status;
	}

	@Override
	protected MultiThreadTask clone() {
		return new SSHNoPasswordLoginMTask();
	}
}
