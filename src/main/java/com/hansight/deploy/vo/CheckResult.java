package com.hansight.deploy.vo;

import java.util.List;

public class CheckResult {
	private STATUS result;
	private List<String> hosts;

	public STATUS getResult() {
		return result;
	}

	public void setResult(STATUS result) {
		this.result = result;
	}

	public List<String> getHosts() {
		return hosts;
	}

	public void setHosts(List<String> hosts) {
		this.hosts = hosts;
	}

	public static enum STATUS {
		HOST, CHECK, AUTH, SUCCESS
	}
}