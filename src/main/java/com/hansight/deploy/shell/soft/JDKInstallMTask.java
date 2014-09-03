package com.hansight.deploy.shell.soft;

import java.util.ArrayList;
import java.util.List;

import com.hansight.deploy.shell.base.MultiThreadTask;
import com.hansight.deploy.shell.base.ShellTask;
import com.hansight.deploy.shell.env.EnvPathConfig;
import com.hansight.deploy.shell.install.CompressedFileInstall;
import com.hansight.deploy.vo.Status;

public class JDKInstallMTask extends MultiThreadTask implements ShellTask {
	// TODO use a file, get unzipped name
	private String packName = "jdk-7u55-linux-x64.tar.gz";
	private String unzipedName = "jdk1.7.0_55";
	private String lnName = "jdk";

	@Override
	public Status execute() {
		// TODO user defined install path
		// upload and unpack
		Status status = CompressedFileInstall.install(host, env, "/opt",
				packName, unzipedName, lnName, false);
		if (!status.isSuccess()) {
			return status;
		}
		List<String> exports = new ArrayList<>();
		exports.add("#JDK setting");
		exports.add("export JAVA_HOME=/opt/" + lnName);
		exports.add("export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/tools.jar");
		exports.add("export PATH=$JAVA_HOME/bin:$PATH");
		return new EnvPathConfig(env, host, exports).execute();
	}

	@Override
	public Status call() throws Exception {
		return execute();
	}

	@Override
	protected MultiThreadTask clone() {
		return new JDKInstallMTask();
	}

}
