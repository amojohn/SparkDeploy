package com.hansight.deploy.core.server;

import com.hansight.deploy.core.conf.Configurable;
import com.hansight.deploy.vo.Status;

public interface RESTServer extends Configurable {

	Status start();

	Status stop();

	Status restart();
}
