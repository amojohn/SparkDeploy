package com.hansight.deploy.shell.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.hansight.deploy.entity.Host;
import com.hansight.deploy.vo.Status;

public class MTaskExecutor {
	protected Map<Long, Host> hosts;
	protected Map<String, String> env;
	private ExecutorService pool ;

	public MTaskExecutor(Map<Long, Host> hosts, Map<String, String> env) {
		this.hosts = hosts;
		this.env = env;
		pool = Executors.newFixedThreadPool(hosts.size());
	}

	public List<Status> launch(MultiThreadTask task) {
		List<Future<Status>> futureList = new ArrayList<>();
		boolean first = true;
		for (Map.Entry<Long, Host> entry : hosts.entrySet()) {
			MultiThreadTask obj = null;
			if (first) {
				obj = task;
				first = false;
			} else {
				obj = task.clone();
			}
			obj.host = entry.getValue();
			obj.hosts = hosts;
			obj.env = env;
			Future<Status> future = pool.submit(obj);
			futureList.add(future);
		}
		List<Status> list = new ArrayList<>();
		try {
			for (Future<Status> future : futureList) {
				list.add(future.get());
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			list.add(Status.failure(e));
			return list;
		}
		return list;
	}

	public void close() {
		pool.shutdown();
	}

	public Map<Long, Host> getHosts() {
		return hosts;
	}

	public void setHosts(Map<Long, Host> hosts) {
		this.hosts = hosts;
	}

	public Map<String, String> getEnv() {
		return env;
	}

	public void setEnv(Map<String, String> env) {
		this.env = env;
	}

	public ExecutorService getPool() {
		return pool;
	}

	public void setPool(ExecutorService pool) {
		this.pool = pool;
	}

}
