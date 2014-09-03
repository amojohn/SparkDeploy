package com.hansight.deploy.shell.base;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hansight.deploy.entity.Host;
import com.hansight.deploy.utils.JSchUtil;
import com.hansight.deploy.vo.Status;

/**
 * /etc/profile
 */
public abstract class FileAppend {
	public Status append(final Map<String, String> env, Host host,
			String remoteFile, List<String> exports) {
		// TODO for index
		Status status = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			status = JSchUtil.download(host.getSession(), remoteFile, out);
			if (!status.isSuccess()) {
				status.prepend("setEnvPath error:");
				return status;
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(out.toByteArray())));
			String line = null;
			StringBuffer sb = new StringBuffer();
			List<String> notExist = new ArrayList<String>(exports);
			while ((line = in.readLine()) != null) {
				for (String export : notExist) {
					if (line.equals(export)) {
						notExist.remove(export);
						break;
					}
				}
				sb.append(line).append("\n");
			}
			if (notExist.size() > 0) {
				for (String export : notExist) {
					sb.append(export).append("\n");
				}
			}
			status = JSchUtil.upload(host.getSession(), remoteFile,
					new ByteArrayInputStream(sb.toString().getBytes()));
			if (!status.isSuccess()) {
				status.prepend(remoteFile + " upload error:");
				return status;
			}
			status = JSchUtil.exec(host.getSession(), "source " + remoteFile);
			if (!status.isSuccess()) {
				status.prepend(remoteFile + " upload error:");
				return status;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
}
