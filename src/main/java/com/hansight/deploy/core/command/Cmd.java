package com.hansight.deploy.core.command;

import com.hansight.deploy.core.conf.Configuration;

public interface Cmd {

	String getName();

	String execute(String line, Configuration conf);

	String help();
}
