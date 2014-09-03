package org.hansight.deploy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hansight.deploy.entity.Action;
import com.hansight.deploy.entity.Host;
import com.hansight.deploy.utils.JSchUtil;
import com.hansight.deploy.vo.Status;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class TestJSchUtils {
	public static void main(String[] args) throws JSchException {
		// 1. add user spark
		Action action = new Action();
		action.setName("useradd");
		action.setShell("pwd");

		Map<String, Session> map = new HashMap<String, Session>();
		for (Host host : getHosts()) {
			Session session = map.get(host.getId());
			if (session == null) {
				session = JSchUtil.getSession(host.getIp(), host.getUser(),
						host.getPassword(), host.getPort());
				map.put(host.getIp(), session);
			}
			Status status = JSchUtil.exec(session, action.getShell());
			System.out.println(status.toString());
		}
		for (Session session : map.values()) {
			session.disconnect();
		}
	}

	public static List<Host> getHosts() {
		List<Host> hosts = new ArrayList<Host>();
		Host host = new Host();
		host.setId(1L);
		host.setIp("172.16.219.122");
		host.setName("es122");
		host.setUser("root");
		host.setPassword("bigdata@4102");
		host.setPort(22);
		;
		hosts.add(host);

		return hosts;
	}
}
