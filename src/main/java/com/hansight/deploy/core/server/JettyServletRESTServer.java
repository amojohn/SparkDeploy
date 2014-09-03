package com.hansight.deploy.core.server;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.jetty.servlet.JettyWebContainerFactory;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hansight.deploy.core.conf.Configuration;
import com.hansight.deploy.core.conf.Constants;
import com.hansight.deploy.core.daemon.RESTDaemon;

/**
 * 只能使用jersey和servlet，不能使用jsp
 * 
 * @author 宁贯一
 * 
 */
public class JettyServletRESTServer extends JettyDefaultRESTServer {
	private Logger LOG = LoggerFactory.getLogger(JettyServletRESTServer.class);
	public static String WEB_CLASSES_RESOURCE = "web.classes.resource";

	@Override
	public void configServer() throws Exception {
		String packages = "org.yuqieshi.frame.conf.resources"
				+ ",org.yuqieshi.frame.cmd.resources"
				+ ",org.yuqieshi.frame.web.servlet";
		String packageName = "jersey.config.server.provider.packages";
		String recursiveName = "jersey.config.server.provider.scanning.recursive";

		URI baseUri = UriBuilder.fromUri("http://" + getDomain() + "/")
				.port(getPort()).build();
		ServletContainer jerseyApp = new ServletContainer();
		Map<String, String> init = new HashMap<String, String>();
		init.put(packageName, packages);
		init.put(recursiveName, "false");
		LOG.debug("base uri:{}", baseUri);

		Map<String, String> contextInit = new HashMap<String, String>();
		server = JettyWebContainerFactory.create(baseUri, jerseyApp, init,
				contextInit);
	}

	public static void main(String[] args) {
		Configuration conf = new Configuration();
		conf.set(Constants.RESTFUL_SERVER_IMPL,
				"org.yuqieshi.frame.rest.server.JerseyJettyRESTServer");
		RESTDaemon rest = new RESTDaemon(conf);
		rest.start();
	}
}
