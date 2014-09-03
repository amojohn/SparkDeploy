package com.hansight.deploy.shell.base;

import java.util.Map;
import java.util.concurrent.Callable;

import com.hansight.deploy.entity.Host;
import com.hansight.deploy.vo.Status;

public abstract class MultiThreadTask implements Callable<Status> {
	protected Host host;
	protected Map<Long, Host> hosts;
	protected Map<String, String> env;

	public MultiThreadTask() {
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public Map<Long, Host> getHosts() {
		return hosts;
	}

	public void setHosts(Map<Long, Host> hosts) {
		this.hosts = hosts;
	}

	public Map<String, String> getEnv() {
		return env;
	}

	public void setEnv(Map<String, String> env) {
		this.env = env;
	}

	protected abstract MultiThreadTask clone();

}
