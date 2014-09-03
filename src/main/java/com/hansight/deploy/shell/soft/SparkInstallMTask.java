package com.hansight.deploy.shell.soft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hansight.deploy.entity.Host;
import com.hansight.deploy.shell.ShellUtils;
import com.hansight.deploy.shell.base.MultiThreadTask;
import com.hansight.deploy.shell.base.ShellTask;
import com.hansight.deploy.shell.env.EnvPathConfig;
import com.hansight.deploy.shell.install.CompressedFileInstall;
import com.hansight.deploy.utils.FileUtils;
import com.hansight.deploy.utils.JSchUtil;
import com.hansight.deploy.vo.Status;

public class SparkInstallMTask extends MultiThreadTask implements ShellTask {
	private String packName = "spark-1.0.2-bin-2.4.0.2.1.2.0-402.tgz";
	private String unzippedName = "spark-1.0.2-bin-2.4.0.2.1.2.0-402";
	private String lnName = "spark";
	private String master;

	public SparkInstallMTask(String master) {
		this.master = master;
	}

	@Override
	public Status execute() {
		try {
			// TODO user defined install path
			// upload and unpack
			String distPath = "/opt";
			Status status = CompressedFileInstall.install(host, env, distPath,
					packName, unzippedName, lnName, false);
			if (!status.isSuccess()) {
				return status;
			}
			// env path
			List<String> exports = new ArrayList<>();
			exports.add("#SCALA setting");
			exports.add("export SPARK_HOME=" + distPath + "/" + lnName);
			exports.add("export PATH=$SPARK_HOME/bin:$PATH");
			// config
			StringBuffer sb = new StringBuffer();
			// slaves
			sb.append("echo \"");
			for (Host h : hosts.values()) {
				if (!h.getIp().equals(master)) {
					sb.append(h.getName()).append(" \\\n\n");
				}
			}
			sb.append("\" > ").append(distPath).append("/").append(lnName)
					.append("/conf/slaves;");
			// spark-env.sh
			String sparkEnvFile = distPath + "/" + lnName
					+ "/conf/spark-env.sh";
			sb.append("touch ").append(sparkEnvFile).append(";");
			sb.append("chown -R spark:spark ").append(sparkEnvFile).append(";");
			sb.append(" echo \"")
					.append(FileUtils
							.readShell("shell/spark-env.sh", " \\\n\n"))
					.append("\" > ").append(sparkEnvFile).append(";");
			String configShell = ShellUtils.parser(env, host, sb.toString());
			for (Host h : hosts.values()) {// set spark master
				if (h.getIp().equals(master)) {
					configShell = configShell.replaceAll("%\\{master\\}",
							h.getName());
				}
			}
			status = JSchUtil.exec(host.getSession(), configShell);
			if (!status.isSuccess()) {
				status.prepend(host.getIp() + " config error:");
				return status;
			}
			return new EnvPathConfig(env, host, exports).execute();
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
	protected MultiThreadTask clone() {
		return new SparkInstallMTask(master);
	}

}
