package org.hansight.deploy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hansight.deploy.entity.Host;
import com.hansight.deploy.shell.ShellUtils;
import com.hansight.deploy.shell.base.MTaskExecutor;
import com.hansight.deploy.shell.soft.JDKInstallMTask;
import com.hansight.deploy.shell.soft.ScalaInstallMTask;
import com.hansight.deploy.utils.IPUtils;
import com.hansight.deploy.utils.JSchUtil;
import com.hansight.deploy.vo.Status;
import com.jcraft.jsch.Session;

public class DeployBaseTest {
	public static void main(String[] args) {
		// TODO
		Map<Long, Host> hosts = null;
		MTaskExecutor executor = null;
		try {
			// hosts
			List<String> ips = IPUtils.parse("172.16.219.[243-248]");
			List<Host> hostsList = new ArrayList<>();
			// ID for test
			long id = 1;
			for (String ip : ips) {
				Host host = new Host();
				host.setId(id++);
				host.setName("hdp" + ip.substring(ip.lastIndexOf(".") + 1));
				host.setUser("root");
				host.setPassword("hansight@2014");
				host.setPort(22);
				host.setIp(ip);
				hostsList.add(host);
			}
			hosts = ShellUtils.initSession(hostsList);
			Map<String, String> env = ShellUtils.initGlobal("root", null, null);
			executor = new MTaskExecutor(hosts, env);
			// base
			// DeployBase.base(executor);
			List<Status> statusList = new ArrayList<>();
			statusList = executor.launch(new JDKInstallMTask());
			if (!ShellUtils.isOK(statusList)) {
				ShellUtils.print(statusList);
				return;
			}
			statusList = executor.launch(new ScalaInstallMTask());
			if (!ShellUtils.isOK(statusList)) {
				ShellUtils.print(statusList);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close
			for (final Map.Entry<Long, Host> sub : hosts.entrySet()) {
				final Session session = sub.getValue().getSession();
				JSchUtil.close(session);
			}
			executor.close();
		}

	}
}
