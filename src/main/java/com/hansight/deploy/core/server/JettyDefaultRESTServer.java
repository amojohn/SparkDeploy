package com.hansight.deploy.core.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hansight.deploy.core.conf.Configured;
import com.hansight.deploy.core.conf.Constants;
import com.hansight.deploy.vo.Status;

/**
 * 提供RESTServer的基本框架
 * 
 * @author 宁贯一
 */
public class JettyDefaultRESTServer extends Configured implements RESTServer {
	private static final Logger LOG = LoggerFactory
			.getLogger(JettyDefaultRESTServer.class);
	protected Server server;

	public JettyDefaultRESTServer() {
		LOG.debug("init");
	}

	@Override
	public Status start() {
		if (server != null) {
			return Status.failure("server is null");
		}
		try {
			configServer();
			configConnector();
			configHandler();
			server.start();
			return Status.success();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Status.failure();
	}

	public void configServer() throws Exception {
		server = new Server();

		WebAppContext app = new WebAppContext();
		app.setContextPath(conf.get(Constants.RESTFUL_CONTEXT_PATH, "/"));
		app.setResourceBase(conf.get(Constants.RESTFUL_CONTEXT_PATH,
				"src/main/webapp"));
		app.setDefaultsDescriptor("src/main/resources/webdefault.xml");
		app.setWelcomeFiles(conf.getArray(Constants.RESTFUL_WELCOM_FILES,
				"index.jsp"));

		server.setHandler(app);
	}

	public void configConnector() {
	}

	public void configHandler() {
	}

	public int getPort() {
		return conf.getInt(Constants.RESTFUL_SERVER_PORT, 8091);
	}

	public String getDomain() {
		return conf.get(Constants.RESTFUL_SERVER_DOMAIN, "localhost");
	}

	@Override
	public Status stop() {
		try {
			if (server != null) {
				server.stop();
				return Status.success();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Status.failure();
	}

	@Override
	public Status restart() {
		Status stop = stop();
		Status start = start();
		return Status.success("stop:" + stop.getMessage() + "; start:"
				+ start.getMessage());
	}

}
