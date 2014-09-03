package com.hansight.deploy.conf;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(RequestContextFilter.class);
		packages("com.hansight.deploy.core.resources");
		packages("com.hansight.deploy.resources");
		// register(LoggingFilter.class);

		// register(UserResource.class);
	}
}
