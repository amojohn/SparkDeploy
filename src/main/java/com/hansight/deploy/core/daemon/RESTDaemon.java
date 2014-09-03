package com.hansight.deploy.core.daemon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hansight.deploy.core.conf.Configuration;
import com.hansight.deploy.core.conf.Configured;
import com.hansight.deploy.core.conf.Constants;
import com.hansight.deploy.core.server.RESTServer;
import com.hansight.deploy.vo.Status;

public class RESTDaemon extends Configured {
	private static final Logger LOG = LoggerFactory.getLogger(RESTDaemon.class);
	private RESTServer server;

	public RESTDaemon(Configuration conf) {
		this.conf = conf;
		String impl = conf.get(Constants.RESTFUL_SERVER_IMPL,
				"com.hansight.deploy.core.server.JettyDefaultRESTServer");
		try {
			server = (RESTServer) Class.forName(impl).newInstance();
			server.setConf(conf);
		} catch (Exception e) {
			LOG.error("rest server instance failure", e);
		}
	}

	public Status start() {
		if (!enable()) {
			return Status.failure("disabled");
		}
		return server.start();
	}

	public Status stop() {
		return server.stop();
	}

	public Status restart() {
		return server.restart();
	}

	public boolean enable() {
		return conf.getBoolean(Constants.RESTFUL_ENABLE, true);
	}

	public static void main(String[] args) {
		RESTDaemon daemon = new RESTDaemon(new Configuration());
		daemon.start();
	}

}
