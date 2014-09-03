package com.hansight.deploy.core.monitor;

import com.hansight.deploy.core.conf.Configuration;

public interface Monitor {
	String getName();

	String execute(String value, Configuration conf);
}
