package com.hansight.deploy.shell.env;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hansight.deploy.entity.Host;
import com.hansight.deploy.shell.ShellUtils;
import com.hansight.deploy.shell.base.FileAppend;
import com.hansight.deploy.shell.base.ShellTask;
import com.hansight.deploy.utils.JSchUtil;
import com.hansight.deploy.vo.Status;

public class EnvPathConfig extends FileAppend implements ShellTask {

	private Map<String, String> env;
	private Host host;
	private List<String> exports;

	public EnvPathConfig(Map<String, String> env, Host host,
			List<String> exports) {
		super();
		this.env = env;
		this.host = host;
		this.exports = exports;
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> env = ShellUtils.initGlobal("spark", null, null);
		List<String> exports = new ArrayList<>();
		exports.add("export JAVA_HOME=/opt/java/jdk");
		exports.add("export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/tools.jar");
		exports.add("export SCALA_HOME=/opt/scala");
		exports.add("export PATH=$JAVA_HOME/bin:$PATH:$SCALA_HOME/bin:$SBT_HOME/bin");
		exports.add("export SPARK_HOME=/opt/spark");
		exports.add("export PATH=$SPARK_HOME/bin:$PATH");

		Host host = new Host();
		host.setId(1L);
		host.setName("es122");
		host.setUser("root");
		host.setPassword("hansight@2014");
		host.setPort(22);
		host.setIp("172.16.219.122");
		host.setSession(JSchUtil.getSession(host.getIp(), host.getUser(),
				host.getPassword(), host.getPort()));
		new EnvPathConfig(env, host, exports).execute();
		JSchUtil.close(host.getSession());
	}

	@Override
	public Status execute() {
		String file = "/etc/profile"; // "%{home}/.bashrc"
		String profile = ShellUtils.parser(env, host, file);
		return super.append(env, host, profile, exports);
	}
}
