package com.hansight.deploy.core.conf;

public class Configured implements Configurable {
	protected Configuration conf;

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	@Override
	public Configuration getConf() {
		return this.conf;
	}

}
