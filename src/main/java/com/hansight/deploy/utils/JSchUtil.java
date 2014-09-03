package com.hansight.deploy.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hansight.deploy.vo.Status;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class JSchUtil {
	private static final Logger LOG = LoggerFactory.getLogger(JSchUtil.class);

	public static Session getSession(String host, String username,
			String password, int port) throws JSchException {
		JSch jsch = new JSch();
		Session session = null;

		session = jsch.getSession(username, host, port);
		if (password != null && password.length() > 0) {
			session.setPassword(password);
		}
		Properties config = new Properties();
		config.setProperty("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();
		return session;
	}

	public static Session getSession(String host, String username,
			String password) throws JSchException {
		return getSession(host, username, password, 22);
	}

	public static Session getSession(String host, String password)
			throws JSchException {
		return getSession(host, "root", password, 22);
	}

	public static void disconnectSession(Session session) {
		if (session != null) {
			session.disconnect();
		}
	}

	public static void disconnectChannel(Channel channel) {
		if (channel != null) {
			channel.disconnect();
		}
	}

	public static Status exec(String host, String username, String password,
			Integer port, String command) {
		Session session = null;
		try {
			session = getSession(host, username, password, port);
			return exec(session, command);
		} catch (JSchException e) {
			e.printStackTrace();
			return Status.failure(e);
		} finally {
			disconnectSession(session);
		}
	}

	public static Status exec(Session session, String command) {
		if (session == null) {
			return Status.failure("session is null");
		}
		Status status = null;
		ChannelExec channel = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = null;
		try {
			channel = (ChannelExec) session.openChannel("exec");
			if (channel == null) {
				return Status.failure("cann't open exec channel");
			}
			channel.setCommand(command);
			channel.setInputStream(null);
			channel.setErrStream(out);
			in = channel.getInputStream();
			channel.connect();
			StringBuffer sb = new StringBuffer();
			String msg = readMsg(in, channel);
			sb.append(msg);
			if (channel.getExitStatus() == 0) {
				status = Status.success(msg);
			} else {
				status = Status.failure(msg + out.toString());
			}
		} catch (JSchException | IOException e) {
			e.printStackTrace();
			status = Status.failure(e);
		} finally {
			disconnectChannel(channel);
			IOUtils.close(in);
			LOG.debug("host:{}\t{};\n\t{}", session.getHost(), command, status);
		}
		return status;
	}

	public static Status exec(Session session, String command, String message) {
		Status status = JSchUtil.exec(session, command);
		if (message == null && status.getMessage() == null) {
			return Status.success();
		} else if (message != null
				&& message.trim().equals(status.getMessage())) {
			return Status.success(status.getMessage());
		}
		return status;
	}

	private static String readMsg(InputStream in, ChannelExec channel)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BufferedInputStream bis = new BufferedInputStream(in);
		byte[] tmp = new byte[1024];
		int len;
		while (true) {
			while (bis.available() > 0) {
				len = bis.read(tmp, 0, 1024);
				out.write(tmp, 0, len);
				if (len < 0) {
					break;
				}
			}
			if (channel.isClosed()) {
				if (in.available() > 0) {
					continue;
				} else {
					break;
				}
			} else {
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
				}
			}
		}
		return new String(out.toByteArray());
	}

	public static Status upload(String host, String username, String password,
			Integer port, String remoteLocation, File file) {
		Session session = null;
		try {
			session = getSession(host, username, password, port);
			return upload(session, remoteLocation, file);
		} catch (JSchException e) {
			e.printStackTrace();
			return Status.failure(e);
		} finally {
			disconnectSession(session);
		}
	}

	public static Status upload(Session session, String remoteLocation,
			File file) {
		try {
			return upload(session, remoteLocation, new FileInputStream(file),
					file.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return Status.failure(e);
		}
	}

	public static Status upload(Session session, String remoteLocation,
			InputStream in) {
		int index = remoteLocation.lastIndexOf("/");
		return upload(session, remoteLocation.substring(0, index), in,
				remoteLocation.substring(index + 1));
	}

	public static Status upload(Session session, String remoteLocation,
			InputStream content, String name) {
		if (session == null) {
			return Status.failure("session is null");
		}
		Status mkdir = exec(session, "mkdir -p " + remoteLocation);
		if (!mkdir.isSuccess()) {
			return Status.failure("remoteLocation invalid:" + remoteLocation);
		}
		Status status = null;
		OutputStream out = null;
		ChannelSftp channel = null;
		try {
			channel = (ChannelSftp) session.openChannel("sftp");
			if (channel == null) {
				return Status.failure("cann't open sftp channel");
			}
			channel.connect();
			channel.cd(remoteLocation);
			out = channel.put(name);
			IOUtils.copy(content, out);
			status = Status.success();
		} catch (JSchException | SftpException | IOException e) {
			e.printStackTrace();
			status = Status.failure(e);
		} finally {
			IOUtils.close(content, out);
			if (channel != null) {
				channel.disconnect();
			}
			LOG.debug("host:{}\tupload:{}/{};\n\t{}", session.getHost(),
					remoteLocation, name, status);
		}
		return status;
	}

	public static Status download(Session session, String remoteFile,
			OutputStream out) {
		int index = remoteFile.lastIndexOf("/");

		return download(session, remoteFile.substring(0, index),
				remoteFile.substring(index + 1), out);
	}

	public static Status download(Session session, String remoteLocation,
			String name, OutputStream out) {
		if (session == null) {
			return Status.failure("session is null");
		}
		Status status = null;
		InputStream in = null;
		ChannelSftp channel = null;
		try {
			channel = (ChannelSftp) session.openChannel("sftp");
			if (channel == null) {
				return Status.failure("cann't open sftp channel");
			}
			channel.connect();
			channel.cd(remoteLocation);
			in = channel.get(name);
			IOUtils.copy(in, out);
			status = Status.success();
		} catch (JSchException | SftpException | IOException e) {
			e.printStackTrace();
			status = Status.failure(e);
		} finally {
			IOUtils.close(in, out);
			if (channel != null) {
				channel.disconnect();
			}
			LOG.debug("host:{}\tdown:{}/{};\n\t{}", session.getHost(),
					remoteLocation, name, status);
		}
		return status;
	}

	public static void main(String[] args) throws JSchException {
		Session session = JSchUtil.getSession("172.16.219.122", "bigdata@4102");
		Status pwd = exec(session, "ls -alh");
		System.out.println(pwd);

		System.out.println("=======================get");
		download(session, "/opt/es/config/", "elasticsearch.yml", System.out);
		session.disconnect();
	}

	public static void close(Iterable<Session> sessions) {
		if (sessions == null) {
			return;
		}
		for (Session session : sessions) {
			try {
				session.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(Session session) {
		try {
			if (session != null)
				session.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
