package com.hansight.deploy.shell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.hansight.deploy.entity.Host;
import com.hansight.deploy.utils.JSchUtil;
import com.hansight.deploy.utils.TemplateUtils;
import com.hansight.deploy.vo.Status;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ShellUtils {
	public static List<Status> execute(final String command,
			final String message, Map<Long, Host> hosts,
			final Map<String, String> env) throws JSchException, IOException,
			InterruptedException, ExecutionException {
		ExecutorService pool = Executors.newFixedThreadPool(hosts.size());

		List<Future<Status>> futureList = new ArrayList<>();
		for (Map.Entry<Long, Host> sub : hosts.entrySet()) {
			final Host host = sub.getValue();
			Future<Status> future = pool.submit(new Callable<Status>() {
				public Status call() throws Exception {
					String parsedCommand = parser(env, host, command);
					return JSchUtil.exec(host.getSession(), parsedCommand,
							TemplateUtils.parser(message, env));
				}
			});
			futureList.add(future);
		}
		pool.shutdown();
		List<Status> statusList = new ArrayList<>();
		for (Future<Status> future : futureList) {
			statusList.add(future.get());
		}
		return statusList;
	}

	public static String parser(final Map<String, String> env, Host host,
			String command) {
		Map<String, String> tmp = new HashMap<>(env);
		tmp.put("host_name", host.getName());
		tmp.put("host_user", host.getUser());
		tmp.put("host_pwd", host.getPassword());
		tmp.put("host_ip", host.getIp());
		return TemplateUtils.parser(command, tmp);
	}

	public static boolean isOK(List<Status> statusList) {
		for (Status status : statusList) {
			if (!status.isSuccess()) {
				return false;
			}
		}
		return true;
	}

	public static Map<String, String> initGlobal(String user, String password,
			String group) {
		Map<String, String> env = new HashMap<String, String>();
		env.put("user", user);
		if (user.equals("root")) {
			env.put("home", "/root");
		} else {
			env.put("home", "/home/" + user);
		}
		if (group == null) {
			group = user;
		}
		env.put("group", group);
		env.put("pwd", password);
		return env;
	}

	public static Map<Long, Host> initSession(Iterable<Host> hosts)
			throws JSchException {
		Map<Long, Host> map = new HashMap<>();
		for (Host host : hosts) {
			Session session = JSchUtil.getSession(host.getIp(), host.getUser(),
					host.getPassword(), host.getPort());
			host.setSession(session);
			map.put(host.getId(), host);
		}
		return map;
	}

	public static void print(List<Status> statusList) {
		if (statusList == null) {
			return;
		}
		for (Status status : statusList) {
			System.out.println(status);
		}
	}
}
