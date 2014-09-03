package com.hansight.deploy.shell.env;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hansight.deploy.entity.Host;
import com.hansight.deploy.shell.ShellUtils;
import com.hansight.deploy.shell.base.MTaskExecutor;
import com.hansight.deploy.shell.base.MultiThreadTask;
import com.hansight.deploy.utils.IPUtils;
import com.hansight.deploy.utils.JSchUtil;
import com.hansight.deploy.vo.Status;
import com.jcraft.jsch.Session;

public class EnvPathConfigMTask extends MultiThreadTask {
	private EnvPathConfig epc;
	private List<String> exports;

	public EnvPathConfigMTask(List<String> exports) {
		this.exports = exports;
	}

	@Override
	public Status call() throws Exception {
		this.epc = new EnvPathConfig(env, host, exports);
		return epc.execute();
	}

	public static void main(String[] args) throws Exception {
		List<String> ips = IPUtils.parse("172.16.219.[122-124]");
		long id = 1;
		List<Host> hostsList = new ArrayList<>();
		for (String ip : ips) {
			Host host = new Host();
			host.setId(id++);
			host.setIp(ip);
			host.setName("es" + ip.substring(ip.lastIndexOf(".") + 1));
			host.setUser("root");
			host.setPassword("hansight@2014");
			host.setPort(22);
			hostsList.add(host);
		}
		Map<Long, Host> map = ShellUtils.initSession(hostsList);
		Map<String, String> env = ShellUtils.initGlobal("spark", null, null);
		List<String> exports = new ArrayList<>();
		exports.add("export JAVA_HOME=/opt/java/jdk");
		exports.add("export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/tools.jar");
		exports.add("export SCALA_HOME=/opt/scala");
		exports.add("export PATH=$JAVA_HOME/bin:$PATH:$SCALA_HOME/bin:$SBT_HOME/bin");
		exports.add("export SPARK_HOME=/opt/spark");
		exports.add("export PATH=$SPARK_HOME/bin:$PATH");
		MTaskExecutor executor = new MTaskExecutor(map, env);
		EnvPathConfigMTask cep = new EnvPathConfigMTask(exports);
		List<Status> statusList = executor.launch(cep);
		System.out.println(ShellUtils.isOK(statusList));
		// close
		for (final Map.Entry<Long, Host> sub : map.entrySet()) {
			final Session session = sub.getValue().getSession();
			JSchUtil.close(session);
		}
	}

	@Override
	protected EnvPathConfigMTask clone() {
		return new EnvPathConfigMTask(exports);
	}

}
