package com.hansight.deploy.shell.firewall;

import com.hansight.deploy.shell.base.MultiThreadTask;
import com.hansight.deploy.vo.Status;

public class FirewallSecurityCloseMTask extends MultiThreadTask {
	private FirewallSecurityClose operate;

	public FirewallSecurityCloseMTask() {
	}

	@Override
	public Status call() throws Exception {
		this.operate = new FirewallSecurityClose(host, env);
		return this.operate.execute();
	}

	@Override
	protected FirewallSecurityCloseMTask clone() {
		return new FirewallSecurityCloseMTask();
	}
}
