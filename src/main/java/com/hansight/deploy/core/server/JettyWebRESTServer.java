package com.hansight.deploy.core.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * 支持jersey、jsp、servlet
 * 
 * @author 宁贯一
 * 
 */
public class JettyWebRESTServer extends JettyDefaultRESTServer {

	@Override
	public void configServer() throws Exception {

		server = new Server(getPort());
		WebAppContext app = new WebAppContext();
		app.setContextPath("/");
		app.setResourceBase("src/main/webapp");
		app.setDefaultsDescriptor("src/main/resources/webdefault.xml");

		server.setHandler(app);
	}

}
