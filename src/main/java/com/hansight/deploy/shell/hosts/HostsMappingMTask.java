package com.hansight.deploy.shell.hosts;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import com.hansight.deploy.entity.Host;
import com.hansight.deploy.shell.base.MultiThreadTask;
import com.hansight.deploy.shell.base.ShellTask;
import com.hansight.deploy.utils.JSchUtil;
import com.hansight.deploy.vo.Status;

public class HostsMappingMTask extends MultiThreadTask implements ShellTask {

	@Override
	public Status execute() {
		try {
			String hostFile = "/etc/hosts";
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Status status = JSchUtil.download(host.getSession(), hostFile, out);
			if (!status.isSuccess()) {
				return status;
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(out.toByteArray())));
			Set<Long> notExist = hosts.keySet();
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = in.readLine()) != null) {
				line = line.trim();
				for (Host other : hosts.values()) {
					if (line.startsWith(other.getIp())) {
						boolean exist = false;
						String arr[] = line.split("\\s*");
						for (int i = 0; i < arr.length; i++) {
							if (arr[i].equals(other.getName())) {
								exist = true;
								notExist.remove(other.getId());
								break;
							}
						}
						if (exist) {
							line += "\t" + other.getName();
						}
					}
				}
				sb.append(line).append('\n');
			}
			for (Long id : notExist) {
				Host other = hosts.get(id);
				sb.append(other.getIp()).append("\t").append(other.getName())
						.append('\n');
			}
			status = JSchUtil.upload(host.getSession(), hostFile,
					new ByteArrayInputStream(sb.toString().getBytes("utf-8")));
			return status;
		} catch (IOException e) {
			e.printStackTrace();
			return Status.failure(e);
		}
	}

	@Override
	public Status call() throws Exception {
		return execute();
	}

	@Override
	protected HostsMappingMTask clone() {
		return new HostsMappingMTask();
	}
}
