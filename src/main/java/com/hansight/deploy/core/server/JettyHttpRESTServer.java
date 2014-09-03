package com.hansight.deploy.core.server;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hansight.deploy.core.conf.Configuration;
import com.hansight.deploy.core.conf.Constants;
import com.hansight.deploy.core.daemon.RESTDaemon;

/**
 * 只能使用jersey，不能使用jsp和servlet
 * 
 * @author 宁贯一
 */
public class JettyHttpRESTServer extends JettyDefaultRESTServer {
	private Logger LOG = LoggerFactory.getLogger(JettyHttpRESTServer.class);
	public static String WEB_CLASSES_RESOURCE = "web.classes.resource";

	@Override
	public void configServer() {
		URI baseUri = UriBuilder.fromUri("http://" + getDomain() + "/")
				.port(getPort()).build();
		Set<Class<?>> classes = new HashSet<Class<?>>();
		String resources = conf.get(Constants.RESTFUL_RESOURCE_CLASS_BASE,
				Constants.HARD_RESTFUL_RESOURCES_CLASS_BASE);
		add(resources, classes);
		String extResources = conf.get(Constants.RESTFUL_RESOURCE_CLASS_EXT);
		add(extResources, classes);
		ResourceConfig config = new ResourceConfig(classes);

		LOG.debug("config:{}", config);
		server = JettyHttpContainerFactory.createServer(baseUri, config);
	}

	private void add(String resources, Set<Class<?>> classes) {
		if (resources != null) {
			String[] arr = resources.split(",");
			for (String resource : arr) {
				try {
					classes.add(Class.forName(resource.trim()));
				} catch (ClassNotFoundException e) {
					LOG.warn("jersey resource not found:{}", resource.trim());
				}
			}
		}
	}

	public static void main(String[] args) {
		Configuration conf = new Configuration();
		conf.set(Constants.RESTFUL_SERVER_IMPL,
				"org.yuqieshi.frame.rest.server.HttpWebJettyRESTServer");
		RESTDaemon rest = new RESTDaemon(conf);
		
		rest.start();
	}
}
