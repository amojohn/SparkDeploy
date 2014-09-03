package com.hansight.deploy.resources;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.hansight.deploy.entity.Host;
import com.hansight.deploy.service.HostService;
import com.hansight.deploy.shell.ShellUtils;
import com.hansight.deploy.shell.base.MTaskExecutor;
import com.hansight.deploy.shell.deploy.DeployBase;
import com.hansight.deploy.shell.soft.JDKInstallMTask;
import com.hansight.deploy.shell.soft.ScalaInstallMTask;
import com.hansight.deploy.shell.soft.SparkInstallMTask;
import com.hansight.deploy.utils.IPUtils;
import com.hansight.deploy.utils.JSchUtil;
import com.hansight.deploy.vo.CheckResult;
import com.hansight.deploy.vo.CheckResult.STATUS;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Controller
@Scope("request")
@Path("/deploy/spark")
public class DeploySparkResources {
	private static final Logger LOG = LoggerFactory
			.getLogger(DeploySparkResources.class);
	@Autowired
	private HostService hostService;

	@GET
	public String test() {
		return "test";
	}

	@Path("/install")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String deploy(
			@FormParam("hostIp") String hostIp,
			@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("hostName") String hostName,
			@FormParam("sshPort") Integer sshPort,
			@FormParam("sparkUser") String sparkUser,
			@FormParam("step3_master") String master,
			@FormParam("SPARK_MASTER_PORT") String SPARK_MASTER_PORT,
			@FormParam("SPARK_MASTER_WEBUI_PORT") String SPARK_MASTER_WEBUI_PORT,
			@FormParam("SPARK_MASTER_OPTS") String SPARK_MASTER_OPTS,
			@FormParam("SPARK_DAEMON_JAVA_OPTS") String SPARK_DAEMON_JAVA_OPTS,
			@FormParam("SPARK_WORKER_CORES") String SPARK_WORKER_CORES,
			@FormParam("SPARK_WORKER_MEMORY") String SPARK_WORKER_MEMORY,
			@FormParam("SPARK_WORKER_PORT") String SPARK_WORKER_PORT,
			@FormParam("SPARK_WORKER_WEBUI_PORT") String SPARK_WORKER_WEBUI_PORT,
			@FormParam("SPARK_WORKER_INSTANCES") String SPARK_WORKER_INSTANCES,
			@FormParam("SPARK_WORKER_DIR") String SPARK_WORKER_DIR,
			@FormParam("SPARK_WORKER_OPTS") String SPARK_WORKER_OPTS,
			@FormParam("SPARK_HISTORY_OPTS") String SPARK_HISTORY_OPTS,
			@FormParam("SPARK_PUBLIC_DNS") String SPARK_PUBLIC_DNS)

	{
		Gson gson = new Gson();
		CheckResult cr = new CheckResult();
		// check input
		if (hostName == null || sparkUser == null || hostIp == null
				|| username == null || password == null || sshPort == null
				|| master == null) {
			cr.setResult(STATUS.CHECK);
			return gson.toJson(cr);
		}
		// TODO
		Map<Long, Host> hosts = null;
		try {
			// hosts
			List<String> ips = IPUtils.parse(hostIp);
			List<Host> hostsList = new ArrayList<>();
			for (String ip : ips) {
				Host host = new Host();
				host.setName(hostName + ip.substring(ip.lastIndexOf(".") + 1));
				host.setUser(username);
				host.setPassword(password);
				host.setPort(sshPort);
				host.setIp(ip);
				hostsList.add(host);
			}
			hostService.save(hostsList);
			hosts = ShellUtils.initSession(hostsList);
			Map<String, String> env = ShellUtils.initGlobal(sparkUser, null,
					sparkUser);
			String path = new File(".").getAbsolutePath();
			MTaskExecutor executor = new MTaskExecutor(hosts, env);
			// base
			DeployBase.base(executor);
			executor.launch(new JDKInstallMTask());
			executor.launch(new ScalaInstallMTask());
			executor.launch(new SparkInstallMTask(master));

			// close
			for (final Map.Entry<Long, Host> sub : hosts.entrySet()) {
				final Session session = sub.getValue().getSession();
				JSchUtil.close(session);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close session
			for (final Map.Entry<Long, Host> sub : hosts.entrySet()) {
				final Session session = sub.getValue().getSession();
				JSchUtil.close(session);
			}
		}

		cr.setResult(STATUS.SUCCESS);
		return gson.toJson(cr);
	}

	@Path("/check")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String parser(@FormParam("ips") String inIps,
			@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("sshPort") Integer sshPort) {
		Gson gson = new Gson();
		CheckResult cr = new CheckResult();
		if (inIps == null) {
			cr.setResult(CheckResult.STATUS.HOST);
			return gson.toJson(cr);
		}
		List<String> hosts = IPUtils.parse(inIps);
		boolean check = checkHost(hosts, username, password, sshPort);
		if (!check) {
			cr.setResult(CheckResult.STATUS.AUTH);
		} else {
			cr.setResult(CheckResult.STATUS.SUCCESS);
		}
		cr.setHosts(hosts);
		return gson.toJson(cr);
	}

	public boolean checkHost(List<String> hosts, String userName,
			String password, int port) {
		LOG.debug("username:{}, password:{}, port:{}", userName, password, port);
		for (String host : hosts) {
			Session session = null;
			try {
				session = JSchUtil.getSession(host, userName, password, port);
			} catch (JSchException e) {
				e.printStackTrace();
				return false;
			} finally {
				JSchUtil.close(session);
			}
		}
		return true;
	}

	public HostService getHostService() {
		return hostService;
	}

	public void setHostService(HostService hostService) {
		this.hostService = hostService;
	}
}
