package com.hansight.deploy.shell.base;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import com.hansight.deploy.entity.Host;
import com.hansight.deploy.utils.JSchUtil;
import com.hansight.deploy.vo.Status;

public abstract class FileReplace {
	public Status execute(final Map<String, String> env, Host host,
			String remoteFile) {
		// TODO first line 
		String line = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Status status = JSchUtil.download(host.getSession(), remoteFile,
					out);
			if (!status.isSuccess()) {
				return status;
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(out.toByteArray())));
			StringBuffer sb = new StringBuffer();
			while ((line = in.readLine()) != null) {
				sb.append(replace(line)).append("\n");
			}
			sb = append(sb);
			status = JSchUtil.upload(host.getSession(), remoteFile,
					new ByteArrayInputStream(sb.toString().getBytes()));
			if (!status.isSuccess()) {
				return status;
			}
			return Status.success();
		} catch (IOException e) {
			e.printStackTrace();
			return Status.failure(e);
		}
	}

	public abstract String replace(String line);

	public StringBuffer append(StringBuffer sb) {
		return sb;
	}
}
